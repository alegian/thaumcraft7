package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.loot.WardenLootModifier
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries

object T7GlobalLootModifierSerializers {
    val REGISTRAR = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Thavma.MODID)

    val WARDEN = REGISTRAR.register("warden") { -> WardenLootModifier.CODEC }
}
