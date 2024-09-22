package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WandHandleMaterials {
  public static final DeferredRegister<WandHandleMaterial> REGISTRAR = DeferredRegister.create(T7Registries.WAND_HANDLE, Thaumcraft.MODID);

  public static final DeferredHolder<WandHandleMaterial, WandHandleMaterial> IRON = REGISTRAR.register("iron", WandHandleMaterial::new);
  public static final DeferredHolder<WandHandleMaterial, WandHandleMaterial> GOLD = REGISTRAR.register("gold", WandHandleMaterial::new);
  public static final DeferredHolder<WandHandleMaterial, WandHandleMaterial> ORICHALCUM = REGISTRAR.register("orichalcum", WandHandleMaterial::new);
  public static final DeferredHolder<WandHandleMaterial, WandHandleMaterial> ARCANUM = REGISTRAR.register("arcanum", WandHandleMaterial::new);
}
