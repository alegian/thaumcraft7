package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7MenuTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CrafterBlock;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu implements ContainerListener {
  private final ContainerLevelAccess levelAccess;
  private final Player player;
  private final CraftingContainer craftingContainer = new TransientCraftingContainer(this, 3, 3);
  private final ResultContainer resultContainer = new ResultContainer();


  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(T7MenuTypes.ARCANE_WORKBENCH.get(), pContainerId);
    this.levelAccess = pAccess;
    this.player = pPlayerInventory.player;

    this.addSlot(new ResultSlot(pPlayerInventory.player, this.craftingContainer, this.resultContainer, 0, 124, 35));

    this.addSlot(new Slot(this.craftingContainer, 1, 124, 9));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        this.addSlot(new Slot(this.craftingContainer, j + i * 3, 30 + j * 18, 17 + i * 18));
      }
    }

    for (int k = 0; k < 3; k++) {
      for (int i1 = 0; i1 < 9; i1++) {
        this.addSlot(new Slot(pPlayerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
      }
    }

    for (int l = 0; l < 9; l++) {
      this.addSlot(new Slot(pPlayerInventory, l, 8 + l * 18, 142));
    }

    this.addSlotListener(this);
  }

  private void refreshRecipeResult() {
    if (this.player instanceof ServerPlayer serverplayer) {
      Level level = serverplayer.level();
      CraftingInput craftinginput = this.craftingContainer.asCraftInput();
      ItemStack itemstack = CrafterBlock.getPotentialResults(level, craftinginput)
          .map(p_344359_ -> p_344359_.value().assemble(craftinginput, level.registryAccess()))
          .orElse(ItemStack.EMPTY);
      this.resultContainer.setItem(0, itemstack);
    }
  }

  /**
   * Returns the modified ItemStack after the quick-move.
   * This method may run multiple times if the quick-moved stack
   * needs to be split across multiple slots.
   * Returning EMPTY has special meaning: it signals that
   * there is nothing more to be done, and there is no point
   * retrying. Not returning it when needed may cause infinite loops.
   */
  @Override
  public ItemStack quickMoveStack(Player player, int slotIndex) {
    ItemStack originalItem = ItemStack.EMPTY;
    Slot slot = this.slots.get(slotIndex);
    if (slot.hasItem()) {
      ItemStack slotItem = slot.getItem();
      originalItem = slotItem.copy();

      // try to move stack, making sure zeros are converted to EMPTY. auto-updates dest slot
      if (slotIndex < 9) {
        if (!this.moveItemStackTo(slotItem, 9, 45, true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(slotItem, 0, 9, false)) {
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

  @Override
  public boolean stillValid(Player pPlayer) {
    return stillValid(this.levelAccess, pPlayer, T7Blocks.ARCANE_WORKBENCH.get());
  }

  @Override
  public void removed(Player pPlayer) {
    super.removed(pPlayer);
    this.levelAccess.execute((level, blockPos) -> this.clearContainer(pPlayer, this.craftingContainer));
  }

  @Override
  public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {
    this.refreshRecipeResult();
  }

  @Override
  public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
  }
}
