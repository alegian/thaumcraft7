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
    basicItem(T7Items.IRON_HANDLE.get());
    basicItem(T7Items.GOLD_HANDLE.get());
    basicItem(T7Items.ORICHALCUM_HANDLE.get());
    basicItem(T7Items.ARCANUM_HANDLE.get());

    basicItem(T7Items.GREATWOOD_CORE.get());
    basicItem(T7Items.SILVERWOOD_CORE.get());

    basicItem(T7Items.RUNE.get());
    basicItem(T7Items.ARCANUM_INGOT.get());
    basicItem(T7Items.ARCANUM_NUGGET.get());
    basicItem(T7Items.ORICHALCUM_INGOT.get());
    basicItem(T7Items.ORICHALCUM_NUGGET.get());
    basicItem(T7Items.RESEARCH_SCROLL.get());
    basicItem(T7Items.COMPLETED_RESEARCH.get());

    basicItem(T7Items.GOGGLES.get());
    basicItem(T7Items.RESEARCHER_CHESTPLATE.get());
    basicItem(T7Items.RESEARCHER_LEGGINGS.get());
    basicItem(T7Items.RESEARCHER_BOOTS.get());

    basicItem(T7Items.ARCANUM_HELMET.get());
    basicItem(T7Items.ARCANUM_CHESTPLATE.get());
    basicItem(T7Items.ARCANUM_LEGGINGS.get());
    basicItem(T7Items.ARCANUM_BOOTS.get());

    basicItem(T7Items.CUSTOS_ARCANUM_HELMET.get());
    basicItem(T7Items.CUSTOS_ARCANUM_CHESTPLATE.get());
    basicItem(T7Items.CUSTOS_ARCANUM_LEGGINGS.get());
    basicItem(T7Items.CUSTOS_ARCANUM_BOOTS.get());

    handheldItem(T7Items.ARCANUM_SWORD);
    handheldItem(T7Items.ARCANUM_AXE);
    handheldItem(T7Items.ARCANUM_PICKAXE);
    handheldItem(T7Items.ARCANUM_HAMMER);
    handheldItem(T7Items.ARCANUM_SHOVEL);
    handheldItem(T7Items.ARCANUM_HOE);

    testaItem(T7Items.IGNIS_TESTA);
    testaItem(T7Items.AER_TESTA);
    testaItem(T7Items.TERRA_TESTA);
    testaItem(T7Items.AQUA_TESTA);
    testaItem(T7Items.ORDO_TESTA);
    testaItem(T7Items.PERDITIO_TESTA);

    for (var wand : T7Items.WANDS.values()) {
      withExistingParent(wand.getName(), Thavma.id("wand"));
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
        .texture("layer0", Thavma.id(texturePath).withPrefix("item/"));
  }
}
