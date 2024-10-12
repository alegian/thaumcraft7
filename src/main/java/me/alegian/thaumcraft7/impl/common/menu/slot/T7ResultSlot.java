package me.alegian.thaumcraft7.impl.common.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;

public class T7ResultSlot extends ResultSlot {
  int size;

  public T7ResultSlot(Player player, CraftingContainer craftSlots, Container container, int id, SlotPose pose, int size) {
    super(player, craftSlots, container, id, pose.getX(), pose.getY());
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}
