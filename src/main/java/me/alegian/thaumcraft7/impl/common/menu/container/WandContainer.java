package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import me.alegian.thaumcraft7.impl.common.menu.Menu;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.WandSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WandContainer implements T7Container {
  private final Menu menu;
  private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
  private final SlotRange.Single range;

  public WandContainer(Menu menu) {
    this.menu = menu;
    this.range = new SlotRange.Single(menu);
  }

  public boolean contains(AspectMap required){
    return AspectContainer.from(this.getItem(0)).map(container->
        container.getAspects().contains(required)
    ).orElse(false);
  }

  @Override
  public int getContainerSize() {
    return 1;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemstack : this.itemStacks) if (!itemstack.isEmpty()) return false;

    return true;
  }

  @Override
  public ItemStack getItem(int pSlot) {
    return this.itemStacks.get(0);
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

  @Override
  public void addSlots() {
    this.menu.addSlot(new WandSlot(this, 0, this.menu));
    this.range.track();
  }

  @Override
  public SlotRange getRange() {
    return this.range;
  }
}
