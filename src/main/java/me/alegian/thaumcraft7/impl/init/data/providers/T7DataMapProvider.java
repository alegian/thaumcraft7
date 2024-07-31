package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class T7DataMapProvider extends DataMapProvider {
  public T7DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void gather() {
    builder(T7DataMaps.ASPECT_CONTENT)
        .add(vanilla(Items.STONE), AspectList.of(Aspect.TERRA, 2), false)
        .add(vanilla(Items.STICK), AspectList.of(Aspect.HERBA, 1), false)
    ;
  }

  private ResourceKey<Item> vanilla(Item item) {
    return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
  }
}
