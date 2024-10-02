package me.alegian.thaumcraft7.impl.common.recipe;

import me.alegian.thaumcraft7.impl.common.aspect.AspectList;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7RecipeSerializers;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7RecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class CrucibleRecipe implements Recipe<CrucibleRecipeInput> {
  private final AspectList requiredAspects;
  private final Ingredient requiredCatalyst;
  private final ItemStack result;

  /**
   * Catalyst Items should be tagged as such, otherwise the crucible is going to melt them anyway
   */
  public CrucibleRecipe(AspectList requiredAspects, Ingredient requiredCatalyst, ItemStack result) {
    this.requiredAspects = requiredAspects;
    this.requiredCatalyst = requiredCatalyst;
    this.result = result;
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return NonNullList.of(this.requiredCatalyst);
  }

  public AspectList getRequiredAspects() {
    return this.requiredAspects;
  }

  public Ingredient getRequiredCatalyst() {
    return this.requiredCatalyst;
  }

  public ItemStack getResult() {
    return this.result;
  }

  @Override
  public boolean matches(CrucibleRecipeInput input, Level level) {
    return input.aspects().contains(this.requiredAspects) && this.requiredCatalyst.test(input.catalyst());
  }

  @Override
  public ItemStack assemble(CrucibleRecipeInput pInput, HolderLookup.Provider pRegistries) {
    return this.result.copy();
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return pWidth * pHeight >= 1;
  }

  /**
   * For recipe book & JEI
   */
  @Override
  public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
    return this.result;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return T7RecipeSerializers.CRUCIBLE.get();
  }

  @Override
  public RecipeType<?> getType() {
    return T7RecipeTypes.CRUCIBLE.get();
  }
}
