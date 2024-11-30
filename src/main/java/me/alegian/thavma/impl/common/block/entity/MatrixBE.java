package me.alegian.thavma.impl.common.block.entity;

import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MatrixBE extends BlockEntity implements GeoBlockEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final AnimationController<MatrixBE> ANIM_CONTROLLER = new AnimationController<>(this, "cycle", 20, state -> PlayState.CONTINUE)
      .triggerableAnim("closed", RawAnimation.begin().thenLoop("closed"))
      .triggerableAnim("open", RawAnimation.begin().thenLoop("open"))
      .triggerableAnim("spin_closed", RawAnimation.begin().thenLoop("spin_closed"))
      .triggerableAnim("spin_closed_fast", RawAnimation.begin().thenLoop("spin_closed_fast"))
      .triggerableAnim("spin_open", RawAnimation.begin().thenLoop("spin_open"));

  /**
   * Dummy constructor used for rendering Item form
   */
  public MatrixBE() {
    this(new BlockPos(0, 0, 0), T7Blocks.MATRIX.get().defaultBlockState());
  }

  public MatrixBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.MATRIX.get(), pos, blockState);
    SingletonGeoAnimatable.registerSyncedAnimatable(this);
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(this.ANIM_CONTROLLER);
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }
}
