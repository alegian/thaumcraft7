package me.alegian.thaumcraft7.impl.common.menu;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class ArcaneWorkbenchMenu extends CraftingMenu {
  private final ContainerLevelAccess levelAccess;

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory) {
    this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
  }

  public ArcaneWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
    super(pContainerId, pPlayerInventory, pAccess);
    levelAccess = pAccess;
  }

  @Override
  public boolean stillValid(Player pPlayer) {
    return stillValid(this.levelAccess, pPlayer, T7Blocks.ARCANE_WORKBENCH.get());
  }
}
