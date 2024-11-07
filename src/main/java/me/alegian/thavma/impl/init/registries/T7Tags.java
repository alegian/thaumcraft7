package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class T7Tags {
  public static final String COMMON = "c";

  public static class CrucibleHeatSourceTag {
    public static final TagKey<Block> BLOCK = TagKey.create(
        Registries.BLOCK,
        Thavma.id("crucible_heat_source")
    );

    public static final TagKey<Fluid> FLUID = TagKey.create(
        Registries.FLUID,
        Thavma.id("crucible_heat_source")
    );
  }

  public static final TagKey<Item> WAND_HANDLE = TagKey.create(
      Registries.ITEM,
      Thavma.id("wand_handle")
  );

  public static final TagKey<Item> WAND_CORE = TagKey.create(
      Registries.ITEM,
      Thavma.id("wand_core")
  );

  public static final TagKey<Item> TESTA = TagKey.create(
      Registries.ITEM,
      Thavma.id("testa")
  );

  public static final TagKey<Item> CATALYST = TagKey.create(
      Registries.ITEM,
      Thavma.id("catalyst")
  );
}
