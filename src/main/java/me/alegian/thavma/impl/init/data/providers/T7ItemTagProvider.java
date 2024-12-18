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
    this.tag(T7Tags.INSTANCE.getWAND_HANDLE()).add(
        T7Items.INSTANCE.getIRON_HANDLE().get(),
        T7Items.INSTANCE.getGOLD_HANDLE().get(),
        T7Items.INSTANCE.getORICHALCUM_HANDLE().get(),
        T7Items.INSTANCE.getARCANUM_HANDLE().get()
    );

    this.tag(T7Tags.INSTANCE.getWAND_CORE())
        .addTag(Tags.Items.RODS_WOODEN)
        .add(
            T7Items.INSTANCE.getGREATWOOD_CORE().get(),
            T7Items.INSTANCE.getSILVERWOOD_CORE().get()
        );

    this.tag(T7Tags.INSTANCE.getTESTA()).add(
        T7Items.INSTANCE.getTERRA_TESTA().get(),
        T7Items.INSTANCE.getAER_TESTA().get(),
        T7Items.INSTANCE.getIGNIS_TESTA().get(),
        T7Items.INSTANCE.getORDO_TESTA().get(),
        T7Items.INSTANCE.getAQUA_TESTA().get(),
        T7Items.INSTANCE.getPERDITIO_TESTA().get()
    );

    this.tag(Tags.Items.INGOTS).add(
        T7Items.INSTANCE.getARCANUM_INGOT().get(),
        T7Items.INSTANCE.getORICHALCUM_INGOT().get()
    );

    this.tag(Tags.Items.NUGGETS).add(
        T7Items.INSTANCE.getARCANUM_NUGGET().get(),
        T7Items.INSTANCE.getORICHALCUM_NUGGET().get()
    );

    this.tag(ItemTags.SWORDS).add(
        T7Items.INSTANCE.getARCANUM_SWORD().get(),
        T7Items.INSTANCE.getARCANUM_KATANA().get(),
        T7Items.INSTANCE.getZEPHYR().get()
    );
    this.tag(ItemTags.AXES).add(T7Items.INSTANCE.getARCANUM_AXE().get());
    this.tag(ItemTags.PICKAXES).add(T7Items.INSTANCE.getARCANUM_PICKAXE().get());
    this.tag(ItemTags.MINING_ENCHANTABLE).add(T7Items.INSTANCE.getARCANUM_HAMMER().get());
    this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(T7Items.INSTANCE.getARCANUM_HAMMER().get());
    this.tag(Tags.Items.MINING_TOOL_TOOLS).add(T7Items.INSTANCE.getARCANUM_HAMMER().get());
    this.tag(ItemTags.SHOVELS).add(T7Items.INSTANCE.getARCANUM_SHOVEL().get());
    this.tag(ItemTags.HOES).add(T7Items.INSTANCE.getARCANUM_HOE().get());

    this.tag(ItemTags.FOOT_ARMOR).add(
        T7Items.INSTANCE.getARCANUM_BOOTS().get(),
        T7Items.INSTANCE.getCUSTOS_ARCANUM_BOOTS().get(),
        T7Items.INSTANCE.getRESEARCHER_BOOTS().get()
    );
    this.tag(ItemTags.LEG_ARMOR).add(
        T7Items.INSTANCE.getARCANUM_LEGGINGS().get(),
        T7Items.INSTANCE.getCUSTOS_ARCANUM_LEGGINGS().get(),
        T7Items.INSTANCE.getRESEARCHER_LEGGINGS().get()
    );
    this.tag(ItemTags.CHEST_ARMOR).add(
        T7Items.INSTANCE.getARCANUM_CHESTPLATE().get(),
        T7Items.INSTANCE.getCUSTOS_ARCANUM_CHESTPLATE().get(),
        T7Items.INSTANCE.getRESEARCHER_CHESTPLATE().get()
    );
    this.tag(ItemTags.HEAD_ARMOR).add(
        T7Items.INSTANCE.getGOGGLES().get(),
        T7Items.INSTANCE.getARCANUM_HELMET().get(),
        T7Items.INSTANCE.getCUSTOS_ARCANUM_HELMET().get()
    );

    this.tag(T7Tags.INSTANCE.getCATALYST()).add(Items.DRAGON_EGG);

    this.tag(AccessoriesTags.FACE_TAG).add(T7Items.INSTANCE.getGOGGLES_ACCESSORY().get());
    this.tag(AccessoriesTags.CHARM_TAG).add(T7Items.INSTANCE.getDAWN_CHARM().get());
  }
}
