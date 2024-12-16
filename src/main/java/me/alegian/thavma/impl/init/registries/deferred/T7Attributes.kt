package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import net.minecraft.Util
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.attributes.Attribute
import net.neoforged.neoforge.common.BooleanAttribute
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object T7Attributes {
    val REGISTRAR: DeferredRegister<Attribute> = DeferredRegister.create(Registries.ATTRIBUTE, Thavma.MODID)
    
    val REVEALING: DeferredHolder<Attribute, Attribute> = REGISTRAR.register(
        "revealing"
    ) { rl ->
        BooleanAttribute(
            Util.makeDescriptionId(Registries.ATTRIBUTE.location().path, rl),
            false
        ).setSyncable(true)
    }
}
