package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class T7DatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
  public T7DatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, builder, Set.of(Thaumcraft.MODID));
  }

  public static final ResourceKey<ConfiguredFeature<?, ?>> EXAMPLE_CONFIGURED_FEATURE = ResourceKey.create(
      Registries.CONFIGURED_FEATURE,
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "example_configured_feature")
  );
  public static final ResourceKey<PlacedFeature> EXAMPLE_PLACED_FEATURE = ResourceKey.create(
      Registries.PLACED_FEATURE,
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "example_placed_feature")
  );
  public static final ResourceKey<BiomeModifier> EXAMPLE_BIOME_MODIFIER = ResourceKey.create(
      NeoForgeRegistries.Keys.BIOME_MODIFIERS,
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "example_biome_modifier")
  );

  private static final RegistrySetBuilder builder = new RegistrySetBuilder()
      .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
        bootstrap.register(
            EXAMPLE_CONFIGURED_FEATURE,
            new ConfiguredFeature<>(Feature.TREE, TreeFeatures.createStraightBlobTree(Blocks.COBBLESTONE, Blocks.COBBLESTONE, 8, 4, 0, 4).ignoreVines().build())
        );
      })
      .add(Registries.PLACED_FEATURE, bootstrap -> {
        HolderGetter<ConfiguredFeature<?, ?>> otherRegistry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
        bootstrap.register(EXAMPLE_PLACED_FEATURE, new PlacedFeature(
            otherRegistry.getOrThrow(EXAMPLE_CONFIGURED_FEATURE),
            List.of(
                PlacementUtils.countExtra(10, 0.1f, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)
            )
        ));
      })
      .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
        HolderGetter<PlacedFeature> placedFeatureRegistry = bootstrap.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomeRegistry = bootstrap.lookup(Registries.BIOME);
        bootstrap.register(EXAMPLE_BIOME_MODIFIER,
            new BiomeModifiers.AddFeaturesBiomeModifier(
                biomeRegistry.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatureRegistry.getOrThrow(EXAMPLE_PLACED_FEATURE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
            )
        );
      });
}
