package me.alegian.thavma.impl.common.menu;

import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.menu.container.CraftingContainer3x3;
import me.alegian.thavma.impl.common.menu.container.T7Container;
import me.alegian.thavma.impl.common.menu.container.WandContainer;
import me.alegian.thavma.impl.common.menu.container.WorkbenchResultContainer;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import me.alegian.thavma.impl.init.registries.deferred.T7MenuTypes;
import me.alegian.thavma.impl.init.registries.deferred.T7RecipeTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.Level;

import java.util.List;

public class ArcaneWorkbenchMenu extends Menu {
  private final ContainerLevelAccess levelAccess;
  private final CraftingContainer3x3 craftingContainer = new CraftingContainer3x3(this);
  private final WandContainer wandContainer = new WandContainer(this);
  private final WorkbenchResultContainer resultContainer = new WorkbenchResultContainer(this);

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  /**
   * Slot index must be container unique, but not necessarily menu unique
   */
  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(T7MenuTypes.ARCANE_WORKBENCH.get(), pContainerId, pPlayerInventory);
    this.levelAccess = pAccess;

    this.slotPose.push(50, 42);
    this.craftingContainer.addSlots();
    this.slotPose.pop();

    this.slotPose.push(177, 36);
    this.wandContainer.addSlots();
    this.slotPose.pop();

    this.slotPose.push(28, 146);
    this.playerInventory.addSlots();
    this.slotPose.pop();

    this.slotPose.push(177, 62);
    this.resultContainer.addSlots();
    this.slotPose.pop();

    this.addSlotListener(this);
  }

  private void refreshRecipeResult() {
    Level level = this.getPlayer().level();
    CraftingInput craftinginput = this.craftingContainer.asCraftInput();
    var optionalRecipeHolder = level.getRecipeManager().getRecipeFor(T7RecipeTypes.ARCANE_WORKBENCH.get(), this.craftingContainer.asCraftInput(), level);

    var requiredAspects = optionalRecipeHolder.map(r ->
        r.value().assembleAspects()
    ).orElse(AspectMap.EMPTY);

    if (this.getPlayer() instanceof ServerPlayer serverPlayer) {
      var resultItem = optionalRecipeHolder.map(r ->
          r.value().assemble(craftinginput, level.registryAccess())
      ).orElse(ItemStack.EMPTY);
      var recipeHolder = optionalRecipeHolder.orElse(null);

      this.resultContainer.setItem(0, resultItem);
      this.resultContainer.setRecipeUsed(recipeHolder);
    }

    this.resultContainer.setSlotEnabled(0, this.wandContainer.contains(requiredAspects));
  }

  @Override
  protected List<T7Container> getQuickMovePriorities() {
    return List.of(this.wandContainer, this.craftingContainer);
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return AbstractContainerMenu.stillValid(this.levelAccess, pPlayer, T7Blocks.ARCANE_WORKBENCH.get());
  }

  public WorkbenchResultContainer getResultContainer() {
    return this.resultContainer;
  }

  public WandContainer getWandContainer() {
    return this.wandContainer;
  }

  public CraftingContainer3x3 getCraftingContainer() {
    return this.craftingContainer;
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
