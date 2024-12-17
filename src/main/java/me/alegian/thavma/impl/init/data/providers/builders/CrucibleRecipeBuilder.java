package me.alegian.thavma.impl.init.data.providers.builders;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.recipe.CrucibleRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedHashMap;
import java.util.Map;

public class CrucibleRecipeBuilder implements RecipeBuilder {
  private final ItemStack result;
  private final AspectMap aspects;
  private final Ingredient catalyst;
  private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

  public CrucibleRecipeBuilder(ItemStack result, AspectMap aspects, Ingredient catalyst) {
    this.result = result;
    this.aspects = aspects;
    this.catalyst = catalyst;
  }

  @Override
  public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
    this.criteria.put(name, criterion);
    return this;
  }

  @Override
  public RecipeBuilder group(String group) {
    return this;
  }

  @Override
  public Item getResult() {
    return this.result.getItem();
  }

  @Override
  public void save(RecipeOutput output, ResourceLocation id) {
    Advancement.Builder advancement = output.advancement()
        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
        .rewards(AdvancementRewards.Builder.recipe(id))
        .requirements(AdvancementRequirements.Strategy.OR);
    this.criteria.forEach(advancement::addCriterion);

    var recipe = new CrucibleRecipe(this.aspects, this.catalyst, this.result);

    id = Thavma.Companion.rl(id.getPath()).withSuffix("_crucible");
    output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
  }
}
