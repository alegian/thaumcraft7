package me.alegian.thaumcraft7.impl.client;

import me.alegian.thaumcraft7.impl.init.registries.T7Capabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
  public static boolean isLocalPlayerWearingGoggles() {
    return getLocalPlayerEquipment(EquipmentSlot.HEAD)
        .map(h -> h.getCapability(T7Capabilities.REVEALING) != null)
        .orElse(false);
  }

  public static Optional<ItemStack> getLocalPlayerEquipment(EquipmentSlot slot) {
    return Optional.ofNullable(Minecraft.getInstance().player)
        .map(p -> p.getItemBySlot(slot)).filter(i -> !i.is(Items.AIR));
  }

  public static Optional<Item> getLocalPlayerEquipmentItem(EquipmentSlot slot) {
    return getLocalPlayerEquipment(slot).map(ItemStack::getItem);
  }
}
