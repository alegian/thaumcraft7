package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.T7ResultSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;

public class T7ResultContainer extends ResultContainer implements T7Container {
  private final Menu menu;
  private final CraftingContainer craftingContainer;
  private final SlotRange.Single range;

  public T7ResultContainer(Menu menu, CraftingContainer craftingContainer) {
    super();
    this.menu = menu;
    this.craftingContainer = craftingContainer;
    this.range = new SlotRange.Single(menu);
  }

  @Override
  public void addSlots() {
    this.menu.addSlot(new T7ResultSlot(this.menu, this.craftingContainer, this, 0, 26));
    this.range.track();
  }

  @Override
  public SlotRange getRange() {
    return this.range;
  }

  public T7ResultSlot getSlot(int index) {
    if (this.menu.getSlot(this.range.getStart() + index) instanceof T7ResultSlot slot) return slot;
    throw new IllegalArgumentException("Thaumcraft Exception: T7ResultContainer contains a slot which is not a T7ResultSlot");
  }

  public void setSlotEnabled(int index, boolean enabled) {
    this.getSlot(index).setMayPickup(enabled);
  }
}
