package me.alegian.thavma.impl.common.block;


import me.alegian.thavma.impl.common.block.entity.AuraNodeBE;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
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
  @NotNull
  public RenderShape getRenderShape(@NotNull BlockState blockState) {
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
    return BaseEntityBlock.createTickerHelper(type, T7BlockEntities.INSTANCE.getAURA_NODE().get(), AuraNodeBE::tick);
  }
}
