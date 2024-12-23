package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.item.TestaItem;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class T7ItemModelProvider extends ItemModelProvider {
  public T7ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, Thavma.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    this.basicItem(T7Items.INSTANCE.getIRON_HANDLE().get());
    this.basicItem(T7Items.INSTANCE.getGOLD_HANDLE().get());
    this.basicItem(T7Items.INSTANCE.getORICHALCUM_HANDLE().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_HANDLE().get());

    this.basicItem(T7Items.INSTANCE.getEYE_OF_WARDEN().get());

    this.basicItem(T7Items.INSTANCE.getGREATWOOD_CORE().get());
    this.basicItem(T7Items.INSTANCE.getSILVERWOOD_CORE().get());

    this.basicItem(T7Items.INSTANCE.getRUNE().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_INGOT().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_NUGGET().get());
    this.basicItem(T7Items.INSTANCE.getORICHALCUM_INGOT().get());
    this.basicItem(T7Items.INSTANCE.getORICHALCUM_NUGGET().get());
    this.basicItem(T7Items.INSTANCE.getRESEARCH_SCROLL().get());
    this.basicItem(T7Items.INSTANCE.getCOMPLETED_RESEARCH().get());

    this.basicItem(T7Items.INSTANCE.getGOGGLES().get());
    this.basicItem(T7Items.INSTANCE.getGOGGLES_ACCESSORY().get());
    this.basicItem(T7Items.INSTANCE.getDAWN_CHARM().get());
    this.basicItem(T7Items.INSTANCE.getRESEARCHER_CHESTPLATE().get());
    this.basicItem(T7Items.INSTANCE.getRESEARCHER_LEGGINGS().get());
    this.basicItem(T7Items.INSTANCE.getRESEARCHER_BOOTS().get());

    this.basicItem(T7Items.INSTANCE.getARCANUM_HELMET().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_CHESTPLATE().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_LEGGINGS().get());
    this.basicItem(T7Items.INSTANCE.getARCANUM_BOOTS().get());

    this.basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_HELMET().get());
    this.basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_CHESTPLATE().get());
    this.basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_LEGGINGS().get());
    this.basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_BOOTS().get());

    this.handheldItem(T7Items.INSTANCE.getARCANUM_SWORD());
    this.handheldItem(T7Items.INSTANCE.getARCANUM_AXE());
    this.handheldItem(T7Items.INSTANCE.getARCANUM_PICKAXE());
    this.handheldItem(T7Items.INSTANCE.getARCANUM_HAMMER());
    this.handheldItem(T7Items.INSTANCE.getARCANUM_SHOVEL());
    this.handheldItem(T7Items.INSTANCE.getARCANUM_HOE());

    for (var testa : T7Items.INSTANCE.getTESTAS().values())
      this.testaItem(testa);

    for (var wand : T7Items.INSTANCE.getWANDS().values())
      this.withExistingParent(wand.getName(), Thavma.INSTANCE.rl("wand"));
  }

  public void testaItem(DeferredItem<TestaItem> deferredItem) {
    this.withVanillaParent(deferredItem.getId().getPath(), "testa", "generated");
  }

  public void handheldItem(DeferredItem<? extends Item> deferredItem) {
    this.withVanillaParent(deferredItem, "handheld");
  }

  public void withVanillaParent(DeferredItem<? extends Item> deferredItem, String parent) {
    var path = deferredItem.getId().getPath();
    this.withVanillaParent(path, parent);
  }

  public void withVanillaParent(String itemPath, String parent) {
    this.withVanillaParent(itemPath, itemPath, parent);
  }

  public void withVanillaParent(String itemPath, String texturePath, String parent) {
    this.withExistingParent(itemPath, parent)
        .texture("layer0", Thavma.INSTANCE.rl(texturePath).withPrefix("item/"));
  }
}
