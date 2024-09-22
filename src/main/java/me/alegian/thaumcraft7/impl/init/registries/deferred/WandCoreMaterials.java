package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WandCoreMaterials {
  public static final DeferredRegister<WandCoreMaterial> REGISTRAR = DeferredRegister.create(T7Registries.WAND_CORE, Thaumcraft.MODID);

  public static final DeferredHolder<WandCoreMaterial, WandCoreMaterial> WOOD = REGISTRAR.register("wood", WandCoreMaterial::new);
  public static final DeferredHolder<WandCoreMaterial, WandCoreMaterial> GREATWOOD = REGISTRAR.register("greatwood", WandCoreMaterial::new);
  public static final DeferredHolder<WandCoreMaterial, WandCoreMaterial> SILVERWOOD = REGISTRAR.register("silverwood", WandCoreMaterial::new);
}
