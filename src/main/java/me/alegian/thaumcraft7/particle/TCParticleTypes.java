package me.alegian.thaumcraft7.particle;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRAR =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Thaumcraft.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ASPECTS = REGISTRAR.register(
            "aspects",
            () -> new SimpleParticleType(true)
    );
}
