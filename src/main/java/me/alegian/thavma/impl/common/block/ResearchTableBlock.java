package me.alegian.thavma.impl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ResearchTableBlock extends Block {
  public ResearchTableBlock() {
    super(Properties.ofFullCopy(Blocks.OAK_PLANKS));
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return Shapes.box(0.2, 0.2, 0.2, 0.8, 0.8, 0.8);
  }
}
