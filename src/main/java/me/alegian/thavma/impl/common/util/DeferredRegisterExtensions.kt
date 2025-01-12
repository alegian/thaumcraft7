package me.alegian.thavma.impl.common.util

import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

fun DeferredRegister.Items.registerItem(name: String, supplier: Supplier<Item>): DeferredItem<Item> {
    return this.registerItem(name) { _ -> supplier.get() }
}