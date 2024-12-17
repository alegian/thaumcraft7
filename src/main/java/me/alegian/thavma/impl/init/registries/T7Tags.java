package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class T7Tags {
  public static final String COMMON = "c";
  public static final TagKey<Item> WAND_HANDLE = TagKey.create(
      Registries.ITEM,
      Thavma.INSTANCE.rl("wand_handle")
  );
  public static final TagKey<Item> WAND_CORE = TagKey.create(
      Registries.ITEM,
      Thavma.INSTANCE.rl("wand_core")
  );
  public static final TagKey<Item> TESTA = TagKey.create(
      Registries.ITEM,
      Thavma.INSTANCE.rl("testa")
  );
  public static final TagKey<Item> CATALYST = TagKey.create(
      Registries.ITEM,
      Thavma.INSTANCE.rl("catalyst")
  );

  public static final TagKey<DamageType> SONIC = TagKey.create(
      Registries.DAMAGE_TYPE,
      Thavma.INSTANCE.rl("sonic")
  );

  public static class CrucibleHeatSourceTag {
    public static final TagKey<Block> BLOCK = TagKey.create(
        Registries.BLOCK,
        Thavma.INSTANCE.rl("crucible_heat_source")
    );

    public static final TagKey<Fluid> FLUID = TagKey.create(
        Registries.FLUID,
        Thavma.INSTANCE.rl("crucible_heat_source")
    );
  }
}
