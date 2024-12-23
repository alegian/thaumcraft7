package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.wand.WandCoreMaterial
import me.alegian.thavma.impl.init.registries.T7Registries
import net.neoforged.neoforge.registries.DeferredRegister

object WandCoreMaterials {
    val REGISTRAR = DeferredRegister.create(T7Registries.WAND_CORE.key(), Thavma.MODID)

    val WOOD = REGISTRAR.register("wood") { -> WandCoreMaterial(25) }

    val GREATWOOD = REGISTRAR.register("greatwood") { -> WandCoreMaterial(50) }

    val SILVERWOOD = REGISTRAR.register("silverwood") { -> WandCoreMaterial(100) }
}
