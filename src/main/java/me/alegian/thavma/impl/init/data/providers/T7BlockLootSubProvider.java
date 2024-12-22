package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.common.block.InfusedBlock;
import me.alegian.thavma.impl.common.item.TestaItem;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
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
    this.dropSelf(T7Blocks.INSTANCE.getGREATWOOD_LOG().get());
    this.dropSelf(T7Blocks.INSTANCE.getGREATWOOD_PLANKS().get());
    this.add(T7Blocks.INSTANCE.getGREATWOOD_LEAVES().get(), l -> this.createLeavesDrops(l, T7Blocks.INSTANCE.getGREATWOOD_SAPLING().get(), BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES));
    this.dropSelf(T7Blocks.INSTANCE.getGREATWOOD_SAPLING().get());

    this.dropSelf(T7Blocks.INSTANCE.getSILVERWOOD_LOG().get());
    this.dropSelf(T7Blocks.INSTANCE.getSILVERWOOD_PLANKS().get());
    this.add(T7Blocks.INSTANCE.getSILVERWOOD_LEAVES().get(), l -> this.createLeavesDrops(l, T7Blocks.INSTANCE.getSILVERWOOD_SAPLING().get(), BlockLootSubProvider.NORMAL_LEAVES_SAPLING_CHANCES));
    this.dropSelf(T7Blocks.INSTANCE.getSILVERWOOD_SAPLING().get());

    this.dropSelf(T7Blocks.INSTANCE.getCRUCIBLE().get());
    this.dropSelf(T7Blocks.INSTANCE.getAURA_NODE().get()); // TODO: replace
    this.dropSelf(T7Blocks.INSTANCE.getARCANE_WORKBENCH().get());
    this.dropSelf(T7Blocks.INSTANCE.getMATRIX().get());
    this.dropSelf(T7Blocks.INSTANCE.getPILLAR().get());
    this.dropSelf(T7Blocks.INSTANCE.getPEDESTAL().get());
    this.dropSelf(T7Blocks.INSTANCE.getRESEARCH_TABLE().get());
    this.dropSelf(T7Blocks.INSTANCE.getELEMENTAL_STONE().get());

    this.dropSelf(T7Blocks.INSTANCE.getARCANUM_BLOCK().get());
    this.dropSelf(T7Blocks.INSTANCE.getORICHALCUM_BLOCK().get());

    this.dropSelf(T7Blocks.INSTANCE.getESSENTIA_CONTAINER().get());

    for(var aspect : Aspects.INSTANCE.getPRIMAL_ASPECTS()){
      this.infusedBlock(T7Blocks.INSTANCE.getINFUSED_STONES().get(aspect), T7Items.INSTANCE.getTESTAS().get(aspect));
      this.infusedBlock(T7Blocks.INSTANCE.getINFUSED_DEEPSLATES().get(aspect), T7Items.INSTANCE.getTESTAS().get(aspect));
    }
  }

  @Override
  protected @NotNull Iterable<Block> getKnownBlocks() {
    return T7Blocks.INSTANCE.getREGISTRAR().getEntries()
        .stream()
        .map(e -> (Block) e.value())
        .toList();
  }

  private void infusedBlock(DeferredBlock<InfusedBlock> block, DeferredItem<TestaItem> item) {
    this.add(block.get(), b -> this.createOreDrop(b, item.get()));
  }
}
