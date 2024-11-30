package me.alegian.thavma.impl.common.block.entity;

import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MatrixBE extends BlockEntity implements GeoBlockEntity {
  protected static final RawAnimation TEST = RawAnimation.begin().thenLoop("rotating");
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

  /**
   * Dummy constructor used for rendering Item form
   */
  public MatrixBE() {
    this(new BlockPos(0, 0, 0), T7Blocks.MATRIX.get().defaultBlockState());
  }

  public MatrixBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.MATRIX.get(), pos, blockState);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }
}
