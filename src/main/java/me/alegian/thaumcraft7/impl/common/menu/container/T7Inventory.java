package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.T7Slot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Wrapper around Inventory, that makes it a T7Container
 */
public class T7Inventory implements T7Container {
  private Inventory inventory;
  private final SlotRange range;
  private final Menu menu;

  public T7Inventory(Inventory inventory, Menu menu) {
    this.inventory = inventory;
    this.menu = menu;
    this.range = new SlotRange(menu);
  }

  @Override
  public void addSlots() {
    range.start();

    for (int i = 0; i < 3; i++) {
      menu.getSlotPose().pushX();
      for (int j = 0; j < 9; j++) {
        menu.addSlot(new T7Slot(this, j + i * 9 + 9, menu, 18));
      }
      menu.getSlotPose().popX();
      menu.getSlotPose().translateY(18);
    }

    menu.getSlotPose().translateY(4);
    for (int i = 0; i < 9; i++) {
      menu.addSlot(new T7Slot(this, i, menu, 18));
    }

    range.end();
  }

  @Override
  public SlotRange getRange() {
    return range;
  }

  @Override
  public int getContainerSize() {
    return inventory.getContainerSize();
  }

  @Override
  public boolean isEmpty() {
    return inventory.isEmpty();
  }

  @Override
  public ItemStack getItem(int slot) {
    return inventory.getItem(slot);
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    return inventory.removeItem(slot, amount);
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    return inventory.removeItemNoUpdate(slot);
  }

  @Override
  public void setItem(int slot, ItemStack stack) {
    inventory.setItem(slot, stack);
  }

  @Override
  public void setChanged() {
    inventory.setChanged();
  }

  @Override
  public boolean stillValid(Player player) {
    return inventory.stillValid(player);
  }

  @Override
  public void clearContent() {
    inventory.clearContent();
  }

  public Player getPlayer() {
    return inventory.player;
  }
}
