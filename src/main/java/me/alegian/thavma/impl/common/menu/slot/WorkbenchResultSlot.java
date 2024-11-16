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
   * On taking result, also remove vis from wand.
   * TODO: support craft remainders
   */
  @Override
  public void onTake(Player player, ItemStack stack) {
    var craftingContainer = this.getMenu().getCraftingContainer();
    var positionedInput = craftingContainer.asPositionedCraftInput();
    var craftinginput = positionedInput.input();
    int i = positionedInput.left();
    int j = positionedInput.top();

    for (int k = 0; k < craftinginput.height(); k++)
      for (int l = 0; l < craftinginput.width(); l++) {
        int currSlotIndex = l + i + (k + j) * craftingContainer.getWidth();
        var currItem = craftingContainer.getItem(currSlotIndex);
        if (!currItem.isEmpty()) craftingContainer.removeItem(currSlotIndex, 1);
      }

    AspectContainer.from(this.getMenu().getWandContainer().getItem(0)).ifPresent(c -> {
      var recipeHolder = this.getMenu().getResultContainer().getRecipeUsed();

      if (recipeHolder != null) {
        var aspectsRequired = recipeHolder.value().getResultAspects();
        c.extract(aspectsRequired);
      }
    });
  }
}
