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
    this.basicItem(T7Items.IRON_HANDLE.get());
    this.basicItem(T7Items.GOLD_HANDLE.get());
    this.basicItem(T7Items.ORICHALCUM_HANDLE.get());
    this.basicItem(T7Items.ARCANUM_HANDLE.get());

    this.basicItem(T7Items.GREATWOOD_CORE.get());
    this.basicItem(T7Items.SILVERWOOD_CORE.get());

    this.basicItem(T7Items.RUNE.get());
    this.basicItem(T7Items.ARCANUM_INGOT.get());
    this.basicItem(T7Items.ARCANUM_NUGGET.get());
    this.basicItem(T7Items.ORICHALCUM_INGOT.get());
    this.basicItem(T7Items.ORICHALCUM_NUGGET.get());
    this.basicItem(T7Items.RESEARCH_SCROLL.get());
    this.basicItem(T7Items.COMPLETED_RESEARCH.get());

    this.basicItem(T7Items.GOGGLES.get());
    this.basicItem(T7Items.DAWN_CHARM.get());
    this.basicItem(T7Items.RESEARCHER_CHESTPLATE.get());
    this.basicItem(T7Items.RESEARCHER_LEGGINGS.get());
    this.basicItem(T7Items.RESEARCHER_BOOTS.get());

    this.basicItem(T7Items.ARCANUM_HELMET.get());
    this.basicItem(T7Items.ARCANUM_CHESTPLATE.get());
    this.basicItem(T7Items.ARCANUM_LEGGINGS.get());
    this.basicItem(T7Items.ARCANUM_BOOTS.get());

    this.basicItem(T7Items.CUSTOS_ARCANUM_HELMET.get());
    this.basicItem(T7Items.CUSTOS_ARCANUM_CHESTPLATE.get());
    this.basicItem(T7Items.CUSTOS_ARCANUM_LEGGINGS.get());
    this.basicItem(T7Items.CUSTOS_ARCANUM_BOOTS.get());

    this.handheldItem(T7Items.ARCANUM_SWORD);
    this.handheldItem(T7Items.ARCANUM_AXE);
    this.handheldItem(T7Items.ARCANUM_PICKAXE);
    this.handheldItem(T7Items.ARCANUM_HAMMER);
    this.handheldItem(T7Items.ARCANUM_SHOVEL);
    this.handheldItem(T7Items.ARCANUM_HOE);

    this.testaItem(T7Items.IGNIS_TESTA);
    this.testaItem(T7Items.AER_TESTA);
    this.testaItem(T7Items.TERRA_TESTA);
    this.testaItem(T7Items.AQUA_TESTA);
    this.testaItem(T7Items.ORDO_TESTA);
    this.testaItem(T7Items.PERDITIO_TESTA);

    for (var wand : T7Items.WANDS.values()) this.withExistingParent(wand.getName(), Thavma.rl("wand"));
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
        .texture("layer0", Thavma.rl(texturePath).withPrefix("item/"));
  }
}
