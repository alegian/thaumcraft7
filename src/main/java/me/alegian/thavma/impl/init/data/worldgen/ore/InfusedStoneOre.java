package me.alegian.thavma.impl.init.data.worldgen.ore;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

/**
 * Handles generation of all 6 different Infused Stone Ores. Uses a
 * random feature, and checks all placement criteria here (the individual
 * ores do not have placement filters)
 */
public class InfusedStoneOre {
  public static final String PATH = "ore_infused_stone";
  public static final ResourceKey<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE,
      Thavma.Companion.rl(InfusedStoneOre.PATH)
  );
  public static final ResourceKey<PlacedFeature> PLACED_FEATURE = ResourceKey.create(
      Registries.PLACED_FEATURE,
      Thavma.Companion.rl(InfusedStoneOre.PATH)
  );
  public static final ResourceKey<BiomeModifier> BIOME_MODIFIER = ResourceKey.create(
      NeoForgeRegistries.Keys.BIOME_MODIFIERS,
      Thavma.Companion.rl(InfusedStoneOre.PATH)
  );

  public static void registerConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    HolderGetter<PlacedFeature> placedRegistry = context.lookup(Registries.PLACED_FEATURE);
    var ignisOre = placedRegistry.getOrThrow(IgnisOre.PLACED_FEATURE);
    var terraOre = placedRegistry.getOrThrow(TerraOre.PLACED_FEATURE);
    var aerOre = placedRegistry.getOrThrow(AerOre.PLACED_FEATURE);
    var aquaOre = placedRegistry.getOrThrow(AquaOre.PLACED_FEATURE);
    var ordoOre = placedRegistry.getOrThrow(OrdoOre.PLACED_FEATURE);
    var perditioOre = placedRegistry.getOrThrow(PerditioOre.PLACED_FEATURE);

    // 6 ores, equally likely
    context.register(
        InfusedStoneOre.CONFIGURED_FEATURE,
        new ConfiguredFeature<>(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration((HolderSet.direct(terraOre, ignisOre, aerOre, aquaOre, ordoOre, perditioOre)))
        )
    );
  }

  public static void registerPlaced(BootstrapContext<PlacedFeature> context) {
    HolderGetter<ConfiguredFeature<?, ?>> otherRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

    context.register(InfusedStoneOre.PLACED_FEATURE, new PlacedFeature(
        otherRegistry.getOrThrow(InfusedStoneOre.CONFIGURED_FEATURE),
        List.of(
            CountPlacement.of(30),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
            BiomeFilter.biome()
        )
    ));
  }

  public static void registerBiomeModifier(BootstrapContext<BiomeModifier> context) {
    HolderGetter<PlacedFeature> placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE);
    HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);

    context.register(InfusedStoneOre.BIOME_MODIFIER,
        new BiomeModifiers.AddFeaturesBiomeModifier(
            biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD),
            HolderSet.direct(placedFeatureRegistry.getOrThrow(InfusedStoneOre.PLACED_FEATURE)),
            GenerationStep.Decoration.UNDERGROUND_ORES
        )
    );
  }
}
