package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.common.block.InfusedStoneBlock;
import me.alegian.thavma.impl.common.item.TestaItem;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
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
  protected void generate() {
    this.dropSelf(T7Blocks.GREATWOOD_LOG.get());
    this.dropSelf(T7Blocks.GREATWOOD_PLANKS.get());
    this.add(T7Blocks.GREATWOOD_LEAVES.get(), l -> this.createLeavesDrops(l, T7Blocks.GREATWOOD_SAPLING.get(), BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES));
    this.dropSelf(T7Blocks.GREATWOOD_SAPLING.get());

    this.dropSelf(T7Blocks.SILVERWOOD_LOG.get());
    this.dropSelf(T7Blocks.SILVERWOOD_PLANKS.get());
    this.add(T7Blocks.SILVERWOOD_LEAVES.get(), l -> this.createLeavesDrops(l, T7Blocks.SILVERWOOD_SAPLING.get(), BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES));
    this.dropSelf(T7Blocks.SILVERWOOD_SAPLING.get());

    this.dropSelf(T7Blocks.CRUCIBLE.get());
    this.dropSelf(T7Blocks.AURA_NODE.get()); // TODO: replace
    this.dropSelf(T7Blocks.ARCANE_WORKBENCH.get());
    this.dropSelf(T7Blocks.MATRIX.get());
    this.dropSelf(T7Blocks.PILLAR.get());
    this.dropSelf(T7Blocks.PEDESTAL.get());
    this.dropSelf(T7Blocks.RESEARCH_TABLE.get());
    this.dropSelf(T7Blocks.ELEMENTAL_STONE.get());

    this.dropSelf(T7Blocks.ARCANUM_BLOCK.get());
    this.dropSelf(T7Blocks.ORICHALCUM_BLOCK.get());

    this.dropSelf(T7Blocks.ESSENTIA_CONTAINER.get());

    this.infusedStone(T7Blocks.IGNIS_INFUSED_STONE, T7Items.IGNIS_TESTA);
    this.infusedStone(T7Blocks.TERRA_INFUSED_STONE, T7Items.TERRA_TESTA);
    this.infusedStone(T7Blocks.AER_INFUSED_STONE, T7Items.AER_TESTA);
    this.infusedStone(T7Blocks.AQUA_INFUSED_STONE, T7Items.AQUA_TESTA);
    this.infusedStone(T7Blocks.ORDO_INFUSED_STONE, T7Items.ORDO_TESTA);
    this.infusedStone(T7Blocks.PERDITIO_INFUSED_STONE, T7Items.PERDITIO_TESTA);
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return T7Blocks.REGISTRAR.getEntries()
        .stream()
        .map(e -> (Block) e.value())
        .toList();
  }

  private void infusedStone(DeferredBlock<InfusedStoneBlock> block, DeferredItem<TestaItem> item) {
    this.add(block.get(), b -> this.createOreDrop(b, item.get()));
  }
}
