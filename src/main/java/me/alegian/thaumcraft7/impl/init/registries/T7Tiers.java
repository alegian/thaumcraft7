package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class T7Tiers {
  public static final Tier
      ARCANUM_TIER = new SimpleTier(
      // The tag that determines what blocks this tool cannot break. See below for more information.
      MyBlockTags.INCORRECT_FOR_COPPER_TOOL,
      1000,
      7f,
      2.5f,
      22,
      () -> Ingredient.of(T7Items.ARCANUM_INGOT)
  );
}
