package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.AspectList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class T7DataMaps {
  public static class AspectContent {
    public static final DataMapType<Item, AspectList> ITEM = DataMapType.builder(
        Thaumcraft.id("aspect_content"),
        Registries.ITEM,
        AspectList.CODEC
    ).synced(
        AspectList.CODEC,
        true
    ).build();

    public static final DataMapType<Block, AspectList> BLOCK = DataMapType.builder(
        Thaumcraft.id("aspect_content"),
        Registries.BLOCK,
        AspectList.CODEC
    ).synced(
        AspectList.CODEC,
        true
    ).build();
  }
}
