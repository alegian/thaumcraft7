package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.research.Research;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class T7Registries {
  public static final Registry<WandHandleMaterial> WAND_HANDLE = new RegistryBuilder<WandHandleMaterial>(ResourceKey.createRegistryKey(Thaumcraft.id("wand_handle")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<WandCoreMaterial> WAND_CORE = new RegistryBuilder<WandCoreMaterial>(ResourceKey.createRegistryKey(Thaumcraft.id("wand_core")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<Aspect> ASPECT = new RegistryBuilder<Aspect>(ResourceKey.createRegistryKey(Thaumcraft.id("aspect")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<Research> RESEARCH = new RegistryBuilder<Research>(ResourceKey.createRegistryKey(Thaumcraft.id("research")))
      .maxId(Integer.MAX_VALUE)
      .create();
}
