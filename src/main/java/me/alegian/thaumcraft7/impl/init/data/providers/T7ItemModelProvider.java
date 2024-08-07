package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.ShardItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.registries.BuiltInRegistries;
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
    basicItem(T7Items.IRON_CAP.get());
    basicItem(T7Items.THAUMONOMICON.get());

    shardItem(T7Items.IGNIS_SHARD);
    shardItem(T7Items.AER_SHARD);
    shardItem(T7Items.TERRA_SHARD);
    shardItem(T7Items.AQUA_SHARD);
    shardItem(T7Items.ORDO_SHARD);
    shardItem(T7Items.PERDITIO_SHARD);
  }

  public void shardItem(DeferredItem<ShardItem> deferredItem) {
    getBuilder(deferredItem.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("item/generated"))
        .texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/shard"));
  }
}
