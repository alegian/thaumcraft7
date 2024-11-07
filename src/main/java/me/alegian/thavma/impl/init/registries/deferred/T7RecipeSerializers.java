package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.recipe.CrucibleRecipe;
import me.alegian.thavma.impl.common.recipe.CrucibleRecipeSerializer;
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipe;
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7RecipeSerializers {
  public static final DeferredRegister<RecipeSerializer<?>> REGISTRAR = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Thavma.MODID);

  public static final Supplier<RecipeSerializer<CrucibleRecipe>> CRUCIBLE = REGISTRAR.register("crucible", CrucibleRecipeSerializer::new);
  public static final Supplier<RecipeSerializer<WorkbenchRecipe>> ARCANE_WORKBENCH = REGISTRAR.register("arcane_workbench", WorkbenchRecipeSerializer::new);
}
