package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;

/**
 * A Sized ResultSlot, with a back-reference to a Menu
 */
public class T7ResultSlot<T extends Menu> extends ResultSlot implements Sized {
  private final int size;
  private boolean mayPickup = true;
  private final T menu;

  public T7ResultSlot(T menu, CraftingContainer craftingContainer, ResultContainer container, int id, int size) {
    super(menu.getPlayer(), craftingContainer, container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
    this.menu = menu;
  }

  public T getMenu() {
    return this.menu;
  }

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public void onTake(Player player, ItemStack stack) {
  }

  public void setMayPickup(boolean mayPickup) {
    this.mayPickup = mayPickup;
  }

  @Override
  public boolean mayPickup(Player player) {
    return this.mayPickup;
  }
}
