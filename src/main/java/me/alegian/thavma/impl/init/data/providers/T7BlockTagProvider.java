package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.InfusedBlock;
import me.alegian.thavma.impl.init.registries.T7Tags;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class T7BlockTagProvider extends BlockTagsProvider {
  public T7BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, Thavma.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    var boundInfusedBlocksArray = T7Blocks.INFUSED_BLOCKS.stream().map(DeferredHolder::get).toArray(InfusedBlock[]::new);
    // TODO: add nitor
    this.tag(T7Tags.CrucibleHeatSourceTag.BLOCK).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
    this.tag(Tags.Blocks.ORES).add(boundInfusedBlocksArray);
    this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(boundInfusedBlocksArray);
    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(boundInfusedBlocksArray);
    this.tag(BlockTags.NEEDS_STONE_TOOL).add(boundInfusedBlocksArray);
    this.tag(BlockTags.NEEDS_IRON_TOOL).add(
        T7Blocks.ARCANUM_BLOCK.get(),
        T7Blocks.ORICHALCUM_BLOCK.get()
    );
    this.tag(BlockTags.MINEABLE_WITH_AXE).add(
        T7Blocks.GREATWOOD_LOG.get(),
        T7Blocks.GREATWOOD_PLANKS.get()
    );

    this.tag(BlockTags.LEAVES).add(T7Blocks.GREATWOOD_LEAVES.get(), T7Blocks.SILVERWOOD_LEAVES.get());
    this.tag(BlockTags.LOGS_THAT_BURN).add(T7Blocks.GREATWOOD_LOG.get(), T7Blocks.SILVERWOOD_LOG.get());
    this.tag(BlockTags.SAPLINGS).add(T7Blocks.GREATWOOD_SAPLING.get(), T7Blocks.SILVERWOOD_SAPLING.get());
    this.tag(BlockTags.PLANKS).add(T7Blocks.GREATWOOD_PLANKS.get(), T7Blocks.SILVERWOOD_PLANKS.get());

    this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
        T7Blocks.ARCANUM_BLOCK.get(),
        T7Blocks.ORICHALCUM_BLOCK.get()
    );

    this.tag(BlockTags.BEACON_BASE_BLOCKS).add(
        T7Blocks.ARCANUM_BLOCK.get(),
        T7Blocks.ORICHALCUM_BLOCK.get()
    );
  }
}
