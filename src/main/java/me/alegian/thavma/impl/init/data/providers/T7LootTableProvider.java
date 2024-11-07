package me.alegian.thavma.impl.init.data.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class T7LootTableProvider extends LootTableProvider {
  public T7LootTableProvider(PackOutput pOutput, List<SubProviderEntry> pSubProviders, CompletableFuture<HolderLookup.Provider> pRegistries) {
    super(pOutput, Set.of(), pSubProviders, pRegistries);
  }
}
