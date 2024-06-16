package me.alegian.thaumcraft7.data.component;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TCDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTRAR = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Thaumcraft.MODID);

    public static final Supplier<DataComponentType<VisDataComponent.Vis>> VIS = REGISTRAR.register("vis",
            () -> DataComponentType.<VisDataComponent.Vis>builder()
                    // The codec to read/write the data to disk
                    .persistent(VisDataComponent.CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(VisDataComponent.STREAM_CODEC)
                    .build()
    );

}
