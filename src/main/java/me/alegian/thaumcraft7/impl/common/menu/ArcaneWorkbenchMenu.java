package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.container.WandContainer;
import me.alegian.thaumcraft7.impl.common.menu.slot.WandSlot;
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
  private final WandContainer wandContainer = new WandContainer(this);

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  /**
   * Slot index must be container unique, but not necessarily menu unique
   */
  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(T7MenuTypes.ARCANE_WORKBENCH.get(), pContainerId);
    this.levelAccess = pAccess;
    this.player = pPlayerInventory.player;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        this.addSlot(new Slot(this.craftingContainer, j + i * 3, 50 + j * 18, 37 + i * 18));
      }
    }

    this.addSlot(new WandSlot(this.wandContainer, 0, 175, 29));

    for (int k = 0; k < 3; k++) {
      for (int i1 = 0; i1 < 9; i1++) {
        this.addSlot(new Slot(pPlayerInventory, i1 + k * 9 + 9, 28 + i1 * 18, 124 + k * 18));
      }
    }

    for (int l = 0; l < 9; l++) {
      this.addSlot(new Slot(pPlayerInventory, l, 28 + l * 18, 182));
    }

    this.addSlot(new ResultSlot(pPlayerInventory.player, this.craftingContainer, this.resultContainer, 0, 175, 55));

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
   * slotIndex is relative to this.slots and NOT slot id
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
        if (!this.moveItemStackTo(slotItem, 10, 46, true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.moveItemStackTo(slotItem, 0, 10, false)) {
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
    this.levelAccess.execute((level, blockPos) -> this.clearContainer(pPlayer, this.wandContainer));
  }

  @Override
  public void slotChanged(AbstractContainerMenu pContainerToSend, int pDataSlotIndex, ItemStack pStack) {
    this.refreshRecipeResult();
  }

  @Override
  public void dataChanged(AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
  }
}
