package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class T7ItemModelProvider extends ItemModelProvider {
  public T7ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, Thaumcraft.MODID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    basicItem(T7Items.GOGGLES.get());
    basicItem(T7Items.IRON_CAP.get());
    basicItem(T7Items.THAUMONOMICON.get());
  }
}
