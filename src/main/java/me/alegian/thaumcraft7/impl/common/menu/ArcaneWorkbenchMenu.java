package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7MenuTypes;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;

public class ArcaneWorkbenchMenu extends AbstractContainerMenu {
  private final ContainerLevelAccess access;
  private final Player player;
  private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 3);
  private final ResultContainer resultSlots = new ResultContainer();


  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(T7MenuTypes.ARCANE_WORKBENCH.get(), pContainerId);
    this.access = pAccess;
    this.player = pPlayerInventory.player;

    this.addSlot(new ResultSlot(pPlayerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
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
  }

  protected static void slotChangedCraftingGrid(
      AbstractContainerMenu pMenu,
      Level pLevel,
      Player pPlayer,
      CraftingContainer pCraftSlots,
      ResultContainer pResultSlots,
      @Nullable RecipeHolder<CraftingRecipe> pRecipe
  ) {
    if (!pLevel.isClientSide) {
      CraftingInput craftinginput = pCraftSlots.asCraftInput();
      ServerPlayer serverplayer = (ServerPlayer)pPlayer;
      ItemStack itemstack = ItemStack.EMPTY;
      Optional<RecipeHolder<CraftingRecipe>> optional = pLevel.getServer()
          .getRecipeManager()
          .getRecipeFor(RecipeType.CRAFTING, craftinginput, pLevel, pRecipe);
      if (optional.isPresent()) {
        RecipeHolder<CraftingRecipe> recipeholder = optional.get();
        CraftingRecipe craftingrecipe = recipeholder.value();
        if (pResultSlots.setRecipeUsed(pLevel, serverplayer, recipeholder)) {
          ItemStack itemstack1 = craftingrecipe.assemble(craftinginput, pLevel.registryAccess());
          if (itemstack1.isItemEnabled(pLevel.enabledFeatures())) {
            itemstack = itemstack1;
          }
        }
      }

      pResultSlots.setItem(0, itemstack);
      pMenu.setRemoteSlot(0, itemstack);
      serverplayer.connection.send(new ClientboundContainerSetSlotPacket(pMenu.containerId, pMenu.incrementStateId(), 0, itemstack));
    }
  }

  /**
   * Callback for when the crafting matrix is changed.
   */
  @Override
  public void slotsChanged(Container pInventory) {
      this.access.execute((p_344363_, p_344364_) -> slotChangedCraftingGrid(this, p_344363_, this.player, this.craftSlots, this.resultSlots, null));
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
    return null;
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return stillValid(this.access, pPlayer, T7Blocks.ARCANE_WORKBENCH.get());
  }
}
