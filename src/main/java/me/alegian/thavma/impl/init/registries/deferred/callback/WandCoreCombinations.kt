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
 * core being registered.
 */
class WandCoreCombinations(
    private val itemRegistry: Registry<Item>,
    private val handleRegistry: Registry<WandHandleMaterial>
) : AddCallback<WandCoreMaterial> {
    override fun onAdd(
        coreRegistry: Registry<WandCoreMaterial>,
        id: Int,
        key: ResourceKey<WandCoreMaterial>,
        newCore: WandCoreMaterial
    ) {
        for (handle in this.handleRegistry)
            if (handle.registerCombinations && !isWandRegistered(handle, newCore))
                registerWand(this.itemRegistry, handle, newCore)
    }
}
