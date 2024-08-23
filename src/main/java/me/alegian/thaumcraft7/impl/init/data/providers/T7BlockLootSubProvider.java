package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.common.block.InfusedStoneBlock;
import me.alegian.thaumcraft7.impl.common.item.ShardItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class T7BlockLootSubProvider extends BlockLootSubProvider {
  public T7BlockLootSubProvider(HolderLookup.Provider lookupProvider) {
    super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return T7Blocks.REGISTRAR.getEntries()
        .stream()
        .map(e -> (Block) e.value())
        .toList();
  }

  @Override
  protected void generate() {
    dropSelf(T7Blocks.GREATWOOD_LOG.get());
    dropSelf(T7Blocks.GREATWOOD_PLANKS.get());
    dropSelf(T7Blocks.CRUCIBLE.get());
    dropSelf(T7Blocks.GREATWOOD_SAPLING.get());
    dropSelf(T7Blocks.AURA_NODE.get()); // TODO: replace
    add(T7Blocks.GREATWOOD_LEAVES.get(), l -> createLeavesDrops(l, T7Blocks.GREATWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    infusedStone(T7Blocks.IGNIS_INFUSED_STONE, T7Items.IGNIS_SHARD);
    infusedStone(T7Blocks.TERRA_INFUSED_STONE, T7Items.TERRA_SHARD);
    infusedStone(T7Blocks.AER_INFUSED_STONE, T7Items.AER_SHARD);
    infusedStone(T7Blocks.AQUA_INFUSED_STONE, T7Items.AQUA_SHARD);
    infusedStone(T7Blocks.ORDO_INFUSED_STONE, T7Items.ORDO_SHARD);
    infusedStone(T7Blocks.PERDITIO_INFUSED_STONE, T7Items.PERDITIO_SHARD);
  }

  private void infusedStone(DeferredBlock<InfusedStoneBlock> block, DeferredItem<ShardItem> item) {
    add(block.get(), b -> createOreDrop(b, item.get()));
  }
}