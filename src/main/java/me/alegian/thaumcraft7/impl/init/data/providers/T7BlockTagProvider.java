package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.T7Tags;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class T7BlockTagProvider extends BlockTagsProvider {
  public T7BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, Thaumcraft.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    // TODO: add nitor
    tag(T7Tags.CrucibleHeatSourceTag.BLOCK).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
    tag(Tags.Blocks.ORES).add(
        T7Blocks.IGNIS_INFUSED_STONE.get(),
        T7Blocks.TERRA_INFUSED_STONE.get(),
        T7Blocks.AER_INFUSED_STONE.get(),
        T7Blocks.AQUA_INFUSED_STONE.get(),
        T7Blocks.ORDO_INFUSED_STONE.get(),
        T7Blocks.PERDITIO_INFUSED_STONE.get()
    );
    tag(Tags.Blocks.ORE_RATES_SINGULAR).add(
        T7Blocks.IGNIS_INFUSED_STONE.get(),
        T7Blocks.TERRA_INFUSED_STONE.get(),
        T7Blocks.AER_INFUSED_STONE.get(),
        T7Blocks.AQUA_INFUSED_STONE.get(),
        T7Blocks.ORDO_INFUSED_STONE.get(),
        T7Blocks.PERDITIO_INFUSED_STONE.get()
    );
    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
        T7Blocks.IGNIS_INFUSED_STONE.get(),
        T7Blocks.TERRA_INFUSED_STONE.get(),
        T7Blocks.AER_INFUSED_STONE.get(),
        T7Blocks.AQUA_INFUSED_STONE.get(),
        T7Blocks.ORDO_INFUSED_STONE.get(),
        T7Blocks.PERDITIO_INFUSED_STONE.get(),
        T7Blocks.CRUCIBLE.get()
    );
    tag(BlockTags.NEEDS_STONE_TOOL).add(
        T7Blocks.IGNIS_INFUSED_STONE.get(),
        T7Blocks.TERRA_INFUSED_STONE.get(),
        T7Blocks.AER_INFUSED_STONE.get(),
        T7Blocks.AQUA_INFUSED_STONE.get(),
        T7Blocks.ORDO_INFUSED_STONE.get(),
        T7Blocks.PERDITIO_INFUSED_STONE.get()
    );
    tag(BlockTags.MINEABLE_WITH_AXE).add(
        T7Blocks.GREATWOOD_LOG.get(),
        T7Blocks.GREATWOOD_PLANKS.get()
    );

    tag(BlockTags.LEAVES).add(T7Blocks.GREATWOOD_LEAVES.get());
    tag(BlockTags.LOGS_THAT_BURN).add(T7Blocks.GREATWOOD_LOG.get());
    tag(BlockTags.SAPLINGS).add(T7Blocks.GREATWOOD_SAPLING.get());
    tag(BlockTags.PLANKS).add(T7Blocks.GREATWOOD_PLANKS.get());
  }
}
