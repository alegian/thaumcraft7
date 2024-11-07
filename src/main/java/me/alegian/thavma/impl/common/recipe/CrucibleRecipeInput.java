package me.alegian.thavma.impl.common.recipe;

import me.alegian.thavma.impl.common.aspect.AspectMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CrucibleRecipeInput(AspectMap aspects, ItemStack catalyst) implements RecipeInput {
  @Override
  public ItemStack getItem(int pIndex) {
    return this.catalyst();
  }

  @Override
  public int size() {
    return 1;
  }
}
