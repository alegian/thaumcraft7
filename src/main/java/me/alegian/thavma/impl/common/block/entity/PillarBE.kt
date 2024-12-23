package me.alegian.thavma.impl.common.block.entity

import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.util.GeckoLibUtil

/**
 * Default values used for rendering Item form
 */
class PillarBE(
    pos: BlockPos = BlockPos(0, 0, 0),
    blockState: BlockState = T7Blocks.PILLAR.get().defaultBlockState()
) : BlockEntity(T7BlockEntities.PILLAR.get(), pos, blockState), GeoBlockEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: ControllerRegistrar) {
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.cache
    }
}
