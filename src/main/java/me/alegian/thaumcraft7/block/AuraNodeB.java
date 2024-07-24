package me.alegian.thaumcraft7.block;


import me.alegian.thaumcraft7.blockentity.AuraNodeBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AuraNodeB extends TransparentBlock implements EntityBlock {
  protected AuraNodeB() {
    super(Properties.of()
        .noTerrainParticles()
        .strength(2F)
        .sound(SoundType.WOOL)
        .noCollission()
        .noOcclusion()
    );
  }

  @Override
  @SuppressWarnings("deprecation")
  public RenderShape getRenderShape(BlockState blockState) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
    return new AuraNodeBE(pos, blockState);
  }
}
