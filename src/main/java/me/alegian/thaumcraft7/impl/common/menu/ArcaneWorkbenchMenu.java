package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.container.WandContainer;
import me.alegian.thaumcraft7.impl.common.menu.slot.SlotRange;
import me.alegian.thaumcraft7.impl.common.menu.slot.*;
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
  private final SlotPose slotPose = new SlotPose();
  private final SlotRange inventoryRange = new SlotRange();
  private final SlotRange craftingRange = new SlotRange();
  private final SlotRange.Single wandIndex = new SlotRange.Single();

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

    craftingRange.start(slots);
    slotPose.push(50, 42);
    for (int i = 0; i < 3; i++) {
      slotPose.pushX();
      for (int j = 0; j < 3; j++) {
        addSlot(new T7Slot(this.craftingContainer, j + i * 3, slotPose, 18));
      }
      slotPose.popX();
      slotPose.translateY(18);
    }
    slotPose.pop();
    craftingRange.end(slots);

    slotPose.push(177, 36);
    this.addSlot(new WandSlot(this.wandContainer, 0, slotPose));
    slotPose.pop();
    wandIndex.track(slots);

    inventoryRange.start(slots);
    slotPose.push(28, 146);
    for (int i = 0; i < 3; i++) {
      slotPose.pushX();
      for (int j = 0; j < 9; j++) {
        this.addSlot(new T7Slot(pPlayerInventory, j + i * 9 + 9, slotPose, 18));
      }
      slotPose.popX();
      slotPose.translateY(18);
    }

    slotPose.translateY(4);
    for (int i = 0; i < 9; i++) {
      this.addSlot(new T7Slot(pPlayerInventory, i, slotPose, 18));
    }
    slotPose.pop();
    inventoryRange.end(slots);

    slotPose.push(177, 62);
    this.addSlot(new T7ResultSlot(pPlayerInventory.player, this.craftingContainer, this.resultContainer, 0, slotPose, 26));
    slotPose.pop();

    this.addSlotListener(this);
  }

  protected Slot addSlot(T7Slot slot) {
    slotPose.translateX(slot.getSize());
    return super.addSlot(slot);
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
      boolean isInventorySlot = inventoryRange.contains(slotIndex);
      if (isInventorySlot) {
        if (!moveItemStackToRange(slotItem, wandIndex) &&
            !moveItemStackToRange(slotItem, craftingRange)
        ) {
          return ItemStack.EMPTY;
        }
      } else if (!moveItemStackToRange(slotItem, inventoryRange)) {
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

  private boolean moveItemStackToRange(ItemStack slotItem, SlotRange range) {
    return this.moveItemStackTo(slotItem, range.getStart(), range.getEnd() + 1, false);
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
