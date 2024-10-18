package me.alegian.thaumcraft7.impl.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrucibleRecipeSerializer implements RecipeSerializer<CrucibleRecipe> {
  public static final MapCodec<CrucibleRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
      AspectMap.CODEC.fieldOf("aspects").forGetter(CrucibleRecipe::getRequiredAspects),
      Ingredient.CODEC.fieldOf("catalyst").forGetter(CrucibleRecipe::getRequiredCatalyst),
      ItemStack.CODEC.fieldOf("result").forGetter(CrucibleRecipe::getResult)
  ).apply(inst, CrucibleRecipe::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> STREAM_CODEC =
      StreamCodec.composite(
          AspectMap.STREAM_CODEC, CrucibleRecipe::getRequiredAspects,
          Ingredient.CONTENTS_STREAM_CODEC, CrucibleRecipe::getRequiredCatalyst,
          ItemStack.STREAM_CODEC, CrucibleRecipe::getResult,
          CrucibleRecipe::new
      );

  @Override
  public MapCodec<CrucibleRecipe> codec() {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> streamCodec() {
    return STREAM_CODEC;
  }
}
