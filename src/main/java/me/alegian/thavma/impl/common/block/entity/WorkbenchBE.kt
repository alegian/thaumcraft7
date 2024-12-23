package me.alegian.thavma.impl.common.block.entity

import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities.WORKBENCH
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks.ARCANE_WORKBENCH
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.SingletonGeoAnimatable
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil

/**
 * Default values used for rendering Item form
 */
class WorkbenchBE(
    pos: BlockPos = BlockPos(0, 0, 0),
    blockState: BlockState = ARCANE_WORKBENCH.get().defaultBlockState()
) : BlockEntity(WORKBENCH.get(), pos, blockState), GeoBlockEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)
    private val ANIM_CONTROLLER = AnimationController(
        this, "cycle", 20
    ) { _ -> PlayState.CONTINUE }
        .triggerableAnim("closed", RawAnimation.begin().thenLoop("closed"))
        .triggerableAnim("open", RawAnimation.begin().thenLoop("open"))
        .triggerableAnim("rotating", RawAnimation.begin().thenLoop("rotating"))

    init {
        SingletonGeoAnimatable.registerSyncedAnimatable(this)
    }

    override fun registerControllers(controllers: ControllerRegistrar) {
        controllers.add(this.ANIM_CONTROLLER)
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.cache
    }
}
