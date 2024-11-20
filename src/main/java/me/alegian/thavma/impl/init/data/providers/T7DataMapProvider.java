package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.common.aspect.AspectMap;
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
import java.util.function.Consumer;

public class T7DataMapProvider extends DataMapProvider {
  public T7DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
    super(packOutput, lookupProvider);
  }

  @Override
  protected void gather() {
    var i = builder(T7DataMaps.AspectContent.ITEM);
    var b = builder(T7DataMaps.AspectContent.BLOCK);

    block(b, Tags.Blocks.STONES, am -> am.add(Aspects.TERRA.get(), 5));
    block(b, Blocks.DIRT, am -> am.add(Aspects.TERRA.get(), 5));

    block(b, Blocks.POLISHED_GRANITE, am -> am.add(Aspects.TERRA.get(), 3));
    block(b, Blocks.POLISHED_DIORITE, am -> am.add(Aspects.TERRA.get(), 3));
    block(b, Blocks.POLISHED_ANDESITE, am -> am.add(Aspects.TERRA.get(), 3));
    block(b, Blocks.COARSE_DIRT, am -> am.add(Aspects.TERRA.get(), 3));

    block(b, Blocks.GRASS_BLOCK, am -> am.add(Aspects.TERRA.get(), 5).add(Aspects.HERBA.get(), 2));
    block(b, Blocks.PODZOL, am -> am.add(Aspects.TERRA.get(), 5).add(Aspects.HERBA.get(), 1));
    block(b, Blocks.SHORT_GRASS, am -> am.add(Aspects.HERBA.get(), 5).add(Aspects.AER.get(), 1));
    block(b, Blocks.TALL_GRASS, am -> am.add(Aspects.HERBA.get(), 5).add(Aspects.AER.get(), 1));

    block(b, Tags.Blocks.COBBLESTONES, am -> am.add(Aspects.TERRA.get(), 5).add(Aspects.PERDITIO.get(), 1));
    block(b, Tags.Blocks.SANDS, am -> am.add(Aspects.TERRA.get(), 5).add(Aspects.PERDITIO.get(), 5));

    block(b, BlockTags.TERRACOTTA, am -> am.add(Aspects.AQUA.get(), 15).add(Aspects.TERRA.get(), 15).add(Aspects.IGNIS.get(), 1).add(Aspects.SENSUS.get(), 1));
    block(b, BlockTags.CONCRETE_POWDER, am -> am.add(Aspects.TERRA.get(), 3).add(Aspects.PERDITIO.get(), 2));
    block(b, Tags.Blocks.CONCRETES, am -> am.add(Aspects.TERRA.get(), 3).add(Aspects.PERDITIO.get(), 2).add(Aspects.AQUA.get(), 1).add(Aspects.ORDO.get(), 1));

    block(b, BlockTags.PLANKS, am -> am.add(Aspects.HERBA.get(), 3));
    block(b, BlockTags.WOODEN_STAIRS, am -> am.add(Aspects.HERBA.get(), 3));
    block(b, BlockTags.WOODEN_SLABS, am -> am.add(Aspects.HERBA.get(), 1));
    block(b, BlockTags.LOGS, am -> am.add(Aspects.HERBA.get(), 20));

    block(b, BlockTags.WOOL, am -> am.add(Aspects.BESTIA.get(), 11).add(Aspects.SENSUS.get(), 3).add(Aspects.FABRICO.get(), 3));

    block(b, Tags.Blocks.GLASS_BLOCKS_CHEAP, am -> am.add(Aspects.VITREUS.get(), 5));
    block(b, Tags.Blocks.GLASS_PANES, am -> am.add(Aspects.VITREUS.get(), 1));

    block(b, Blocks.BEDROCK, am -> am.add(Aspects.VACUOS.get(), 25).add(Aspects.PERDITIO.get(), 25).add(Aspects.TERRA.get(), 25).add(Aspects.TENEBRAE.get(), 25));

    item(i, Items.DIAMOND, am -> am.add(Aspects.VITREUS.get(), 15).add(Aspects.DESIDERIUM.get(), 15));
  }

  /**
   * When checking for Aspect contents, the Block aspects are prioritized over the Item aspects.
   * Therefore, to avoid ambiguities, BlockItem aspect registration is forbidden.
   */
  private void item(DataMapProvider.Builder<AspectMap, Item> builder, Item item, Consumer<AspectMap.Builder> builderConsumer) {
    if (item instanceof BlockItem)
      throw new IllegalArgumentException("Cannot register Aspects for BlockItems, you should register for their Blocks instead");

    var aspectBuilder = AspectMap.builder();
    builderConsumer.accept(aspectBuilder);
    builder.add(key(item), aspectBuilder.build(), false);
  }

  private void block(DataMapProvider.Builder<AspectMap, Block> builder, Block block, Consumer<AspectMap.Builder> builderConsumer) {
    var aspectBuilder = AspectMap.builder();
    builderConsumer.accept(aspectBuilder);
    builder.add(key(block), aspectBuilder.build(), false);
  }

  private void block(DataMapProvider.Builder<AspectMap, Block> builder, TagKey<Block> tag, Consumer<AspectMap.Builder> builderConsumer) {
    var aspectBuilder = AspectMap.builder();
    builderConsumer.accept(aspectBuilder);
    builder.add(tag, aspectBuilder.build(), false);
  }

  private ResourceKey<Item> key(Item item) {
    return BuiltInRegistries.ITEM.getResourceKey(item).orElseThrow();
  }

  private ResourceKey<Block> key(Block block) {
    return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
  }
}
