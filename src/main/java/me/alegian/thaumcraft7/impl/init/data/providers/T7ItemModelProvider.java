package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.TestaItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class T7ItemModelProvider extends ItemModelProvider {
  public T7ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, Thaumcraft.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    basicItem(T7Items.GOGGLES.get());

    basicItem(T7Items.IRON_HANDLE.get());
    basicItem(T7Items.GOLD_HANDLE.get());
    basicItem(T7Items.ORICHALCUM_HANDLE.get());
    basicItem(T7Items.ARCANUM_HANDLE.get());

    basicItem(T7Items.RUNE.get());
    basicItem(T7Items.ARCANUM_INGOT.get());
    basicItem(T7Items.ARCANUM_NUGGET.get());
    basicItem(T7Items.ORICHALCUM_INGOT.get());
    basicItem(T7Items.ORICHALCUM_NUGGET.get());
    basicItem(T7Items.RESEARCH_SCROLL.get());
    basicItem(T7Items.COMPLETED_RESEARCH.get());

    handheldItem(T7Items.ARCANUM_SWORD);
    handheldItem(T7Items.ARCANUM_AXE);
    handheldItem(T7Items.ARCANUM_PICKAXE);
    handheldItem(T7Items.ARCANUM_SHOVEL);
    handheldItem(T7Items.ARCANUM_HOE);

    testaItem(T7Items.IGNIS_TESTA);
    testaItem(T7Items.AER_TESTA);
    testaItem(T7Items.TERRA_TESTA);
    testaItem(T7Items.AQUA_TESTA);
    testaItem(T7Items.ORDO_TESTA);
    testaItem(T7Items.PERDITIO_TESTA);

    for (var wand : T7Items.WANDS.values()) {
      withExistingParent(wand.get().getName(), ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "wand"));
    }
  }

  public void testaItem(DeferredItem<TestaItem> deferredItem) {
    withVanillaParent(deferredItem.getId().getPath(), "testa", "generated");
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
        .texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/" + texturePath));
  }
}
