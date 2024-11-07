package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;

/**
 * A Sized ResultSlot
 */
public class T7ResultSlot extends ResultSlot implements Sized {
  private final int size;
  private boolean mayPickup = true;

  public T7ResultSlot(Menu menu, CraftingContainer craftSlots, Container container, int id, int size) {
    super(menu.getPlayer(), craftSlots, container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
  }

  @Override
  public int getSize() {
    return this.size;
  }

  public void setMayPickup(boolean mayPickup) {
    this.mayPickup = mayPickup;
  }

  @Override
  public boolean mayPickup(Player player) {
    return this.mayPickup;
  }
}
