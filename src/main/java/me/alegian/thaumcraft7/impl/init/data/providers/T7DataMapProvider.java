package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.aspect.AspectStack;
import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
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

    block(b, Tags.Blocks.STONES, AspectStack.of(Aspect.TERRA, 5));
    block(b, Blocks.DIRT, AspectStack.of(Aspect.TERRA, 5));

    block(b, Blocks.POLISHED_GRANITE, AspectStack.of(Aspect.TERRA, 3));
    block(b, Blocks.POLISHED_DIORITE, AspectStack.of(Aspect.TERRA, 3));
    block(b, Blocks.POLISHED_ANDESITE, AspectStack.of(Aspect.TERRA, 3));
    block(b, Blocks.COARSE_DIRT, AspectStack.of(Aspect.TERRA, 3));

    block(b, Blocks.GRASS_BLOCK, AspectStack.of(Aspect.TERRA, 5), AspectStack.of(Aspect.HERBA, 2));
    block(b, Blocks.PODZOL, AspectStack.of(Aspect.TERRA, 5), AspectStack.of(Aspect.HERBA, 1));
    block(b, Blocks.SHORT_GRASS, AspectStack.of(Aspect.HERBA, 5), AspectStack.of(Aspect.AER, 1));
    block(b, Blocks.TALL_GRASS, AspectStack.of(Aspect.HERBA, 5), AspectStack.of(Aspect.AER, 1));

    block(b, Tags.Blocks.COBBLESTONES, AspectStack.of(Aspect.TERRA, 5), AspectStack.of(Aspect.PERDITIO, 1));
    block(b, Tags.Blocks.SANDS, AspectStack.of(Aspect.TERRA, 5), AspectStack.of(Aspect.PERDITIO, 5));

    block(b, BlockTags.TERRACOTTA, AspectStack.of(Aspect.AQUA, 15), AspectStack.of(Aspect.TERRA, 15), AspectStack.of(Aspect.IGNIS, 1), AspectStack.of(Aspect.SENSUS, 1));
    block(b, BlockTags.CONCRETE_POWDER, AspectStack.of(Aspect.TERRA, 3), AspectStack.of(Aspect.PERDITIO, 2));
    block(b, Tags.Blocks.CONCRETES, AspectStack.of(Aspect.TERRA, 3), AspectStack.of(Aspect.PERDITIO, 2), AspectStack.of(Aspect.AQUA, 1), AspectStack.of(Aspect.ORDO, 1));

    block(b, BlockTags.PLANKS, AspectStack.of(Aspect.HERBA, 3));
    block(b, BlockTags.WOODEN_STAIRS, AspectStack.of(Aspect.HERBA, 3));
    block(b, BlockTags.WOODEN_SLABS, AspectStack.of(Aspect.HERBA, 1));
    block(b, BlockTags.LOGS, AspectStack.of(Aspect.HERBA, 20));

    block(b, BlockTags.WOOL, AspectStack.of(Aspect.BESTIA, 11), AspectStack.of(Aspect.SENSUS, 3), AspectStack.of(Aspect.FABRICO, 3));

    block(b, Tags.Blocks.GLASS_BLOCKS_CHEAP, AspectStack.of(Aspect.VITREUS, 5));
    block(b, Tags.Blocks.GLASS_PANES, AspectStack.of(Aspect.VITREUS, 1));

    block(b, Blocks.BEDROCK, AspectStack.of(Aspect.VACUOS, 25), AspectStack.of(Aspect.PERDITIO, 25), AspectStack.of(Aspect.TERRA, 25), AspectStack.of(Aspect.TENEBRAE, 25));

    item(i, Items.DIAMOND, AspectStack.of(Aspect.VITREUS, 15), AspectStack.of(Aspect.DESIDERIUM, 15));
  }

  /**
   * When checking for Aspect contents, the Block aspects are prioritized over the Item aspects.
   * Therefore, to avoid ambiguities, BlockItem aspect registration is forbidden.
   */
  private void item(DataMapProvider.Builder<AspectList, Item> builder, Item item, AspectStack... aspects) {
    if (item instanceof BlockItem)
      throw new IllegalArgumentException("Cannot register Aspects for BlockItems, you should register for their Blocks instead");
    builder.add(key(item), AspectList.of(aspects), false);
  }

  private void block(DataMapProvider.Builder<AspectList, Block> builder, Block block, AspectStack... aspects) {
    builder.add(key(block), AspectList.of(aspects), false);
  }

  private void block(DataMapProvider.Builder<AspectList, Block> builder, TagKey<Block> tag, AspectStack... aspects) {
    builder.add(tag, AspectList.of(aspects), false);
  }

  private ResourceKey<Item> key(Item item) {
    return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
  }

  private ResourceKey<Block> key(Block block) {
    return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
  }
}
