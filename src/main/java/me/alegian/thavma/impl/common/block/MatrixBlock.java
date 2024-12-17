package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.block.entity.MatrixBE;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MatrixBlock extends Block implements EntityBlock {
  public static final List<String> animKeys = List.of("closed", "open", "spin_open", "spin_closed", "spin_closed_fast");
  public static int cycle = 0;

  public MatrixBlock() {
    super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion());
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
    if (!pLevel.isClientSide()) pLevel.getBlockEntity(pPos, T7BlockEntities.INSTANCE.getMATRIX().get()).ifPresent(be ->
        be.triggerAnim("cycle", MatrixBlock.animKeys.get(MatrixBlock.cycle++ % MatrixBlock.animKeys.size()))
    );
    return InteractionResult.SUCCESS;
  }

  @NotNull
  @Override
  public RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
    return new MatrixBE(pos, blockState);
  }
}
