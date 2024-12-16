package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.block.entity.PillarBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PillarBlock extends Block implements EntityBlock {
  public PillarBlock() {
    super(Properties.ofFullCopy(Blocks.STONE).noOcclusion());
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
    );
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    var stateForPlacement = this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection());
    var pos = context.getClickedPos();
    var facing = stateForPlacement.getValue(BlockStateProperties.HORIZONTAL_FACING);

    if (!context.getLevel().getBlockState(pos.relative(facing)).canBeReplaced()) return null;
    if (!context.getLevel().getBlockState(pos.relative(facing).above()).canBeReplaced()) return null;
    return stateForPlacement;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(BlockStateProperties.HORIZONTAL_FACING);
  }

  @NotNull
  @Override
  public RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
    return new PillarBE(pos, blockState);
  }
}
