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
    add(T7Items.ARCANUM_INGOT.get(), "Arcanum Ingot");
    add(T7Items.ARCANUM_NUGGET.get(), "Arcanum Nugget");
    add(T7Items.ORICHALCUM_INGOT.get(), "Orichalcum Ingot");
    add(T7Items.ORICHALCUM_NUGGET.get(), "Orichalcum Nugget");
    add(T7Items.RESEARCH_SCROLL.get(), "Research Scroll");
    add(T7Items.COMPLETED_RESEARCH.get(), "Completed Research");
    add(T7Items.IRON_WOOD_WAND.get(), "Iron Handle Wooden Wand");
    add(T7Items.THAUMOMETER.get(), "Thaumometer");
    add(T7Items.THAUMONOMICON.get(), "Thaumonomicon");
    add(T7Items.GOGGLES.get(), "Goggles Of Revealing");
    add(T7Items.IGNIS_TESTA.get(), "Ignis Testa");
    add(T7Items.AER_TESTA.get(), "Aer Testa");
    add(T7Items.TERRA_TESTA.get(), "Terra Testa");
    add(T7Items.AQUA_TESTA.get(), "Aqua Testa");
    add(T7Items.ORDO_TESTA.get(), "Ordo Testa");
    add(T7Items.PERDITIO_TESTA.get(), "Perditio Testa");

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
