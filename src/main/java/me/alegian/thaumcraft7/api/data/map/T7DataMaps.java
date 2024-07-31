package me.alegian.thaumcraft7.api.data.map;

import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class T7DataMaps {
  public static final DataMapType<Item, AspectList> ASPECT_CONTENT = DataMapType.builder(
      ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "aspect_content"),
      Registries.ITEM,
      AspectList.CODEC
  ).synced(
      AspectList.CODEC,
      true
  ).build();
}
