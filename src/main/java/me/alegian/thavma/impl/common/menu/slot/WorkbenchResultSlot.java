package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.menu.ArcaneWorkbenchMenu;
import me.alegian.thavma.impl.common.menu.container.WandContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;

public class WorkbenchResultSlot extends T7ResultSlot<ArcaneWorkbenchMenu> {
  public WorkbenchResultSlot(ArcaneWorkbenchMenu menu, CraftingContainer craftingContainer, WandContainer wandContainer, ResultContainer container, int id, int size) {
    super(menu, craftingContainer, container, id, size);
  }

  /**
   * On taking result, also remove vis from wand
   */
  @Override
  public void onTake(Player player, ItemStack stack) {
    super.onTake(player, stack);
    AspectContainer.from(this.getMenu().getWandContainer().getItem(0)).ifPresent(c -> {
      var recipeHolder = this.getMenu().getResultContainer().getRecipeUsed();

      if (recipeHolder != null) {
        var aspectsRequired = recipeHolder.value().getResultAspects();
        c.extract(aspectsRequired);
      }
    });
  }
}
