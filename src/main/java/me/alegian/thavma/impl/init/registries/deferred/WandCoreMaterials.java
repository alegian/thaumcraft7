package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.init.registries.deferred.util.DeferredWandCoreMaterial;
import me.alegian.thavma.impl.init.registries.deferred.util.T7DeferredRegister;

public class WandCoreMaterials {
  public static final T7DeferredRegister.WandCoreMaterials REGISTRAR = T7DeferredRegister.createWandCoreMaterials(Thavma.MODID);

  public static final DeferredWandCoreMaterial<WandCoreMaterial> WOOD = register("wood", 25);
  public static final DeferredWandCoreMaterial<WandCoreMaterial> GREATWOOD = register("greatwood", 50);
  public static final DeferredWandCoreMaterial<WandCoreMaterial> SILVERWOOD = register("silverwood", 100);

  private static DeferredWandCoreMaterial<WandCoreMaterial> register(String name, int capacity) {
    return REGISTRAR.registerWandCoreMaterial(name, () -> new WandCoreMaterial(capacity));
  }
}
