package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.container.T7Inventory;
import me.alegian.thaumcraft7.impl.common.menu.slot.Sized;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotPose;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * An AbstractContainerMenu that has a SlotPose and a player Inventory.
 * Used to dynamically position Slots, and dynamically generate the
 * quick-move operations between all children containers.
 */
public abstract class Menu extends AbstractContainerMenu implements ContainerListener {
  protected final SlotPose slotPose = new SlotPose();
  protected T7Inventory playerInventory;

  protected Menu(@Nullable MenuType<?> menuType, int containerId, Inventory playerInventory) {
    super(menuType, containerId);
    this.playerInventory = new T7Inventory(playerInventory, this);
  }

  public Slot addSlot(Slot slot) {
    if (slot instanceof Sized sizedSlot) slotPose.translateX(sizedSlot.getSize());
    else slotPose.translateX(18);
    return super.addSlot(slot);
  }

  protected boolean moveItemStackToRange(ItemStack slotItem, SlotRange range) {
    return this.moveItemStackTo(slotItem, range.getStart(), range.getEnd() + 1, false);
  }

  public SlotPose getSlotPose() {
    return slotPose;
  }

  public Player getPlayer() {
    return this.playerInventory.getPlayer();
  }

  @Override
  public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
  }
}
