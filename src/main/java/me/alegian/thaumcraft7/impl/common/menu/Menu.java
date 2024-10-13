package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.container.T7Container;
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

import java.util.List;

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

  /**
   * slotIndex is relative to this.slots and NOT slot id
   */
  @Override
  public ItemStack quickMoveStack(Player player, int slotIndex) {
    ItemStack originalItem = ItemStack.EMPTY;
    Slot slot = this.slots.get(slotIndex);
    if (slot.hasItem()) {
      ItemStack slotItem = slot.getItem();
      originalItem = slotItem.copy();

      // attempt to move stack, one by one to each target container, until one succeeds
      // zeros are converted to EMPTY. auto-updates dest slot
      boolean isInventorySlot = playerInventory.getRange().contains(slotIndex);
      if (isInventorySlot) {
        if (getQuickMovePriorities().stream().noneMatch(container ->
            moveItemStackToRange(slotItem, container.getRange()))
        ) {
          return ItemStack.EMPTY;
        }
      } else if (!moveItemStackToRange(slotItem, playerInventory.getRange())) {
        return ItemStack.EMPTY;
      }

      // update source slot, zeros are converted to EMPTY
      if (slotItem.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      } else {
        slot.setChanged();
      }

      // if nothing was done (there is no space), signal to avoid retrying
      if (slotItem.getCount() == originalItem.getCount()) {
        return ItemStack.EMPTY;
      }

      slot.onTake(player, slotItem);
    }

    return originalItem;
  }

  protected List<T7Container> getQuickMovePriorities() {
    return List.of();
  }

  @Override
  public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
  }
}
