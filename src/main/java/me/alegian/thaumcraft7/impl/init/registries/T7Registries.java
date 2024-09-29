package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class T7Registries {
  public static final Registry<WandHandleMaterial> WAND_HANDLE = new RegistryBuilder<WandHandleMaterial>(ResourceKey.createRegistryKey(Thaumcraft.id("wand_handle")))
      .maxId(Integer.MAX_VALUE)
      .create();

  public static final Registry<WandCoreMaterial> WAND_CORE = new RegistryBuilder<WandCoreMaterial>(ResourceKey.createRegistryKey(Thaumcraft.id("wand_core")))
      .maxId(Integer.MAX_VALUE)
      .create();
}
