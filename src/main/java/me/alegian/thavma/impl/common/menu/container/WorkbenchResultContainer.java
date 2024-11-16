package me.alegian.thavma.impl.common.menu.container;

import me.alegian.thavma.impl.common.menu.WorkbenchMenu;
import me.alegian.thavma.impl.common.menu.slot.WorkbenchResultSlot;

public class WorkbenchResultContainer extends T7ResultContainer<WorkbenchMenu> {
  private final WorkbenchMenu menu;

  public WorkbenchResultContainer(WorkbenchMenu menu) {
    super(menu, menu.getCraftingContainer());
    this.menu = menu;
  }

  @Override
  public void addSlots() {
    this.menu.addSlot(new WorkbenchResultSlot(this.menu, 0, 26));
    this.range.track();
  }
}
