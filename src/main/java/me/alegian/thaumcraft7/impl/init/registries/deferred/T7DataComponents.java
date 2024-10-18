package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7DataComponents {
  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Thaumcraft.MODID);

  public static final Supplier<DataComponentType<AspectMap>> ASPECTS = REGISTRAR.registerComponentType("aspects",
      (builder) -> builder
          .persistent(AspectMap.CODEC)
          .networkSynchronized(AspectMap.STREAM_CODEC)
  );

}
