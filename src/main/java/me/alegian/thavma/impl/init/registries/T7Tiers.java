package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class T7Tiers {
  public static final Tier ARCANUM_TIER = new SimpleTier(
      BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
      1000,
      7f,
      2.5f,
      22,
      () -> Ingredient.of(T7Items.ARCANUM_INGOT)
  );
}
