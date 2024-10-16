package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.common.menu.container.CraftingContainer3x3;
import me.alegian.thaumcraft7.impl.common.menu.container.T7Container;
import me.alegian.thaumcraft7.impl.common.menu.container.T7ResultContainer;
import me.alegian.thaumcraft7.impl.common.menu.container.WandContainer;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7MenuTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CrafterBlock;

import java.util.List;

public class ArcaneWorkbenchMenu extends Menu {
  private final ContainerLevelAccess levelAccess;
  private final CraftingContainer3x3 craftingContainer = new CraftingContainer3x3(this);
  private final WandContainer wandContainer = new WandContainer(this);
  private final T7ResultContainer resultContainer = new T7ResultContainer(this, craftingContainer);

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  /**
   * Slot index must be container unique, but not necessarily menu unique
   */
  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(T7MenuTypes.ARCANE_WORKBENCH.get(), pContainerId, pPlayerInventory);
    this.levelAccess = pAccess;

    slotPose.push(50, 42);
    craftingContainer.addSlots();
    slotPose.pop();

    slotPose.push(177, 36);
    wandContainer.addSlots();
    slotPose.pop();

    slotPose.push(28, 146);
    playerInventory.addSlots();
    slotPose.pop();

    slotPose.push(177, 62);
    resultContainer.addSlots();
    slotPose.pop();

    this.addSlotListener(this);
  }

  private void refreshRecipeResult() {
    if (this.getPlayer() instanceof ServerPlayer serverplayer) {
      Level level = serverplayer.level();
      CraftingInput craftinginput = this.craftingContainer.asCraftInput();
      ItemStack itemstack = CrafterBlock.getPotentialResults(level, craftinginput)
          .map(recipeHolder -> recipeHolder.value().assemble(craftinginput, level.registryAccess()))
          .orElse(ItemStack.EMPTY);
      this.resultContainer.setItem(0, itemstack);
    }
  }

  @Override
  protected List<T7Container> getQuickMovePriorities() {
    return List.of(wandContainer, craftingContainer);
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
}
