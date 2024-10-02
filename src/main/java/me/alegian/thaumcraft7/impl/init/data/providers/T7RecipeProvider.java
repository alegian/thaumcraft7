package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.common.aspect.AspectList;
import me.alegian.thaumcraft7.impl.common.aspect.AspectStack;
import me.alegian.thaumcraft7.impl.init.data.providers.builders.CrucibleRecipeBuilder;
import me.alegian.thaumcraft7.impl.init.registries.deferred.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class T7RecipeProvider extends RecipeProvider {
  public T7RecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
    super(pOutput, pRegistries);
  }

  @Override
  protected void buildRecipes(RecipeOutput pRecipeOutput) {
    planksFromLog(pRecipeOutput, T7Blocks.GREATWOOD_PLANKS, T7Blocks.GREATWOOD_LOG);
    planksFromLog(pRecipeOutput, T7Blocks.SILVERWOOD_PLANKS, T7Blocks.SILVERWOOD_LOG);
    wandHandle(pRecipeOutput, T7Items.IRON_HANDLE.get(), Items.IRON_INGOT, Items.IRON_NUGGET);
    wand(pRecipeOutput, T7Items.wand(WandHandleMaterials.IRON, WandCoreMaterials.WOOD), T7Items.IRON_HANDLE.get(), Tags.Items.RODS_WOODEN);
    ingot(pRecipeOutput, T7Items.ARCANUM_INGOT.get(), T7Items.ARCANUM_NUGGET.get(), T7Blocks.ARCANUM_BLOCK.get());
    ingot(pRecipeOutput, T7Items.ORICHALCUM_INGOT.get(), T7Items.ORICHALCUM_NUGGET.get(), T7Blocks.ORICHALCUM_BLOCK.get());

    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, T7Items.ARCANUM_SWORD.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern(" a ")
        .pattern(" a ")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_PICKAXE.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern("aaa")
        .pattern(" s ")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_HAMMER.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern("aaa")
        .pattern("aaa")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_AXE.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern("aa ")
        .pattern("as ")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_SHOVEL.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern(" a ")
        .pattern(" s ")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_HOE.get())
        .define('a', T7Items.ARCANUM_INGOT.get())
        .define('s', Tags.Items.RODS_WOODEN)
        .pattern("aa ")
        .pattern(" s ")
        .pattern(" s ")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, T7Items.ARCANUM_HELMET)
        .define('a', T7Items.ARCANUM_INGOT)
        .pattern("aaa")
        .pattern("a a")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, T7Items.ARCANUM_CHESTPLATE)
        .define('a', T7Items.ARCANUM_INGOT)
        .pattern("a a")
        .pattern("aaa")
        .pattern("aaa")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, T7Items.ARCANUM_LEGGINGS)
        .define('a', T7Items.ARCANUM_INGOT)
        .pattern("aaa")
        .pattern("a a")
        .pattern("a a")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);
    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, T7Items.ARCANUM_BOOTS)
        .define('a', T7Items.ARCANUM_INGOT)
        .pattern("a a")
        .pattern("a a")
        .unlockedBy(getHasName(T7Items.ARCANUM_INGOT.get()), has(T7Items.ARCANUM_INGOT.get()))
        .save(pRecipeOutput);

    inCrucible(pRecipeOutput,
        new ItemStack(Items.DIAMOND),
        AspectList.of(AspectStack.of(Aspects.TERRA.get(), 6), AspectStack.of(Aspects.PERDITIO.get(), 2)),
        Ingredient.of(Items.DRAGON_EGG)
    );
  }

  protected static void ingot(RecipeOutput pRecipeOutput, ItemLike ingot, ItemLike nugget, ItemLike block) {
    nineBlockStorageRecipesRecipesWithCustomUnpacking(
        pRecipeOutput, RecipeCategory.MISC, ingot, RecipeCategory.BUILDING_BLOCKS, block, getItemName(ingot) + "_from_iron_block", getItemName(ingot)
    );
    nineBlockStorageRecipesWithCustomPacking(
        pRecipeOutput, RecipeCategory.MISC, nugget, RecipeCategory.MISC, ingot, getItemName(ingot) + "_from_nuggets", getItemName(ingot)
    );
  }

  protected static void wand(RecipeOutput pRecipeOutput, ItemLike wand, ItemLike handle, ItemLike core) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, wand)
        .define('h', handle)
        .define('c', core)
        .pattern("  c")
        .pattern(" c ")
        .pattern("h  ")
        .group("wand")
        .unlockedBy(getHasName(handle), has(handle))
        .save(pRecipeOutput);
  }

  // for wooden cores
  protected static void wand(RecipeOutput pRecipeOutput, ItemLike wand, ItemLike handle, TagKey<Item> core) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, wand)
        .define('h', handle)
        .define('c', core)
        .pattern("  c")
        .pattern(" c ")
        .pattern("h  ")
        .group("wand")
        .unlockedBy(getHasName(handle), has(handle))
        .save(pRecipeOutput);
  }

  protected static void wandHandle(RecipeOutput pRecipeOutput, ItemLike cap, ItemLike ingot, ItemLike nugget) {
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, cap)
        .define('i', ingot)
        .define('n', nugget)
        .pattern(" n ")
        .pattern(" in")
        .pattern("i  ")
        .group("wand_handle")
        .unlockedBy(getHasName(ingot), has(ingot))
        .save(pRecipeOutput);
  }

  protected static void planksFromLog(RecipeOutput pRecipeOutput, ItemLike pPlanks, ItemLike pLog) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pPlanks, 4)
        .requires(pLog)
        .group("planks")
        .unlockedBy("has_logs", has(pLog))
        .save(pRecipeOutput);
  }

  protected static void inCrucible(RecipeOutput output, ItemStack result, AspectList aspects, Ingredient catalyst) {
    var catalystItem = catalyst.getItems()[0].getItem();
    new CrucibleRecipeBuilder(result, aspects, catalyst)
        .unlockedBy(getHasName(catalystItem), has(catalystItem))
        .save(output);
  }
}
