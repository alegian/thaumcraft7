package me.alegian.thaumcraft7.impl.common.block;

import me.alegian.thaumcraft7.impl.common.aspect.AspectHelper;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.block.entity.CrucibleBE;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.impl.common.data.capability.IAspectContainer;
import me.alegian.thaumcraft7.impl.common.recipe.CrucibleRecipeInput;
import me.alegian.thaumcraft7.impl.init.registries.T7Tags;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7RecipeTypes;
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
  public static final VoxelShape CRUCIBLE_INSIDE = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
  public static final VoxelShape CRUCIBLE_SHAPE = Shapes.join(
      Shapes.block(),
      Shapes.or(
          box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
          box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
          box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0),
          CRUCIBLE_INSIDE
      ),
      BooleanOp.ONLY_FIRST
  );
  public static final BooleanProperty BOILING = T7BlockStateProperties.BOILING;

  public CrucibleBlock() {
    super(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(BOILING);
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    var below = pContext.getClickedPos().below();

    return defaultBlockState()
        .setValue(BOILING, isHeatSource(pContext.getLevel(), below));
  }

  @Override
  protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
    return pState.setValue(BOILING, isHeatSource(pLevel, pPos.below()));
  }

  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pState.getValue(BOILING) && !pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity) {
      pEntity.hurt(pLevel.damageSources().hotFloor(), 1.0F);
    }

    super.stepOn(pLevel, pPos, pState, pEntity);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
    // water buckets should be usable to top off, even if 1000 mB is too much
    if (pPlayer.getItemInHand(pHand).is(Items.WATER_BUCKET) && fillUp(pLevel, pPos)) {
      if (!pPlayer.isCreative()) pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
      pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

      return ItemInteractionResult.SUCCESS;
    }

    // generic fluid interaction
    if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, pHitResult.getDirection())) {
      return ItemInteractionResult.SUCCESS;
    }

    return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
  }

  @Override
  protected void entityInside(BlockState pState, Level level, BlockPos pPos, Entity pEntity) {
    if (!level.isClientSide
        && level instanceof ServerLevel serverLevel
        && pState.getValue(BOILING)
        && pEntity instanceof ItemEntity itemEntity
        && this.isEntityInsideContent(pPos, pEntity)
    ) {
      if (pEntity.mayInteract(level, pPos)) {
        meltItem(serverLevel, pPos, itemEntity);
        level.sendBlockUpdated(
            pPos,
            pState,
            pState,
            Block.UPDATE_CLIENTS
        );
      }
    }
  }

  public static void meltItem(ServerLevel level, BlockPos pPos, ItemEntity itemEntity) {
    var thrownStack = itemEntity.getItem();

    // try to use as catalyst
    if (thrownStack.is(T7Tags.CATALYST)) {
      AspectMap crucibleAspects = AspectContainerHelper
          .getAspectContainer(level, pPos).map(IAspectContainer::getAspects).orElseThrow();

      RecipeManager recipes = level.getRecipeManager();
      var input = new CrucibleRecipeInput(crucibleAspects, thrownStack);

      boolean success = recipes.getRecipeFor(
          T7RecipeTypes.CRUCIBLE.get(),
          input,
          level
      ).map(recipe -> {
        if (!tryLowerFillLevel(level, pPos)) return false;
        waterSplash(level, pPos);

        AspectContainerHelper
            .getAspectContainer(level, pPos)
            .ifPresent(container ->
                container.subtract(recipe.value().getRequiredAspects())
            );

        if (itemEntity.getOwner() instanceof ServerPlayer player) {
          ItemEntity itementity = player.drop(recipe.value().getResult(), true, true);
          if (itementity != null) {
            itementity.setNoPickUpDelay();
            itementity.setTarget(player.getUUID());
          }
          shrinkItemEntity(itemEntity);
        }

        return true;
      }).orElse(false);

      if (success) return;// if catalyst failed, try to melt item instead
    }

    if (!AspectHelper.hasAspects(thrownStack)) return;
    AspectMap itemAspects = AspectHelper.getAspects(itemEntity);
    AspectContainerHelper
        .getAspectContainer(level, pPos)
        .ifPresent(c -> c.addAspects(itemAspects));
    waterSplash(level, pPos);
    itemEntity.discard();
  }

  private static void waterSplash(ServerLevel level, BlockPos pPos) {
    level.playSound(null, pPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1F, 1.0F);
  }

  // returns true if any water was drained
  public static boolean tryLowerFillLevel(Level pLevel, BlockPos pPos) {
    return pLevel.getBlockEntity(pPos, T7BlockEntities.CRUCIBLE.get()).map(be -> be.getFluidHandler().catalystDrain()).orElse(false);
  }

  // returns true if any water was filled
  public static boolean fillUp(Level pLevel, BlockPos pPos) {
    var be = pLevel.getBlockEntity(pPos);
    if (be instanceof CrucibleBE crucibleBE) {
      return crucibleBE.getFluidHandler().fillUp();
    }
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
    var bsHeat = bs.is(T7Tags.CrucibleHeatSourceTag.BLOCK);
    var fs = level.getFluidState(pos);
    var fsHeat = fs.is(T7Tags.CrucibleHeatSourceTag.FLUID);
    return bsHeat || fsHeat;
  }

  protected boolean isEntityInsideContent(BlockPos pPos, Entity pEntity) {
    return pEntity.getY() < (double) pPos.getY() + 15 / 16f
        && pEntity.getBoundingBox().maxY > (double) pPos.getY() + 0.25;
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

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new CrucibleBE(pPos, pState);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
    return type == T7BlockEntities.CRUCIBLE.get() ? CrucibleBE::tick : null;
  }

  @Override
  protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return CRUCIBLE_SHAPE;
  }

  @Override
  protected VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
    return CRUCIBLE_INSIDE;
  }

  @Override
  protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
    return false;
  }
}
