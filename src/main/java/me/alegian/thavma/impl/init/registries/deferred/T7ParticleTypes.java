package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7ParticleTypes {
  public static final DeferredRegister<ParticleType<?>> REGISTRAR =
      DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Thavma.MODID);

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ASPECTS = REGISTRAR.register(
      "aspects",
      () -> new SimpleParticleType(true)
  );

  public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRUCIBLE_BUBBLE = REGISTRAR.register(
      "crucible_bubble",
      () -> new SimpleParticleType(true)
  );
}
