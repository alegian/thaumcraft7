package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
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
    basicItem(T7Items.INSTANCE.getIRON_HANDLE().get());
    basicItem(T7Items.INSTANCE.getGOLD_HANDLE().get());
    basicItem(T7Items.INSTANCE.getORICHALCUM_HANDLE().get());
    basicItem(T7Items.INSTANCE.getARCANUM_HANDLE().get());

    basicItem(T7Items.INSTANCE.getEYE_OF_WARDEN().get());
    basicItem(T7Items.INSTANCE.getSIGIL().get());

    basicItem(T7Items.INSTANCE.getGREATWOOD_CORE().get());
    basicItem(T7Items.INSTANCE.getSILVERWOOD_CORE().get());

    basicItem(T7Items.INSTANCE.getRUNE().get());
    basicItem(T7Items.INSTANCE.getARCANUM_INGOT().get());
    basicItem(T7Items.INSTANCE.getARCANUM_NUGGET().get());
    basicItem(T7Items.INSTANCE.getORICHALCUM_INGOT().get());
    basicItem(T7Items.INSTANCE.getORICHALCUM_NUGGET().get());
    basicItem(T7Items.INSTANCE.getRESEARCH_SCROLL().get());
    basicItem(T7Items.INSTANCE.getCOMPLETED_RESEARCH().get());

    basicItem(T7Items.INSTANCE.getGOGGLES().get());
    basicItem(T7Items.INSTANCE.getGOGGLES_ACCESSORY().get());
    basicItem(T7Items.INSTANCE.getDAWN_CHARM().get());
    basicItem(T7Items.INSTANCE.getRESEARCHER_CHESTPLATE().get());
    basicItem(T7Items.INSTANCE.getRESEARCHER_LEGGINGS().get());
    basicItem(T7Items.INSTANCE.getRESEARCHER_BOOTS().get());

    basicItem(T7Items.INSTANCE.getARCANUM_HELMET().get());
    basicItem(T7Items.INSTANCE.getARCANUM_CHESTPLATE().get());
    basicItem(T7Items.INSTANCE.getARCANUM_LEGGINGS().get());
    basicItem(T7Items.INSTANCE.getARCANUM_BOOTS().get());

    basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_HELMET().get());
    basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_CHESTPLATE().get());
    basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_LEGGINGS().get());
    basicItem(T7Items.INSTANCE.getCUSTOS_ARCANUM_BOOTS().get());

    handheldItem(T7Items.INSTANCE.getARCANUM_SWORD());
    handheldItem(T7Items.INSTANCE.getARCANUM_AXE());
    handheldItem(T7Items.INSTANCE.getARCANUM_PICKAXE());
    handheldItem(T7Items.INSTANCE.getARCANUM_HAMMER());
    handheldItem(T7Items.INSTANCE.getARCANUM_SHOVEL());
    handheldItem(T7Items.INSTANCE.getARCANUM_HOE());

    for (var testa : T7Items.INSTANCE.getTESTAS().values())
      withVanillaParent(testa.getId().getPath(), "testa", "generated");

    for (var wand : T7Items.INSTANCE.getWANDS().values())
      withExistingParent(wand.getName(), Thavma.INSTANCE.rl("wand"));

    withExistingParent(T7Items.INSTANCE.getANGRY_ZOMBIE_SPAWN_EGG().getId().getPath(), "template_spawn_egg");
  }

  public void handheldItem(DeferredItem<? extends Item> deferredItem) {
    withVanillaParent(deferredItem, "handheld");
  }

  public void withVanillaParent(DeferredItem<? extends Item> deferredItem, String parent) {
    var path = deferredItem.getId().getPath();
    withVanillaParent(path, parent);
  }

  public void withVanillaParent(String itemPath, String parent) {
    withVanillaParent(itemPath, itemPath, parent);
  }

  public void withVanillaParent(String itemPath, String texturePath, String parent) {
    withExistingParent(itemPath, parent)
        .texture("layer0", Thavma.INSTANCE.rl(texturePath).withPrefix("item/"));
  }
}
