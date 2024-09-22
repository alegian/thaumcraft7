package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.T7DeferredRegister;

public class WandHandleMaterials {
  public static final T7DeferredRegister.WandHandleMaterials REGISTRAR = T7DeferredRegister.createWandHandleMaterials(Thaumcraft.MODID);

  public static final DeferredWandHandleMaterial<WandHandleMaterial> IRON = REGISTRAR.registerWandHandleMaterial("iron", WandHandleMaterial::new);
  public static final DeferredWandHandleMaterial<WandHandleMaterial> GOLD = REGISTRAR.registerWandHandleMaterial("gold", WandHandleMaterial::new);
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ORICHALCUM = REGISTRAR.registerWandHandleMaterial("orichalcum", WandHandleMaterial::new);
  public static final DeferredWandHandleMaterial<WandHandleMaterial> ARCANUM = REGISTRAR.registerWandHandleMaterial("arcanum", WandHandleMaterial::new);
}
