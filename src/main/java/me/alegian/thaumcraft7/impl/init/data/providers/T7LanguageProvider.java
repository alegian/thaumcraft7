package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import me.alegian.thaumcraft7.impl.init.registries.deferred.WandCoreMaterials;
import me.alegian.thaumcraft7.impl.init.registries.deferred.WandHandleMaterials;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.HashMap;
import java.util.Map;

public class T7LanguageProvider extends LanguageProvider {
  public T7LanguageProvider(PackOutput output, String locale) {
    super(output, Thaumcraft.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    add("thaumcraft", "Thaumcraft 7");

    add(T7Items.IRON_HANDLE.get(), "Iron Wand Handle");
    add(T7Items.GOLD_HANDLE.get(), "Gold Wand Handle");
    add(T7Items.ORICHALCUM_HANDLE.get(), "Orichalcum Wand Handle");
    add(T7Items.ARCANUM_HANDLE.get(), "Arcanum Wand Handle");

    add(T7Items.GREATWOOD_CORE.get(), "Greatwood Wand Core");
    add(T7Items.SILVERWOOD_CORE.get(), "Silverwood Wand Core");

    add(T7Items.RUNE.get(), "Rune");
    add(T7Items.ARCANUM_INGOT.get(), "Arcanum Ingot");
    add(T7Items.ARCANUM_NUGGET.get(), "Arcanum Nugget");
    add(T7Items.ORICHALCUM_INGOT.get(), "Orichalcum Ingot");
    add(T7Items.ORICHALCUM_NUGGET.get(), "Orichalcum Nugget");
    add(T7Items.RESEARCH_SCROLL.get(), "Research Scroll");
    add(T7Items.COMPLETED_RESEARCH.get(), "Completed Research");
    add(T7Items.THAUMOMETER.get(), "Thaumometer");
    add(T7Items.THAUMONOMICON.get(), "Thaumonomicon");

    add(T7Items.GOGGLES.get(), "Goggles Of Revealing");
    add(T7Items.ARCANUM_BOOTS.get(), "Arcanum Boots");
    add(T7Items.ARCANUM_HELMET.get(), "Arcanum Helmet");
    add(T7Items.ARCANUM_CHESTPLATE.get(), "Arcanum Chestplate");
    add(T7Items.ARCANUM_LEGGINGS.get(), "Arcanum Leggings");

    add(T7Items.IGNIS_TESTA.get(), "Ignis Testa");
    add(T7Items.AER_TESTA.get(), "Aer Testa");
    add(T7Items.TERRA_TESTA.get(), "Terra Testa");
    add(T7Items.AQUA_TESTA.get(), "Aqua Testa");
    add(T7Items.ORDO_TESTA.get(), "Ordo Testa");
    add(T7Items.PERDITIO_TESTA.get(), "Perditio Testa");

    add(T7Items.ARCANUM_SWORD.get(), "Arcanum Sword");
    add(T7Items.ARCANUM_AXE.get(), "Arcanum Axe");
    add(T7Items.ARCANUM_PICKAXE.get(), "Arcanum Pickaxe");
    add(T7Items.ARCANUM_HAMMER.get(), "Arcanum Hammer");
    add(T7Items.ARCANUM_SHOVEL.get(), "Arcanum Shovel");
    add(T7Items.ARCANUM_HOE.get(), "Arcanum Hoe");
    add(T7Items.ARCANUM_KATANA.get(), "Arcanum Katana");

    Map<WandHandleMaterial, String> handleNames = new HashMap<>();
    handleNames.put(WandHandleMaterials.IRON.get(), "Iron Handle");
    handleNames.put(WandHandleMaterials.GOLD.get(), "Gold Handle");
    handleNames.put(WandHandleMaterials.ORICHALCUM.get(), "Orichalcum Handle");
    handleNames.put(WandHandleMaterials.ARCANUM.get(), "Arcanum Handle");

    Map<WandCoreMaterial, String> coreNames = new HashMap<>();
    coreNames.put(WandCoreMaterials.WOOD.get(), "Wooden");
    coreNames.put(WandCoreMaterials.GREATWOOD.get(), "Greatwood");
    coreNames.put(WandCoreMaterials.SILVERWOOD.get(), "Silverwood");

    for (var handleEntry : handleNames.entrySet()) {
      for (var coreEntry : coreNames.entrySet()) {
        WandItem wand = T7Items.wandOrThrow(handleEntry.getKey(), coreEntry.getKey());
        add(wand, handleEntry.getValue() + " " + coreEntry.getValue() + " Wand");
      }
    }

    add(T7Blocks.AURA_NODE.get(), "Aura Node");
    add(T7Blocks.CRUCIBLE.get(), "Crucible");
    add(T7Blocks.ARCANE_WORKBENCH.get(), "Arcane Workbench");
    add(T7Blocks.IGNIS_INFUSED_STONE.get(), "Ignis Infused Stone");
    add(T7Blocks.AER_INFUSED_STONE.get(), "Aer Infused Stone");
    add(T7Blocks.TERRA_INFUSED_STONE.get(), "Terra Infused Stone");
    add(T7Blocks.AQUA_INFUSED_STONE.get(), "Aqua Infused Stone");
    add(T7Blocks.ORDO_INFUSED_STONE.get(), "Ordo Infused Stone");
    add(T7Blocks.PERDITIO_INFUSED_STONE.get(), "Perditio Infused Stone");
    add(T7Blocks.ARCANUM_BLOCK.get(), "Arcanum Block");
    add(T7Blocks.ORICHALCUM_BLOCK.get(), "Orichalcum Block");

    add(T7Blocks.GREATWOOD_LOG.get(), "Greatwood Log");
    add(T7Blocks.GREATWOOD_LEAVES.get(), "Greatwood Leaves");
    add(T7Blocks.GREATWOOD_PLANKS.get(), "Greatwood Planks");
    add(T7Blocks.GREATWOOD_SAPLING.get(), "Greatwood Sapling");
    add(T7Blocks.SILVERWOOD_LOG.get(), "Silverwood Log");
    add(T7Blocks.SILVERWOOD_LEAVES.get(), "Silverwood Leaves");
    add(T7Blocks.SILVERWOOD_PLANKS.get(), "Silverwood Planks");
    add(T7Blocks.SILVERWOOD_SAPLING.get(), "Silverwood Sapling");

    add(T7Blocks.ESSENTIA_CONTAINER.get(), "Essentia Container");

    add("container." + Thaumcraft.MODID + ".arcane_workbench", "Arcane Workbench");
  }
}
