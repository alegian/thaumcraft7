package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;

/**
 * Keeps track of slot index ranges. Need to call start() and end() before the
 * addition of the first slot and after the addition of the last slot
 */
public class SlotRange {
  protected Menu menu;
  protected int start;
  protected int end;

  public SlotRange(Menu menu) {
    this.menu = menu;
    this.start = 0;
    this.end = 0;
  }

  public void start() {
    this.start = this.menu.slots.size();
  }

  public void end() {
    this.end = this.menu.slots.size() - 1;
  }

  public int getStart() {
    return this.start;
  }

  public int getEnd() {
    return this.end;
  }

  public boolean contains(int slotId) {
    return slotId >= this.start && slotId <= this.end;
  }

  /**
   * Tracking single slots should be done AFTER adding them
   */
  public static class Single extends SlotRange {
    public Single(Menu menu) {
      super(menu);
    }

    public void track() {
      this.start = this.menu.slots.size() - 1;
      this.end = this.menu.slots.size() - 1;
    }

    public boolean is(int slotId) {
      return this.contains(slotId);
    }
  }
}
