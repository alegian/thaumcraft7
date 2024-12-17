package me.alegian.thavma.impl.init.registries.deferred.callback;

import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.callback.AddCallback;

/**
 * Registry Callback to register all wand combinations of current
 * handle being registered.
 */
public class WandHandleCombinations implements AddCallback<WandHandleMaterial> {
  private Registry<Item> itemRegistry;
  private Registry<WandCoreMaterial> coreRegistry;

  public WandHandleCombinations(Registry<Item> itemRegistry, Registry<WandCoreMaterial> coreRegistry) {
    this.coreRegistry = coreRegistry;
    this.itemRegistry = itemRegistry;
  }

  @Override
  public void onAdd(Registry<WandHandleMaterial> handleRegistry, int id, ResourceKey<WandHandleMaterial> key, WandHandleMaterial newHandle) {
    for (var core : this.coreRegistry)
      if (core.registerCombinations && !T7Items.INSTANCE.isWandRegistered(newHandle, core))
        T7Items.INSTANCE.registerWand(this.itemRegistry, newHandle, core);
  }
}
