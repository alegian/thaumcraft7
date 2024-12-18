package me.alegian.thavma.impl.init.data.providers;

import io.wispforest.accessories.api.data.AccessoriesTags;
import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.init.registries.T7Tags;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class T7ItemTagProvider extends ItemTagsProvider {

  public T7ItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper pExistingFileHelper) {
    super(pOutput, pLookupProvider, pBlockTags, Thavma.MODID, pExistingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider pProvider) {
    this.tag(T7Tags.WAND_HANDLE).add(
        T7Items.IRON_HANDLE.get(),
        T7Items.GOLD_HANDLE.get(),
        T7Items.ORICHALCUM_HANDLE.get(),
        T7Items.ARCANUM_HANDLE.get()
    );

    this.tag(T7Tags.WAND_CORE)
        .addTag(Tags.Items.RODS_WOODEN)
        .add(
            T7Items.GREATWOOD_CORE.get(),
            T7Items.SILVERWOOD_CORE.get()
        );

    this.tag(T7Tags.TESTA).add(
        T7Items.TERRA_TESTA.get(),
        T7Items.AER_TESTA.get(),
        T7Items.IGNIS_TESTA.get(),
        T7Items.ORDO_TESTA.get(),
        T7Items.AQUA_TESTA.get(),
        T7Items.PERDITIO_TESTA.get()
    );

    this.tag(Tags.Items.INGOTS).add(
        T7Items.ARCANUM_INGOT.get(),
        T7Items.ORICHALCUM_INGOT.get()
    );

    this.tag(Tags.Items.NUGGETS).add(
        T7Items.ARCANUM_NUGGET.get(),
        T7Items.ORICHALCUM_NUGGET.get()
    );

    this.tag(ItemTags.SWORDS).add(
        T7Items.ARCANUM_SWORD.get(),
        T7Items.ARCANUM_KATANA.get(),
        T7Items.ZEPHYR.get()
    );
    this.tag(ItemTags.AXES).add(T7Items.ARCANUM_AXE.get());
    this.tag(ItemTags.PICKAXES).add(T7Items.ARCANUM_PICKAXE.get());
    this.tag(ItemTags.MINING_ENCHANTABLE).add(T7Items.ARCANUM_HAMMER.get());
    this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(T7Items.ARCANUM_HAMMER.get());
    this.tag(Tags.Items.MINING_TOOL_TOOLS).add(T7Items.ARCANUM_HAMMER.get());
    this.tag(ItemTags.SHOVELS).add(T7Items.ARCANUM_SHOVEL.get());
    this.tag(ItemTags.HOES).add(T7Items.ARCANUM_HOE.get());

    this.tag(ItemTags.FOOT_ARMOR).add(
        T7Items.ARCANUM_BOOTS.get(),
        T7Items.CUSTOS_ARCANUM_BOOTS.get(),
        T7Items.RESEARCHER_BOOTS.get()
    );
    this.tag(ItemTags.LEG_ARMOR).add(
        T7Items.ARCANUM_LEGGINGS.get(),
        T7Items.CUSTOS_ARCANUM_LEGGINGS.get(),
        T7Items.RESEARCHER_LEGGINGS.get()
    );
    this.tag(ItemTags.CHEST_ARMOR).add(
        T7Items.ARCANUM_CHESTPLATE.get(),
        T7Items.CUSTOS_ARCANUM_CHESTPLATE.get(),
        T7Items.RESEARCHER_CHESTPLATE.get()
    );
    this.tag(ItemTags.HEAD_ARMOR).add(
        T7Items.GOGGLES.get(),
        T7Items.ARCANUM_HELMET.get(),
        T7Items.CUSTOS_ARCANUM_HELMET.get()
    );

    this.tag(T7Tags.CATALYST).add(Items.DRAGON_EGG);

    this.tag(AccessoriesTags.FACE_TAG).add(T7Items.GOGGLES_ACCESSORY.get());
    this.tag(AccessoriesTags.CHARM_TAG).add(T7Items.DAWN_CHARM.get());
  }
}
