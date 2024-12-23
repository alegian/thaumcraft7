package me.alegian.thavma.impl.init.registries.deferred.callback

import me.alegian.thavma.impl.common.wand.WandCoreMaterial
import me.alegian.thavma.impl.common.wand.WandHandleMaterial
import me.alegian.thavma.impl.init.registries.deferred.T7Items.isWandRegistered
import me.alegian.thavma.impl.init.registries.deferred.T7Items.registerWand
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.callback.AddCallback

/**
 * Registry Callback to register all wand combinations of current
 * handle being registered.
 */
class WandHandleCombinations(
    private val itemRegistry: Registry<Item>,
    private val coreRegistry: Registry<WandCoreMaterial>
) : AddCallback<WandHandleMaterial> {
    override fun onAdd(
        handleRegistry: Registry<WandHandleMaterial>,
        id: Int,
        key: ResourceKey<WandHandleMaterial>,
        newHandle: WandHandleMaterial
    ) {
        for (core in this.coreRegistry)
            if (core.registerCombinations && !isWandRegistered(newHandle, core))
                registerWand(this.itemRegistry, newHandle, core)
    }
}
