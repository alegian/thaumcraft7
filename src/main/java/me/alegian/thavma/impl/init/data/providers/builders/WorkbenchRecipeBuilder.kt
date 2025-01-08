package me.alegian.thavma.impl.init.data.providers.builders

import me.alegian.thavma.impl.Thavma.rl
import me.alegian.thavma.impl.common.aspect.AspectMap
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipe
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

class WorkbenchRecipeBuilder private constructor(result: ItemLike, count: Int) : ShapedRecipeBuilder(RecipeCategory.MISC, result, count) {
    private var requiredAspects: AspectMap? = null
    private var result = ItemStack(result, count)

    fun requireAspects(requiredAspects: AspectMap?): WorkbenchRecipeBuilder {
        this.requiredAspects = requiredAspects
        return this
    }

    override fun save(recipeOutput: RecipeOutput, id: ResourceLocation) {
        val shapedrecipepattern = this.ensureValid(id)
        val advancementBuilder = recipeOutput.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR)
        criteria.forEach { (key, criterion) -> advancementBuilder.addCriterion(key, criterion) }
        val recipe = WorkbenchRecipe(
            shapedrecipepattern,
            this.result,
            this.requiredAspects
        )

        val t7id = rl(id.path).withSuffix("_arcane_workbench")
        recipeOutput.accept(t7id, recipe, advancementBuilder.build(t7id.withPrefix("recipes/")))
    }

    companion object {
        fun shaped(result: ItemLike, count: Int): WorkbenchRecipeBuilder {
            return WorkbenchRecipeBuilder(result, count)
        }
    }
}
