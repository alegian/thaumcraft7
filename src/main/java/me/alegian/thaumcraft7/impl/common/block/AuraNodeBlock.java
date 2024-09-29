package me.alegian.thaumcraft7.impl.common.block;


import me.alegian.thaumcraft7.impl.common.block.entity.AuraNodeBE;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AuraNodeBlock extends TransparentBlock implements EntityBlock {
  public AuraNodeBlock() {
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

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
    return BaseEntityBlock.createTickerHelper(type, T7BlockEntities.AURA_NODE.get(), AuraNodeBE::tick);
  }
}
