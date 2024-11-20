package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.recipe.CrucibleRecipe;
import me.alegian.thavma.impl.common.recipe.WorkbenchRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7RecipeTypes {
  public static final DeferredRegister<RecipeType<?>> REGISTRAR =
      DeferredRegister.create(Registries.RECIPE_TYPE, Thavma.MODID);

  public static final Supplier<RecipeType<CrucibleRecipe>> CRUCIBLE =
      T7RecipeTypes.REGISTRAR.register(
          "crucible",
          () -> RecipeType.simple(Thavma.rl("crucible"))
      );
  public static final Supplier<RecipeType<WorkbenchRecipe>> ARCANE_WORKBENCH =
      T7RecipeTypes.REGISTRAR.register(
          "arcane_workbench",
          () -> RecipeType.simple(Thavma.rl("arcane_workbench"))
      );
}
