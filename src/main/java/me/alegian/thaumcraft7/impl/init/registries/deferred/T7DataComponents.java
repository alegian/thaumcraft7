package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.component.VisDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7DataComponents {
  public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Thaumcraft.MODID);

  public static final Supplier<DataComponentType<VisDataComponent.Vis>> VIS = REGISTRAR.registerComponentType("vis",
      (builder) -> builder
          // The codec to read/write the data to disk
          .persistent(VisDataComponent.CODEC)
          // The codec to read/write the data across the network
          .networkSynchronized(VisDataComponent.STREAM_CODEC)
  );

}
