package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.block.entity.PillarBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PillarBlock extends Block implements EntityBlock {
  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

  public PillarBlock() {
    super(Properties.ofFullCopy(Blocks.STONE).noOcclusion());
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(PillarBlock.FACING, Direction.NORTH)
    );
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState().setValue(PillarBlock.FACING, context.getHorizontalDirection());
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(PillarBlock.FACING);
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
