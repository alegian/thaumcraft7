package me.alegian.thavma.impl.init.registries.deferred.util;

import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
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

  public static T7DeferredRegister.WandCoreMaterials createWandCoreMaterials(String modid) {
    return new WandCoreMaterials(modid);
  }

  public static T7DeferredRegister.WandHandleMaterials createWandHandleMaterials(String modid) {
    return new WandHandleMaterials(modid);
  }

  public static T7DeferredRegister.Aspects createAspects(String modid) {
    return new Aspects(modid);
  }

  public static class WandCoreMaterials extends DeferredRegister<WandCoreMaterial> {
    protected WandCoreMaterials(String namespace) {
      super(T7Registries.WAND_CORE.key(), namespace);
    }

    @SuppressWarnings("unchecked")
    public <M extends WandCoreMaterial> DeferredWandCoreMaterial<M> registerWandCoreMaterial(String name, Supplier<? extends M> sup) {
      return (DeferredWandCoreMaterial<M>) this.register(name, key -> sup.get());
    }

    @Override
    protected <I extends WandCoreMaterial> DeferredWandCoreMaterial<I> createHolder(ResourceKey<? extends Registry<WandCoreMaterial>> registryKey, ResourceLocation key) {
      return DeferredWandCoreMaterial.createMaterial(ResourceKey.create(registryKey, key));
    }
  }

  public static class WandHandleMaterials extends DeferredRegister<WandHandleMaterial> {
    protected WandHandleMaterials(String namespace) {
      super(T7Registries.WAND_HANDLE.key(), namespace);
    }

    @SuppressWarnings("unchecked")
    public <M extends WandHandleMaterial> DeferredWandHandleMaterial<M> registerWandHandleMaterial(String name, Supplier<? extends M> sup) {
      return (DeferredWandHandleMaterial<M>) this.register(name, key -> sup.get());
    }

    @Override
    protected <I extends WandHandleMaterial> DeferredWandHandleMaterial<I> createHolder(ResourceKey<? extends Registry<WandHandleMaterial>> registryKey, ResourceLocation key) {
      return DeferredWandHandleMaterial.createMaterial(ResourceKey.create(registryKey, key));
    }
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
