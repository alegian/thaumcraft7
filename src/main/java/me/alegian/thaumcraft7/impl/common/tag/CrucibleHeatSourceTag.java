package me.alegian.thaumcraft7.impl.common.tag;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class CrucibleHeatSourceTag {
  public static final TagKey<Block> BLOCK = TagKey.create(
      Registries.BLOCK,
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "crucible_heat_source")
  );

  public static final TagKey<Fluid> FLUID = TagKey.create(
      Registries.FLUID,
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "crucible_heat_source")
  );
}
