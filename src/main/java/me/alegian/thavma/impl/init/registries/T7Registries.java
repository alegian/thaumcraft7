package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.common.research.Research;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class T7Registries {
  public static final Registry<WandHandleMaterial> WAND_HANDLE = new RegistryBuilder<WandHandleMaterial>(ResourceKey.createRegistryKey(Thavma.rl("wand_handle")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<WandCoreMaterial> WAND_CORE = new RegistryBuilder<WandCoreMaterial>(ResourceKey.createRegistryKey(Thavma.rl("wand_core")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<Aspect> ASPECT = new RegistryBuilder<Aspect>(ResourceKey.createRegistryKey(Thavma.rl("aspect")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<Research> RESEARCH = new RegistryBuilder<Research>(ResourceKey.createRegistryKey(Thavma.rl("research")))
      .maxId(Integer.MAX_VALUE)
      .create();
}
