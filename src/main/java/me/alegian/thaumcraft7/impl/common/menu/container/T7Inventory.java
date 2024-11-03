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
    this.range.start();

    for (int i = 0; i < 3; i++) {
      this.menu.getSlotPose().pushX();
      for (int j = 0; j < 9; j++) this.menu.addSlot(new T7Slot(this, j + i * 9 + 9, this.menu, 18));
      this.menu.getSlotPose().popX();
      this.menu.getSlotPose().translateY(18);
    }

    this.menu.getSlotPose().translateY(4);
    for (int i = 0; i < 9; i++) this.menu.addSlot(new T7Slot(this, i, this.menu, 18));

    this.range.end();
  }

  @Override
  public SlotRange getRange() {
    return this.range;
  }

  @Override
  public int getContainerSize() {
    return this.inventory.getContainerSize();
  }

  @Override
  public boolean isEmpty() {
    return this.inventory.isEmpty();
  }

  @Override
  public ItemStack getItem(int slot) {
    return this.inventory.getItem(slot);
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    return this.inventory.removeItem(slot, amount);
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    return this.inventory.removeItemNoUpdate(slot);
  }

  @Override
  public void setItem(int slot, ItemStack stack) {
    this.inventory.setItem(slot, stack);
  }

  @Override
  public void setChanged() {
    this.inventory.setChanged();
  }

  @Override
  public boolean stillValid(Player player) {
    return this.inventory.stillValid(player);
  }

  @Override
  public void clearContent() {
    this.inventory.clearContent();
  }

  public Player getPlayer() {
    return this.inventory.player;
  }
}
