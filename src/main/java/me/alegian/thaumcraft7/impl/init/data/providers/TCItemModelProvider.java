package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TCItemModelProvider extends ItemModelProvider {
  public TCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, Thaumcraft.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    basicItem(TCItems.GOGGLES.get());
    basicItem(TCItems.IRON_CAP.get());
    basicItem(TCItems.THAUMONOMICON.get());
  }
}
