package me.alegian.thaumcraft7.block;

import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import me.alegian.thaumcraft7.blockentity.TCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.Nullable;

public class CrucibleB extends Block implements EntityBlock {
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

  public CrucibleB() {
    super(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON));
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
  protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
    if (!pLevel.isClientSide && pEntity instanceof ItemEntity && this.isEntityInsideContent(pPos, pEntity)) {
      if (pEntity.mayInteract(pLevel, pPos) && lowerFillLevel(pLevel, pPos)) {
        pEntity.kill();
        pLevel.playSound(pEntity, pPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1F, 1.0F);
      }
    }
  }

  // returns true if any water was drained
  public static boolean lowerFillLevel(Level pLevel, BlockPos pPos) {
    var be = pLevel.getBlockEntity(pPos);
    if (be instanceof CrucibleBE crucibleBE) {
      return crucibleBE.getFluidHandler().catalystDrain();
    }
    return false;
  }

  // returns true if any water was filled
  public static boolean fillUp(Level pLevel, BlockPos pPos) {
    var be = pLevel.getBlockEntity(pPos);
    if (be instanceof CrucibleBE crucibleBE) {
      return crucibleBE.getFluidHandler().fillUp();
    }
    return false;
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
    return type == TCBlockEntities.CRUCIBLE.get() ? CrucibleBE::tick : null;
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
