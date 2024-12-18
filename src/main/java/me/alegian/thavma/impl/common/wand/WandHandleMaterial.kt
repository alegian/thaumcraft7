package me.alegian.thavma.impl.common.wand

import me.alegian.thavma.impl.init.registries.T7Registries.WAND_HANDLE
import net.minecraft.resources.ResourceLocation


class WandHandleMaterial(val registerCombinations: Boolean = true) {
    val registeredName
        get() = registeredLocation.path

    val registeredLocation: ResourceLocation
        get() {
            val key = WAND_HANDLE.getKey(this)
            requireNotNull(key) { "Thavma Exception: Trying to Access Unregistered Wand Handle" }
            return key
        }
}
