package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.T7ResultSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;

public class T7ResultContainer extends ResultContainer implements T7Container {
  private final Menu menu;
  private final CraftingContainer craftingContainer;
  private final SlotRange range;

  public T7ResultContainer(Menu menu, CraftingContainer craftingContainer) {
    super();
    this.menu = menu;
    this.craftingContainer = craftingContainer;
    this.range = new SlotRange(menu);
  }

  @Override
  public void addSlots() {
    menu.addSlot(new T7ResultSlot(menu, craftingContainer, this, 0, 26));
  }

  @Override
  public SlotRange getRange() {
    return range;
  }
}
