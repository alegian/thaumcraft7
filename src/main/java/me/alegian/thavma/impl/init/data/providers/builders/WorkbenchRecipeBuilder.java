package me.alegian.thavma.impl.init.data.providers.builders;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;

public class WorkbenchRecipeBuilder extends ShapedRecipeBuilder {
  private AspectMap requiredAspects;
  private ItemStack result;

  private WorkbenchRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
    super(category, result, count);
  }

  public WorkbenchRecipeBuilder(ItemLike resultItem, int itemCount) {
    this(RecipeCategory.MISC, resultItem, itemCount);
    this.result = new ItemStack(resultItem, itemCount);
  }

  public WorkbenchRecipeBuilder requireAspects(AspectMap requiredAspects) {
    this.requiredAspects = requiredAspects;
    return this;
  }

  public static WorkbenchRecipeBuilder shaped(ItemLike result, int count) {
    return new WorkbenchRecipeBuilder(result, count);
  }

  @Override
  public void save(RecipeOutput recipeOutput, ResourceLocation id) {
    ShapedRecipePattern shapedrecipepattern = this.ensureValid(id);
    Advancement.Builder advancement$builder = recipeOutput.advancement()
        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
        .rewards(AdvancementRewards.Builder.recipe(id))
        .requirements(AdvancementRequirements.Strategy.OR);
    this.criteria.forEach(advancement$builder::addCriterion);
    var recipe = new WorkbenchRecipe(
        shapedrecipepattern,
        this.result,
        this.requiredAspects
    );

    id = Thavma.id(id.getPath()).withSuffix("_arcane_workbench");
    recipeOutput.accept(id, recipe, advancement$builder.build(id.withPrefix("recipes/")));
  }
}
