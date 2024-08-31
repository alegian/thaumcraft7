package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.TestaItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
    basicItem(T7Items.RUNE.get());
    basicItem(T7Items.ARCANUM_INGOT.get());
    basicItem(T7Items.ARCANUM_NUGGET.get());
    basicItem(T7Items.ORICHALCUM_INGOT.get());
    basicItem(T7Items.ORICHALCUM_NUGGET.get());

    testaItem(T7Items.IGNIS_TESTA);
    testaItem(T7Items.AER_TESTA);
    testaItem(T7Items.TERRA_TESTA);
    testaItem(T7Items.AQUA_TESTA);
    testaItem(T7Items.ORDO_TESTA);
    testaItem(T7Items.PERDITIO_TESTA);
  }

  public void testaItem(DeferredItem<TestaItem> deferredItem) {
    getBuilder(deferredItem.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("item/generated"))
        .texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/testa"));
  }
}
