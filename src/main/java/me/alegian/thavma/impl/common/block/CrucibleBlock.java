package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.aspect.AspectHelperKt;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.block.entity.CrucibleBE;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.data.capability.IAspectContainer;
import me.alegian.thavma.impl.common.recipe.CrucibleRecipeInput;
import me.alegian.thavma.impl.init.registries.T7Tags;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends Block implements EntityBlock {
  public static final VoxelShape CRUCIBLE_INSIDE = Block.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
  public static final VoxelShape CRUCIBLE_SHAPE = Shapes.join(
      Shapes.block(),
      Shapes.or(
          Block.box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
          Block.box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
          Block.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
          CrucibleBlock.CRUCIBLE_INSIDE
      ),
      BooleanOp.ONLY_FIRST
  );
  public static final BooleanProperty BOILING = T7BlockStateProperties.BOILING;

  public CrucibleBlock() {
    super(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
  }

  public static void meltItem(ServerLevel level, BlockPos pPos, ItemEntity itemEntity) {
    var thrownStack = itemEntity.getItem();

    // try to use as catalyst
    if (thrownStack.is(T7Tags.INSTANCE.getCATALYST())) {
      AspectMap crucibleAspects = AspectContainer
          .at(level, pPos).map(IAspectContainer::getAspects).orElseThrow();

      RecipeManager recipes = level.getRecipeManager();
      var input = new CrucibleRecipeInput(crucibleAspects, thrownStack);

      boolean success = recipes.getRecipeFor(
          T7RecipeTypes.INSTANCE.getCRUCIBLE().get(),
          input,
          level
      ).map(recipe -> {
        if (!CrucibleBlock.tryLowerFillLevel(level, pPos)) return false;
        CrucibleBlock.waterSplash(level, pPos);

        AspectContainer
            .at(level, pPos)
            .ifPresent(container ->
                container.extract(recipe.value().getRequiredAspects())
            );

        if (itemEntity.getOwner() instanceof ServerPlayer player) {
          ItemEntity itementity = player.drop(recipe.value().assemble(input, level.registryAccess()), true, true);
          if (itementity != null) {
            itementity.setNoPickUpDelay();
            itementity.setTarget(player.getUUID());
          }
          CrucibleBlock.shrinkItemEntity(itemEntity);
        }

        return true;
      }).orElse(false);

      if (success) return;// if catalyst failed, try to melt item instead
    }

    if (!AspectHelperKt.hasAspects(thrownStack) || !CrucibleBlock.hasWater(level, pPos)) return;
    AspectMap itemAspects = AspectHelperKt.getAspects(itemEntity);
    AspectContainer
        .at(level, pPos)
        .ifPresent(c -> c.insert(itemAspects));
    CrucibleBlock.waterSplash(level, pPos);
    itemEntity.discard();
  }

  private static void waterSplash(ServerLevel level, BlockPos pPos) {
    level.playSound(null, pPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1F, 1.0F);
  }

  // returns true if any water was drained
  public static boolean tryLowerFillLevel(Level pLevel, BlockPos pPos) {
    return pLevel.getBlockEntity(pPos, T7BlockEntities.INSTANCE.getCRUCIBLE().get()).map(be -> be.getFluidHandler().catalystDrain()).orElse(false);
  }

  public static boolean hasWater(Level pLevel, BlockPos pPos) {
    return pLevel.getBlockEntity(pPos, T7BlockEntities.INSTANCE.getCRUCIBLE().get()).map(be -> be.getFluidHandler().getFluidAmount() > 0).orElse(false);
  }

  // returns true if any water was filled
  public static boolean fillUp(Level pLevel, BlockPos pPos) {
    var be = pLevel.getBlockEntity(pPos);
    if (be instanceof CrucibleBE crucibleBE) return crucibleBE.getFluidHandler().fillUp();
    return false;
  }

  public static void shrinkItemEntity(ItemEntity itemEntity) {
    var stack = itemEntity.getItem();
    stack.shrink(1);
    if (stack.isEmpty()) itemEntity.discard();
    else itemEntity.setItem(stack.copy());
  }

  public static boolean isHeatSource(LevelAccessor level, BlockPos pos) {
    var bs = level.getBlockState(pos);
    var bsHeat = bs.is(T7Tags.CrucibleHeatSourceTag.INSTANCE.getBLOCK());
    var fs = level.getFluidState(pos);
    var fsHeat = fs.is(T7Tags.CrucibleHeatSourceTag.INSTANCE.getFLUID());
    return bsHeat || fsHeat;
  }

  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pState.getValue(CrucibleBlock.BOILING) && !pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity)
      pEntity.hurt(pLevel.damageSources().hotFloor(), 1.0F);

    super.stepOn(pLevel, pPos, pState, pEntity);
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    var below = pContext.getClickedPos().below();

    return this.defaultBlockState()
        .setValue(CrucibleBlock.BOILING, CrucibleBlock.isHeatSource(pContext.getLevel(), below));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(CrucibleBlock.BOILING);
  }

  protected boolean isEntityInsideContent(BlockPos pPos, Entity pEntity) {
    return pEntity.getY() < (double) pPos.getY() + 15 / 16f
        && pEntity.getBoundingBox().maxY > (double) pPos.getY() + 0.25;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new CrucibleBE(pPos, pState);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
    return type == T7BlockEntities.INSTANCE.getCRUCIBLE().get() ? CrucibleBE::tick : null;
  }

  @Override
  protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
    return false;
  }

  @Override
  protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
    return pState.setValue(CrucibleBlock.BOILING, CrucibleBlock.isHeatSource(pLevel, pPos.below()));
  }

  @Override
  protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
    super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    if (!pOldState.is(this)) pLevel.invalidateCapabilities(pPos);
  }

  @Override
  protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
    super.onRemove(pState, pLevel, pPos, pOldState, pIsMoving);
    if (!pState.is(pOldState.getBlock())) pLevel.invalidateCapabilities(pPos);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
    // water buckets should be usable to top off, even if 1000 mB is too much
    if (pPlayer.getItemInHand(pHand).is(Items.WATER_BUCKET) && CrucibleBlock.fillUp(pLevel, pPos)) {
      if (!pPlayer.isCreative()) pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
      pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

      return ItemInteractionResult.SUCCESS;
    }

    // generic fluid interaction
    if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, pHitResult.getDirection()))
      return ItemInteractionResult.SUCCESS;

    return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
  }

  @Override
  protected VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
    return CrucibleBlock.CRUCIBLE_INSIDE;
  }

  @Override
  protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return CrucibleBlock.CRUCIBLE_SHAPE;
  }

  @Override
  protected void entityInside(BlockState pState, Level level, BlockPos pPos, Entity pEntity) {
    if (!level.isClientSide
        && level instanceof ServerLevel serverLevel
        && pState.getValue(CrucibleBlock.BOILING)
        && pEntity instanceof ItemEntity itemEntity
        && this.isEntityInsideContent(pPos, pEntity)
    ) if (pEntity.mayInteract(level, pPos)) {
      CrucibleBlock.meltItem(serverLevel, pPos, itemEntity);
      level.sendBlockUpdated(
          pPos,
          pState,
          pState,
          Block.UPDATE_CLIENTS
      );
    }
  }
}
