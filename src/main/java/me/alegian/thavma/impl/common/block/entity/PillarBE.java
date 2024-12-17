package me.alegian.thavma.impl.common.block.entity;

import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PillarBE extends BlockEntity implements GeoBlockEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  /**
   * Dummy constructor used for rendering Item form
   */
  public PillarBE() {
    this(new BlockPos(0, 0, 0), T7Blocks.INSTANCE.getPILLAR().get().defaultBlockState());
  }

  public PillarBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.INSTANCE.getPILLAR().get(), pos, blockState);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }
}
