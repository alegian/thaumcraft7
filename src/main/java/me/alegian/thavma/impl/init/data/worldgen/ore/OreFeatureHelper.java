package me.alegian.thavma.impl.init.data.worldgen.ore;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * A helper class for registering the 6 different aspects of InfusedStoneOre.
 */
public class OreFeatureHelper {
  public static Holder<ConfiguredFeature<?, ?>> registerConfiguredInfusedStone(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, BlockState stoneOre, BlockState deepSlateOre) {
    var stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    var deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    List<OreConfiguration.TargetBlockState> list = List.of(
        OreConfiguration.target(stoneReplaceables, stoneOre),
        OreConfiguration.target(deepslateReplaceables, deepSlateOre)
    );

    return context.register(
        key,
        new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(list, 7))
    );
  }

  public static Holder<PlacedFeature> registerPlacedInfusedStone(BootstrapContext<PlacedFeature> context, ResourceKey<ConfiguredFeature<?, ?>> configuredKey, ResourceKey<PlacedFeature> placedKey) {
    HolderGetter<ConfiguredFeature<?, ?>> otherRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

    return context.register(placedKey, new PlacedFeature(
        otherRegistry.getOrThrow(configuredKey),
        List.of()
    ));
  }
}
