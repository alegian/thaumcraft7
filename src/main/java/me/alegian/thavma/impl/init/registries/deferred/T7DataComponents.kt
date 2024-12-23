package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.aspect.AspectMap
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.DeferredRegister

object T7DataComponents {
    val REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Thavma.MODID)

    val ASPECTS = REGISTRAR.registerComponentType("aspects") { builder ->
        builder
            .persistent(AspectMap.CODEC)
            .networkSynchronized(AspectMap.STREAM_CODEC)
    }
}
