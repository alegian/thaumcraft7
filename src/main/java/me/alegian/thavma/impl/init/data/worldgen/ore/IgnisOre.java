package me.alegian.thavma.impl.init.data.worldgen.ore;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * The raw aspect ores such as this one are not used directly, rather they are
 * assigned chances through a random feature called InfusedStoneOre.
 */
public class IgnisOre {
  public static final String PATH = "ore_ignis";
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE,
      Thavma.rl(IgnisOre.PATH)
  );
  public static final ResourceKey<PlacedFeature> PLACED_FEATURE = ResourceKey.create(
      Registries.PLACED_FEATURE,
      Thavma.rl(IgnisOre.PATH)
  );

  public static Holder<ConfiguredFeature<?, ?>> registerConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    return OreFeatureHelper.registerConfiguredInfusedStone(context, IgnisOre.CONFIGURED_FEATURE, T7Blocks.IGNIS_INFUSED_STONE.get().defaultBlockState(), T7Blocks.IGNIS_INFUSED_DEEPSLATE.get().defaultBlockState());
  }

  public static Holder<PlacedFeature> registerPlaced(BootstrapContext<PlacedFeature> context) {
    return OreFeatureHelper.registerPlacedInfusedStone(context, IgnisOre.CONFIGURED_FEATURE, IgnisOre.PLACED_FEATURE);
  }
}
