package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class T7DataMaps {
  public static class AspectContent {
    public static final DataMapType<Item, AspectMap> ITEM = DataMapType.builder(
        Thaumcraft.id("aspect_content"),
        Registries.ITEM,
        AspectMap.CODEC
    ).synced(
        AspectMap.CODEC,
        true
    ).build();

    public static final DataMapType<Block, AspectMap> BLOCK = DataMapType.builder(
        Thaumcraft.id("aspect_content"),
        Registries.BLOCK,
        AspectMap.CODEC
    ).synced(
        AspectMap.CODEC,
        true
    ).build();
  }
}
