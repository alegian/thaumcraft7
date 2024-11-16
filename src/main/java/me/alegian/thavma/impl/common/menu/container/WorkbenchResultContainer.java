package me.alegian.thavma.impl.common.menu.container;

import me.alegian.thavma.impl.common.menu.ArcaneWorkbenchMenu;
import me.alegian.thavma.impl.common.menu.slot.WorkbenchResultSlot;
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class WorkbenchResultContainer extends T7ResultContainer {
  private final ArcaneWorkbenchMenu menu;

  public WorkbenchResultContainer(ArcaneWorkbenchMenu menu) {
    super(menu, menu.getCraftingContainer());
    this.menu = menu;
  }

  @Override
  public void addSlots() {
    this.menu.addSlot(new WorkbenchResultSlot(this.menu, 0, 26));
    this.range.track();
  }

  @Override
  public @Nullable RecipeHolder<WorkbenchRecipe> getRecipeUsed() {
    var superRecipe = super.getRecipeUsed();
    if (superRecipe == null) return null;
    if (superRecipe.value() instanceof WorkbenchRecipe) return (RecipeHolder<WorkbenchRecipe>) superRecipe;
    return null;
  }
}
