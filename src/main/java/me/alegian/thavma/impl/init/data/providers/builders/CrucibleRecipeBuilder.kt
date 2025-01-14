package me.alegian.thavma.impl.init.data.providers.builders

import me.alegian.thavma.impl.Thavma.rl
import me.alegian.thavma.impl.common.aspect.AspectMap
import me.alegian.thavma.impl.common.recipe.CrucibleRecipe
import net.minecraft.advancements.AdvancementRequirements
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.advancements.Criterion
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

class CrucibleRecipeBuilder(private val result: ItemStack, private val aspects: AspectMap, private val catalyst: Ingredient) : RecipeBuilder {
    private val criteria: MutableMap<String, Criterion<*>> = LinkedHashMap()

    override fun unlockedBy(name: String, criterion: Criterion<*>): RecipeBuilder {
        criteria[name] = criterion
        return this
    }

    override fun group(group: String?): RecipeBuilder {
        return this
    }

    override fun getResult(): Item {
        return result.item
    }

    override fun save(output: RecipeOutput, id: ResourceLocation) {
        val advancement = output.advancement()
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
            .rewards(AdvancementRewards.Builder.recipe(id))
            .requirements(AdvancementRequirements.Strategy.OR)
        criteria.forEach { (key, criterion) -> advancement.addCriterion(key, criterion) }

        val recipe = CrucibleRecipe(this.aspects, this.catalyst, this.result)

        val t7id = rl(id.path).withSuffix("_crucible")
        output.accept(t7id, recipe, advancement.build(t7id.withPrefix("recipes/")))
    }
}