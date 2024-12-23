package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.init.data.worldgen.tree.trunk.GreatwoodTrunkPlacer
import me.alegian.thavma.impl.init.data.worldgen.tree.trunk.SilverwoodTrunkPlacer
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
import net.neoforged.neoforge.registries.DeferredRegister

object T7TrunkPlacerTypes {
    val REGISTRAR = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Thavma.MODID)

    val GREATWOOD = REGISTRAR.register("greatwood") { -> TrunkPlacerType(GreatwoodTrunkPlacer.CODEC) }
    val SILVERWOOD = REGISTRAR.register("silverwood") { -> TrunkPlacerType(SilverwoodTrunkPlacer.CODEC) }
}
