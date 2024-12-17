package me.alegian.thavma.impl.init.registries.deferred.util

import me.alegian.thavma.impl.common.aspect.Aspect
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.registries.DeferredHolder

class DeferredAspect<T : Aspect> private constructor(key: ResourceKey<Aspect>) :
    DeferredHolder<Aspect, T>(key) {
    companion object {
        fun <T : Aspect> createAspect(key: ResourceKey<Aspect>): DeferredAspect<T> {
            return DeferredAspect(key)
        }
    }
}
