package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.T7DeferredRegister;

import java.util.List;

public class WandCoreMaterials {
  public static final T7DeferredRegister.WandCoreMaterials REGISTRAR = T7DeferredRegister.createWandCoreMaterials(Thaumcraft.MODID);

  public static final DeferredWandCoreMaterial<WandCoreMaterial> WOOD = REGISTRAR.registerWandCoreMaterial("wood", WandCoreMaterial::new);
  public static final DeferredWandCoreMaterial<WandCoreMaterial> GREATWOOD = REGISTRAR.registerWandCoreMaterial("greatwood", WandCoreMaterial::new);
  public static final DeferredWandCoreMaterial<WandCoreMaterial> SILVERWOOD = REGISTRAR.registerWandCoreMaterial("silverwood", WandCoreMaterial::new);

  public static final List<DeferredWandCoreMaterial<WandCoreMaterial>> ALL = List.of(WOOD, GREATWOOD, SILVERWOOD);
}
