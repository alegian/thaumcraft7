package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.research.Research
import me.alegian.thavma.impl.init.registries.T7Registries
import net.neoforged.neoforge.registries.DeferredRegister

object Researches {
    val REGISTRAR: DeferredRegister<Research> = DeferredRegister.create(T7Registries.RESEARCH, Thavma.MODID)

    val TRANSFUSION = REGISTRAR.register("transfusion", ::Research)
}
