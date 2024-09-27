package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.T7DeferredRegister;

import java.util.List;

public class WandHandleMaterials {
  public static final T7DeferredRegister.WandHandleMaterials REGISTRAR = T7DeferredRegister.createWandHandleMaterials(Thaumcraft.MODID);

  public static final DeferredWandHandleMaterial<WandHandleMaterial> IRON = register("iron");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> GOLD = register("gold");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ORICHALCUM = register("orichalcum");
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ARCANUM = register("arcanum");

  public static final List<DeferredWandHandleMaterial<WandHandleMaterial>> ALL = List.of(IRON, GOLD, ORICHALCUM, ARCANUM);

  private static DeferredWandHandleMaterial<WandHandleMaterial> register(String name) {
    return REGISTRAR.registerWandHandleMaterial(name, WandHandleMaterial::new);
  }
}
