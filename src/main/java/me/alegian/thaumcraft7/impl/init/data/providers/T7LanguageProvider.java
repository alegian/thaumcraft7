package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class T7LanguageProvider extends LanguageProvider {
  public T7LanguageProvider(PackOutput output, String locale) {
    super(output, Thaumcraft.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    add("thaumcraft", "Thaumcraft 7");

    add(T7Items.IRON_HANDLE.get(), "Iron Wand Handle");
    add(T7Items.RUNE.get(), "Rune");
    add(T7Items.IRON_WOOD_WAND.get(), "Iron Handle Wooden Wand");
    add(T7Items.THAUMOMETER.get(), "Thaumometer");
    add(T7Items.THAUMONOMICON.get(), "Thaumonomicon");
    add(T7Items.GOGGLES.get(), "Goggles Of Revealing");
    add(T7Items.IGNIS_SHARD.get(), "Ignis Shard");
    add(T7Items.AER_SHARD.get(), "Aer Shard");
    add(T7Items.TERRA_SHARD.get(), "Terra Shard");
    add(T7Items.AQUA_SHARD.get(), "Aqua Shard");
    add(T7Items.ORDO_SHARD.get(), "Ordo Shard");
    add(T7Items.PERDITIO_SHARD.get(), "Perditio Shard");

    add(T7Blocks.AURA_NODE.get(), "Aura Node");
    add(T7Blocks.CRUCIBLE.get(), "Crucible");
    add(T7Blocks.IGNIS_INFUSED_STONE.get(), "Ignis Infused Stone");
    add(T7Blocks.AER_INFUSED_STONE.get(), "Aer Infused Stone");
    add(T7Blocks.TERRA_INFUSED_STONE.get(), "Terra Infused Stone");
    add(T7Blocks.AQUA_INFUSED_STONE.get(), "Aqua Infused Stone");
    add(T7Blocks.ORDO_INFUSED_STONE.get(), "Ordo Infused Stone");
    add(T7Blocks.PERDITIO_INFUSED_STONE.get(), "Perditio Infused Stone");
    add(T7Blocks.GREATWOOD_LOG.get(), "Greatwood Log");
    add(T7Blocks.GREATWOOD_LEAVES.get(), "Greatwood Leaves");
    add(T7Blocks.GREATWOOD_PLANKS.get(), "Greatwood Planks");
    add(T7Blocks.GREATWOOD_SAPLING.get(), "Greatwood Sapling");
  }
}
