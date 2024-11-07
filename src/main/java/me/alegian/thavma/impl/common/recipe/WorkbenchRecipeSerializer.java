package me.alegian.thavma.impl.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class WorkbenchRecipeSerializer implements RecipeSerializer<WorkbenchRecipe> {
  public static final MapCodec<WorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
      ShapedRecipePattern.MAP_CODEC.forGetter(WorkbenchRecipe::getPattern),
      ItemStack.CODEC.fieldOf("resultItem").forGetter(WorkbenchRecipe::getResultItem),
      AspectMap.CODEC.fieldOf("resultAspects").forGetter(WorkbenchRecipe::getResultAspects)
  ).apply(inst, WorkbenchRecipe::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, WorkbenchRecipe> STREAM_CODEC =
      StreamCodec.composite(
          ShapedRecipePattern.STREAM_CODEC, WorkbenchRecipe::getPattern,
          ItemStack.STREAM_CODEC, WorkbenchRecipe::getResultItem,
          AspectMap.STREAM_CODEC, WorkbenchRecipe::getResultAspects,
          WorkbenchRecipe::new
      );

  @Override
  public MapCodec<WorkbenchRecipe> codec() {
    return CODEC;
  }

  @Override
  public StreamCodec<RegistryFriendlyByteBuf, WorkbenchRecipe> streamCodec() {
    return STREAM_CODEC;
  }
}
