package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.slot.SlotPose;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.T7Slot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Menu extends AbstractContainerMenu implements ContainerListener {
  protected final SlotPose slotPose = new SlotPose();

  protected Menu(@Nullable MenuType<?> menuType, int containerId) {
    super(menuType, containerId);
  }

  public Slot addSlot(T7Slot slot) {
    slotPose.translateX(slot.getSize());
    return super.addSlot(slot);
  }

  protected boolean moveItemStackToRange(ItemStack slotItem, SlotRange range) {
    return this.moveItemStackTo(slotItem, range.getStart(), range.getEnd() + 1, false);
  }

  public SlotPose getSlotPose() {
    return slotPose;
  }

  @Override
  public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
  }
}
