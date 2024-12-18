package me.alegian.thavma.impl.client

import me.alegian.thavma.impl.init.registries.deferred.T7Attributes.REVEALING
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item

fun localPlayerHasRevealing(): Boolean {
    return Minecraft.getInstance().player?.getAttribute(REVEALING)?.value == 1.0
}

fun getLocalPlayerEquipmentItem(slot: EquipmentSlot): Item? {
    return Minecraft.getInstance().player?.getItemBySlot(slot)?.item
}
