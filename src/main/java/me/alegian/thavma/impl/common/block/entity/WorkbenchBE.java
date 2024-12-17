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

public class WorkbenchBE extends BlockEntity implements GeoBlockEntity {
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final AnimationController<WorkbenchBE> ANIM_CONTROLLER = new AnimationController<>(this, "cycle", 20, state -> PlayState.CONTINUE)
      .triggerableAnim("closed", RawAnimation.begin().thenLoop("closed"))
      .triggerableAnim("open", RawAnimation.begin().thenLoop("open"))
      .triggerableAnim("rotating", RawAnimation.begin().thenLoop("rotating"));

  /**
   * Dummy constructor used for rendering Item form
   */
  public WorkbenchBE() {
    this(new BlockPos(0, 0, 0), T7Blocks.ARCANE_WORKBENCH.get().defaultBlockState());
  }

  public WorkbenchBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.INSTANCE.getWORKBENCH().get(), pos, blockState);
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
