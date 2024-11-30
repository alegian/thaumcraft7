package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.block.entity.PedestalBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends Block implements EntityBlock {
  public PedestalBlock() {
    super(Properties.ofFullCopy(Blocks.STONE).noOcclusion());
  }

  @NotNull
  @Override
  public RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
    return new PedestalBE(pos, blockState);
  }
}
