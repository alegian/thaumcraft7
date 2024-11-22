package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.loot.WardenLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class T7GlobalLootModifierProvider extends GlobalLootModifierProvider {
  public T7GlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, Thavma.MODID);
  }

  @Override
  protected void start() {
    this.add("warden", new WardenLootModifier(), List.of());
  }
}
