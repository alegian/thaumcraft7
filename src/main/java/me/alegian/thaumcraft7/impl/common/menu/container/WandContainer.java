package me.alegian.thaumcraft7.impl.common.menu.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class WandContainer implements Container{
  private final AbstractContainerMenu menu;
  private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);

  public WandContainer(AbstractContainerMenu menu) {
    this.menu = menu;
  }

  @Override
  public int getContainerSize() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemstack : this.itemStacks) {
      if (!itemstack.isEmpty()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public ItemStack getItem(int pSlot) {
    return itemStacks.get(0);
  }

  @Override
  public ItemStack removeItem(int pSlot, int pAmount) {
    return ContainerHelper.takeItem(this.itemStacks, 0);
  }

  @Override
  public ItemStack removeItemNoUpdate(int pSlot) {
    return ContainerHelper.takeItem(this.itemStacks, 0);
  }

  @Override
  public void setItem(int pSlot, ItemStack pStack) {
    this.itemStacks.set(0, pStack);
    this.menu.slotsChanged(this);
  }

  @Override
  public void setChanged() {
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return true;
  }

  @Override
  public void clearContent() {
    this.itemStacks.clear();
  }
}
