package me.alegian.thavma.impl.init.registries.deferred.util

import me.alegian.thavma.impl.common.aspect.Aspect
import me.alegian.thavma.impl.init.registries.T7Registries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

class T7DeferredRegister<T> private constructor(registryKey: ResourceKey<out Registry<T>>, namespace: String) :
    DeferredRegister<T>(registryKey, namespace) {
    class Aspects(namespace: String) : DeferredRegister<Aspect>(T7Registries.ASPECT.key(), namespace) {
        fun <A : Aspect> registerAspect(name: String, sup: Supplier<out A>): DeferredAspect<A> {
            return register(name) { _ -> sup.get() } as DeferredAspect<A>
        }

        override fun <A : Aspect> createHolder(
            registryKey: ResourceKey<out Registry<Aspect>>,
            key: ResourceLocation
        ): DeferredAspect<A> {
            return DeferredAspect.createAspect(ResourceKey.create(registryKey, key))
        }
    }

    companion object {
        fun createAspects(modid: String): Aspects {
            return Aspects(modid)
        }
    }
}
