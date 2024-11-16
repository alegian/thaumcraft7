package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.menu.WorkbenchMenu;
import me.alegian.thavma.impl.init.registries.deferred.T7RecipeTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WorkbenchResultSlot extends T7ResultSlot<WorkbenchMenu> {
  public WorkbenchResultSlot(WorkbenchMenu menu, int id, int size) {
    super(menu, menu.getCraftingContainer(), menu.getResultContainer(), id, size);
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
    var optionalRecipe = player.level().getRecipeManager().getRecipeFor(T7RecipeTypes.ARCANE_WORKBENCH.get(), craftinginput, player.level());

    for (int k = 0; k < craftinginput.height(); k++)
      for (int l = 0; l < craftinginput.width(); l++) {
        int currSlotIndex = l + i + (k + j) * craftingContainer.getWidth();
        var currItem = craftingContainer.getItem(currSlotIndex);
        if (!currItem.isEmpty()) craftingContainer.removeItem(currSlotIndex, 1);
      }

    AspectContainer.from(this.getMenu().getWandContainer().getItem(0)).ifPresent(c ->
        optionalRecipe.ifPresent(recipe ->
            c.extract(recipe.value().getResultAspects())
        )
    );
  }
}
