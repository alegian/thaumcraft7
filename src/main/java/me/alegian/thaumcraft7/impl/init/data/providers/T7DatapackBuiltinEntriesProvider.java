package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.data.worldgen.tree.GreatwoodTree;
import me.alegian.thaumcraft7.impl.init.data.worldgen.tree.SilverwoodTree;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class T7DatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
  public T7DatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, builder, Set.of(Thaumcraft.MODID));
  }

  private static final RegistrySetBuilder builder = new RegistrySetBuilder()
      .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
        GreatwoodTree.registerConfigured(bootstrap);
        SilverwoodTree.registerConfigured(bootstrap);
      })
      .add(Registries.PLACED_FEATURE, bootstrap -> {
        GreatwoodTree.registerPlaced(bootstrap);
        SilverwoodTree.registerPlaced(bootstrap);
      })
      .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
        GreatwoodTree.registerBiomeModifier(bootstrap);
        //SilverwoodTree.registerBiomeModifier(bootstrap);
      });
}
