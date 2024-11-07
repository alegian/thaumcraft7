package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import me.alegian.thavma.impl.init.registries.deferred.util.DeferredWandHandleMaterial;
import me.alegian.thavma.impl.init.registries.deferred.util.T7DeferredRegister;

public class WandHandleMaterials {
  public static final T7DeferredRegister.WandHandleMaterials REGISTRAR = T7DeferredRegister.createWandHandleMaterials(Thavma.MODID);

  public static final DeferredWandHandleMaterial<WandHandleMaterial> IRON = register("iron");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> GOLD = register("gold");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ORICHALCUM = register("orichalcum");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ARCANUM = register("arcanum");

  private static DeferredWandHandleMaterial<WandHandleMaterial> register(String name) {
    return REGISTRAR.registerWandHandleMaterial(name, WandHandleMaterial::new);
  }
}
