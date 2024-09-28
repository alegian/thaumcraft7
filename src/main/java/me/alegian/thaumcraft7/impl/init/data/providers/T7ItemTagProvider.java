package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.T7Tags;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class T7ItemTagProvider extends ItemTagsProvider {

  public T7ItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper pExistingFileHelper) {
    super(pOutput, pLookupProvider, pBlockTags, Thaumcraft.MODID, pExistingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    tag(T7Tags.WAND_CORE).addTag(Tags.Items.RODS_WOODEN);

    tag(T7Tags.WAND_HANDLE).add(
        T7Items.IRON_HANDLE.get(),
        T7Items.GOLD_HANDLE.get(),
        T7Items.ORICHALCUM_HANDLE.get(),
        T7Items.ARCANUM_HANDLE.get()
    );

    tag(T7Tags.TESTA).add(
        T7Items.TERRA_TESTA.get(),
        T7Items.AER_TESTA.get(),
        T7Items.IGNIS_TESTA.get(),
        T7Items.ORDO_TESTA.get(),
        T7Items.AQUA_TESTA.get(),
        T7Items.PERDITIO_TESTA.get()
    );

    tag(Tags.Items.INGOTS).add(
        T7Items.ARCANUM_INGOT.get(),
        T7Items.ORICHALCUM_INGOT.get()
    );

    tag(Tags.Items.NUGGETS).add(
        T7Items.ARCANUM_NUGGET.get(),
        T7Items.ORICHALCUM_NUGGET.get()
    );

    tag(ItemTags.SWORDS).add(T7Items.ARCANUM_SWORD.get(), T7Items.ARCANUM_KATANA.get());
    tag(ItemTags.AXES).add(T7Items.ARCANUM_AXE.get());
    tag(ItemTags.PICKAXES).add(T7Items.ARCANUM_PICKAXE.get());
    tag(ItemTags.SHOVELS).add(T7Items.ARCANUM_SHOVEL.get());
    tag(ItemTags.HOES).add(T7Items.ARCANUM_HOE.get());

    tag(ItemTags.FOOT_ARMOR).add(T7Items.ARCANUM_BOOTS.get());
    tag(ItemTags.LEG_ARMOR).add(T7Items.ARCANUM_LEGGINGS.get());
    tag(ItemTags.CHEST_ARMOR).add(T7Items.ARCANUM_CHESTPLATE.get());
    tag(ItemTags.HEAD_ARMOR).add(T7Items.ARCANUM_HELMET.get());
  }
}
