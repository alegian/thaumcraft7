package me.alegian.thavma.impl.client;

import me.alegian.thavma.impl.init.registries.deferred.T7Attributes;
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
  public static boolean localPlayerHasRevealing() {
    return Optional.ofNullable(Minecraft.getInstance().player)
        .map(p -> p.getAttribute(T7Attributes.REVEALING))
        .map(a -> a.getValue() == 1)
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
