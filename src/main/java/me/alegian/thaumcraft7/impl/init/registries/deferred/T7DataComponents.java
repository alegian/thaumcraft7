package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.AspectList;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7DataComponents {
  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Thaumcraft.MODID);

  public static final Supplier<DataComponentType<AspectList>> ASPECTS = REGISTRAR.registerComponentType("aspects",
      (builder) -> builder
          .persistent(AspectList.CODEC)
          .networkSynchronized(AspectList.STREAM_CODEC)
  );

}
