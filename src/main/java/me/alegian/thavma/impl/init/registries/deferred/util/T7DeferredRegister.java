package me.alegian.thavma.impl.init.registries.deferred.util;

import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.init.registries.T7Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class T7DeferredRegister<T> extends DeferredRegister<T> {
  protected T7DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
    super(registryKey, namespace);
  }

  public static T7DeferredRegister.Aspects createAspects(String modid) {
    return new Aspects(modid);
  }

  public static class Aspects extends DeferredRegister<Aspect> {
    protected Aspects(String namespace) {
      super(T7Registries.ASPECT.key(), namespace);
    }

    @SuppressWarnings("unchecked")
    public <A extends Aspect> DeferredAspect<A> registerAspect(String name, Supplier<? extends A> sup) {
      return (DeferredAspect<A>) this.register(name, key -> sup.get());
    }

    @Override
    protected <A extends Aspect> DeferredAspect<A> createHolder(ResourceKey<? extends Registry<Aspect>> registryKey, ResourceLocation key) {
      return DeferredAspect.createAspect(ResourceKey.create(registryKey, key));
    }
  }
}
