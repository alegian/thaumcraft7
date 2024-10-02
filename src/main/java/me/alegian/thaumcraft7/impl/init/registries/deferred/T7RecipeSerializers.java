package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.recipe.CrucibleRecipe;
import me.alegian.thaumcraft7.impl.common.recipe.CrucibleRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7RecipeSerializers {
  public static final DeferredRegister<RecipeSerializer<?>> REGISTRAR =
      DeferredRegister.create(Registries.RECIPE_SERIALIZER, Thaumcraft.MODID);

  public static final Supplier<RecipeSerializer<CrucibleRecipe>> CRUCIBLE =
      REGISTRAR.register("crucible", CrucibleRecipeSerializer::new);
}
