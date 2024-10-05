package me.alegian.thaumcraft7.impl.init.registries.deferred.callback;

import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.callback.AddCallback;

/**
 * Registry Callback to register all wand combinations of current
 * core being registered.
 */
public class WandCoreCombinations implements AddCallback<WandCoreMaterial> {
  private Registry<Item> itemRegistry;
  private Registry<WandHandleMaterial> handleRegistry;

  public WandCoreCombinations(Registry<Item> itemRegistry, Registry<WandHandleMaterial> handleRegistry) {
    this.handleRegistry = handleRegistry;
    this.itemRegistry = itemRegistry;
  }

  @Override
  public void onAdd(Registry<WandCoreMaterial> coreRegistry, int id, ResourceKey<WandCoreMaterial> key, WandCoreMaterial newCore) {
    for (var handle : handleRegistry) {
      if (handle.registerCombinations() && !T7Items.isWandRegistered(handle, newCore))
        T7Items.registerWand(itemRegistry, handle, newCore);
    }
  }
}
