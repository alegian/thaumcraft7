package me.alegian.thaumcraft7.impl.common.menu.container;

import me.alegian.thaumcraft7.impl.common.menu.Menu;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.T7Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;

public class CraftingContainer3x3 extends TransientCraftingContainer implements T7Container {
  private final Menu menu;
  private final SlotRange range;

  public CraftingContainer3x3(Menu menu) {
    super(menu, 3, 3);
    this.menu = menu;
    this.range = new SlotRange(menu);
  }

  @Override
  public void addSlots() {
    range.start();
    for (int i = 0; i < 3; i++) {
      menu.getSlotPose().pushX();
      for (int j = 0; j < 3; j++) {
        menu.addSlot(new T7Slot(this, j + i * 3, menu, 18));
      }
      menu.getSlotPose().popX();
      menu.getSlotPose().translateY(18);
    }
    range.end();
  }

  @Override
  public SlotRange getRange() {
    return range;
  }
}
