package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.T7DeferredRegister;

import java.util.List;

public class WandCoreMaterials {
  public static final T7DeferredRegister.WandCoreMaterials REGISTRAR = T7DeferredRegister.createWandCoreMaterials(Thaumcraft.MODID);

  public static final DeferredWandCoreMaterial<WandCoreMaterial> WOOD = register("wood", "Wooden");
  public static final DeferredWandCoreMaterial<WandCoreMaterial> GREATWOOD = register("greatwood", "Greatwood");
  public static final DeferredWandCoreMaterial<WandCoreMaterial> SILVERWOOD = register("silverwood", "Silverwood");

  public static final List<DeferredWandCoreMaterial<WandCoreMaterial>> ALL = List.of(WOOD, GREATWOOD, SILVERWOOD);

  private static DeferredWandCoreMaterial<WandCoreMaterial> register(String name, String displayName) {
    return REGISTRAR.registerWandCoreMaterial(name, () -> new WandCoreMaterial(displayName));
  }
}
