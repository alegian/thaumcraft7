package me.alegian.thaumcraft7.impl.common.menu.slot;

import net.minecraft.world.inventory.Slot;

import java.util.List;

/**
 * Keeps track of slot index ranges. Need to call start() and end() before the
 * addition of the first slot and after the addition of the last slot
 */
public class SlotRange {
  protected int start;
  protected int end;

  public SlotRange() {
    this.start = 0;
    this.end = 0;
  }

  public void start(List<Slot> slots) {
    this.start = slots.size();
  }

  public void end(List<Slot> slots) {
    this.end = slots.size() - 1;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public boolean contains(int slotId) {
    return slotId >= start && slotId <= end;
  }

  /**
   * Tracking single slots should be done AFTER adding them
   */
  public static class Single extends SlotRange {
    public void track(List<Slot> slots) {
      this.start = slots.size() - 1;
      this.end = slots.size() - 1;
    }

    public boolean is(int slotId) {
      return contains(slotId);
    }
  }
}
