package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.recipe.CrucibleRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7RecipeTypes {
  public static final DeferredRegister<RecipeType<?>> REGISTRAR =
      DeferredRegister.create(Registries.RECIPE_TYPE, Thaumcraft.MODID);

  public static final Supplier<RecipeType<CrucibleRecipe>> CRUCIBLE =
      REGISTRAR.register(
          "crucible",
          () -> RecipeType.simple(Thaumcraft.id("crucible"))
      );
}
