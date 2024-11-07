package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.aspect.AspectStack;
import me.alegian.thavma.impl.init.registries.T7DataMaps;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class T7DataMapProvider extends DataMapProvider {
  public T7DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void gather() {
    var i = builder(T7DataMaps.AspectContent.ITEM);
    var b = builder(T7DataMaps.AspectContent.BLOCK);

    block(b, Tags.Blocks.STONES, AspectStack.of(Aspects.TERRA.get(), 5));
    block(b, Blocks.DIRT, AspectStack.of(Aspects.TERRA.get(), 5));

    block(b, Blocks.POLISHED_GRANITE, AspectStack.of(Aspects.TERRA.get(), 3));
    block(b, Blocks.POLISHED_DIORITE, AspectStack.of(Aspects.TERRA.get(), 3));
    block(b, Blocks.POLISHED_ANDESITE, AspectStack.of(Aspects.TERRA.get(), 3));
    block(b, Blocks.COARSE_DIRT, AspectStack.of(Aspects.TERRA.get(), 3));

    block(b, Blocks.GRASS_BLOCK, AspectStack.of(Aspects.TERRA.get(), 5), AspectStack.of(Aspects.HERBA.get(), 2));
    block(b, Blocks.PODZOL, AspectStack.of(Aspects.TERRA.get(), 5), AspectStack.of(Aspects.HERBA.get(), 1));
    block(b, Blocks.SHORT_GRASS, AspectStack.of(Aspects.HERBA.get(), 5), AspectStack.of(Aspects.AER.get(), 1));
    block(b, Blocks.TALL_GRASS, AspectStack.of(Aspects.HERBA.get(), 5), AspectStack.of(Aspects.AER.get(), 1));

    block(b, Tags.Blocks.COBBLESTONES, AspectStack.of(Aspects.TERRA.get(), 5), AspectStack.of(Aspects.PERDITIO.get(), 1));
    block(b, Tags.Blocks.SANDS, AspectStack.of(Aspects.TERRA.get(), 5), AspectStack.of(Aspects.PERDITIO.get(), 5));

    block(b, BlockTags.TERRACOTTA, AspectStack.of(Aspects.AQUA.get(), 15), AspectStack.of(Aspects.TERRA.get(), 15), AspectStack.of(Aspects.IGNIS.get(), 1), AspectStack.of(Aspects.SENSUS.get(), 1));
    block(b, BlockTags.CONCRETE_POWDER, AspectStack.of(Aspects.TERRA.get(), 3), AspectStack.of(Aspects.PERDITIO.get(), 2));
    block(b, Tags.Blocks.CONCRETES, AspectStack.of(Aspects.TERRA.get(), 3), AspectStack.of(Aspects.PERDITIO.get(), 2), AspectStack.of(Aspects.AQUA.get(), 1), AspectStack.of(Aspects.ORDO.get(), 1));

    block(b, BlockTags.PLANKS, AspectStack.of(Aspects.HERBA.get(), 3));
    block(b, BlockTags.WOODEN_STAIRS, AspectStack.of(Aspects.HERBA.get(), 3));
    block(b, BlockTags.WOODEN_SLABS, AspectStack.of(Aspects.HERBA.get(), 1));
    block(b, BlockTags.LOGS, AspectStack.of(Aspects.HERBA.get(), 20));

    block(b, BlockTags.WOOL, AspectStack.of(Aspects.BESTIA.get(), 11), AspectStack.of(Aspects.SENSUS.get(), 3), AspectStack.of(Aspects.FABRICO.get(), 3));

    block(b, Tags.Blocks.GLASS_BLOCKS_CHEAP, AspectStack.of(Aspects.VITREUS.get(), 5));
    block(b, Tags.Blocks.GLASS_PANES, AspectStack.of(Aspects.VITREUS.get(), 1));

    block(b, Blocks.BEDROCK, AspectStack.of(Aspects.VACUOS.get(), 25), AspectStack.of(Aspects.PERDITIO.get(), 25), AspectStack.of(Aspects.TERRA.get(), 25), AspectStack.of(Aspects.TENEBRAE.get(), 25));

    item(i, Items.DIAMOND, AspectStack.of(Aspects.VITREUS.get(), 15), AspectStack.of(Aspects.DESIDERIUM.get(), 15));
  }

  /**
   * When checking for Aspect contents, the Block aspects are prioritized over the Item aspects.
   * Therefore, to avoid ambiguities, BlockItem aspect registration is forbidden.
   */
  private void item(DataMapProvider.Builder<AspectMap, Item> builder, Item item, AspectStack... aspects) {
    if (item instanceof BlockItem)
      throw new IllegalArgumentException("Cannot register Aspects for BlockItems, you should register for their Blocks instead");
    builder.add(key(item), AspectMap.of(aspects), false);
  }

  private void block(DataMapProvider.Builder<AspectMap, Block> builder, Block block, AspectStack... aspects) {
    builder.add(key(block), AspectMap.of(aspects), false);
  }

  private void block(DataMapProvider.Builder<AspectMap, Block> builder, TagKey<Block> tag, AspectStack... aspects) {
    builder.add(tag, AspectMap.of(aspects), false);
  }

  private ResourceKey<Item> key(Item item) {
    return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
  }

  private ResourceKey<Block> key(Block block) {
    return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
  }
}
