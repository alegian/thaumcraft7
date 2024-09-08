package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    wandHandle(pRecipeOutput, T7Items.IRON_HANDLE.get(), Items.IRON_NUGGET);
    wand(pRecipeOutput, T7Items.IRON_WOOD_WAND.get(), T7Items.IRON_HANDLE.get(), Tags.Items.RODS_WOODEN);
    ingot(pRecipeOutput, T7Items.ARCANUM_INGOT.get(), T7Items.ARCANUM_NUGGET.get(), T7Blocks.ARCANUM_BLOCK.get());
    ingot(pRecipeOutput, T7Items.ORICHALCUM_INGOT.get(), T7Items.ORICHALCUM_NUGGET.get(), T7Blocks.ORICHALCUM_BLOCK.get());

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, T7Items.ARCANUM_SWORD.get())
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

  protected static void wandHandle(RecipeOutput pRecipeOutput, ItemLike cap, ItemLike material) {
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, cap)
        .define('m', material)
        .pattern("mmm")
        .pattern("m m")
        .group("wand_cap")
        .unlockedBy(getHasName(material), has(material))
        .save(pRecipeOutput);
  }

  protected static void planksFromLog(RecipeOutput pRecipeOutput, ItemLike pPlanks, ItemLike pLog) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pPlanks, 4)
        .requires(pLog)
        .group("planks")
        .unlockedBy("has_logs", has(pLog))
        .save(pRecipeOutput);
  }
}
