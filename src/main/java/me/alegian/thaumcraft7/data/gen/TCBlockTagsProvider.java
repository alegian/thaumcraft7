package me.alegian.thaumcraft7.data.gen;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.tag.CrucibleHeatSourceTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TCBlockTagsProvider extends BlockTagsProvider {
  public TCBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, Thaumcraft.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    // TODO: add nitor
    tag(CrucibleHeatSourceTag.BLOCK).addTag(BlockTags.FIRE).addTag(BlockTags.CAMPFIRES);
  }
}
