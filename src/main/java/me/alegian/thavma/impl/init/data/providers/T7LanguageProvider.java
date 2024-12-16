package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.item.WandItem;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import me.alegian.thavma.impl.init.registries.deferred.*;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;
import java.util.Map;

public class T7LanguageProvider extends LanguageProvider {
  public T7LanguageProvider(PackOutput output, String locale) {
    super(output, Thavma.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    this.add(Thavma.MODID, "Thavma");

    this.add(T7Items.IRON_HANDLE.get(), "Iron Wand Handle");
    this.add(T7Items.GOLD_HANDLE.get(), "Gold Wand Handle");
    this.add(T7Items.ORICHALCUM_HANDLE.get(), "Orichalcum Wand Handle");
    this.add(T7Items.ARCANUM_HANDLE.get(), "Arcanum Wand Handle");

    this.add(T7Items.EYE_OF_WARDEN.get(), "Eye of Warden");

    this.add(T7Items.GREATWOOD_CORE.get(), "Greatwood Wand Core");
    this.add(T7Items.SILVERWOOD_CORE.get(), "Silverwood Wand Core");

    this.add(T7Items.RUNE.get(), "Rune");
    this.add(T7Items.ARCANUM_INGOT.get(), "Arcanum Ingot");
    this.add(T7Items.ARCANUM_NUGGET.get(), "Arcanum Nugget");
    this.add(T7Items.ORICHALCUM_INGOT.get(), "Orichalcum Ingot");
    this.add(T7Items.ORICHALCUM_NUGGET.get(), "Orichalcum Nugget");
    this.add(T7Items.RESEARCH_SCROLL.get(), "Research Scroll");
    this.add(T7Items.COMPLETED_RESEARCH.get(), "Completed Research");
    this.add(T7Items.OCULUS.get(), "Oculus");
    this.add(T7Items.THAUMONOMICON.get(), "Thaumonomicon");

    this.add(T7Items.GOGGLES.get(), "Goggles Of Revealing");
    this.add(T7Items.GOGGLES_ACCESSORY.get(), "Goggles Of Revealing (Accessory)");
    this.add(T7Items.DAWN_CHARM.get(), "Charm of the Dawn");
    this.add(T7Items.RESEARCHER_BOOTS.get(), "Researcher Boots");
    this.add(T7Items.RESEARCHER_CHESTPLATE.get(), "Researcher Chestplate");
    this.add(T7Items.RESEARCHER_LEGGINGS.get(), "Researcher Leggings");

    this.add(T7Items.ARCANUM_BOOTS.get(), "Arcanum Boots");
    this.add(T7Items.ARCANUM_HELMET.get(), "Arcanum Helmet");
    this.add(T7Items.ARCANUM_CHESTPLATE.get(), "Arcanum Chestplate");
    this.add(T7Items.ARCANUM_LEGGINGS.get(), "Arcanum Leggings");

    this.add(T7Items.CUSTOS_ARCANUM_BOOTS.get(), "Custos Arcanum Boots");
    this.add(T7Items.CUSTOS_ARCANUM_HELMET.get(), "Custos Arcanum Helmet");
    this.add(T7Items.CUSTOS_ARCANUM_CHESTPLATE.get(), "Custos Arcanum Chestplate");
    this.add(T7Items.CUSTOS_ARCANUM_LEGGINGS.get(), "Custos Arcanum Leggings");

    this.add(T7Items.IGNIS_TESTA.get(), "Ignis Testa");
    this.add(T7Items.AER_TESTA.get(), "Aer Testa");
    this.add(T7Items.TERRA_TESTA.get(), "Terra Testa");
    this.add(T7Items.AQUA_TESTA.get(), "Aqua Testa");
    this.add(T7Items.ORDO_TESTA.get(), "Ordo Testa");
    this.add(T7Items.PERDITIO_TESTA.get(), "Perditio Testa");

    this.add(T7Items.ARCANUM_SWORD.get(), "Arcanum Sword");
    this.add(T7Items.ARCANUM_AXE.get(), "Arcanum Axe");
    this.add(T7Items.ARCANUM_PICKAXE.get(), "Arcanum Pickaxe");
    this.add(T7Items.ARCANUM_HAMMER.get(), "Arcanum Hammer");
    this.add(T7Items.ARCANUM_SHOVEL.get(), "Arcanum Shovel");
    this.add(T7Items.ARCANUM_HOE.get(), "Arcanum Hoe");
    this.add(T7Items.ARCANUM_KATANA.get(), "Arcanum Katana");
    this.add(T7Items.ZEPHYR.get(), "Zephyr");

    Map<WandHandleMaterial, String> handleNames = new HashMap<>();
    handleNames.put(WandHandleMaterials.IRON.get(), "Iron Handle");
    handleNames.put(WandHandleMaterials.GOLD.get(), "Gold Handle");
    handleNames.put(WandHandleMaterials.ORICHALCUM.get(), "Orichalcum Handle");
    handleNames.put(WandHandleMaterials.ARCANUM.get(), "Arcanum Handle");

    Map<WandCoreMaterial, String> coreNames = new HashMap<>();
    coreNames.put(WandCoreMaterials.WOOD.get(), "Wooden");
    coreNames.put(WandCoreMaterials.GREATWOOD.get(), "Greatwood");
    coreNames.put(WandCoreMaterials.SILVERWOOD.get(), "Silverwood");

    for (var handleEntry : handleNames.entrySet())
      for (var coreEntry : coreNames.entrySet()) {
        WandItem wand = T7Items.wandOrThrow(handleEntry.getKey(), coreEntry.getKey());
        this.add(wand, handleEntry.getValue() + " " + coreEntry.getValue() + " Wand");
      }

    this.add(T7Blocks.AURA_NODE.get(), "Aura Node");
    this.add(T7Blocks.CRUCIBLE.get(), "Crucible");
    this.add(T7Blocks.ARCANE_WORKBENCH.get(), "Arcane Workbench");
    this.add(T7Blocks.MATRIX.get(), "Infusion Matrix");
    this.add(T7Blocks.PILLAR.get(), "Infusion Pillar");
    this.add(T7Blocks.PEDESTAL.get(), "Infusion Pedestal");
    this.add(T7Blocks.RESEARCH_TABLE.get(), "Research Table");
    this.add(T7Blocks.ELEMENTAL_STONE.get(), "Elemental Stone");

    this.add(T7Blocks.IGNIS_INFUSED_STONE.get(), "Ignis Infused Stone");
    this.add(T7Blocks.AER_INFUSED_STONE.get(), "Aer Infused Stone");
    this.add(T7Blocks.TERRA_INFUSED_STONE.get(), "Terra Infused Stone");
    this.add(T7Blocks.AQUA_INFUSED_STONE.get(), "Aqua Infused Stone");
    this.add(T7Blocks.ORDO_INFUSED_STONE.get(), "Ordo Infused Stone");
    this.add(T7Blocks.PERDITIO_INFUSED_STONE.get(), "Perditio Infused Stone");
    this.add(T7Blocks.IGNIS_INFUSED_DEEPSLATE.get(), "Ignis Infused Deepslate");
    this.add(T7Blocks.AER_INFUSED_DEEPSLATE.get(), "Aer Infused Deepslate");
    this.add(T7Blocks.TERRA_INFUSED_DEEPSLATE.get(), "Terra Infused Deepslate");
    this.add(T7Blocks.AQUA_INFUSED_DEEPSLATE.get(), "Aqua Infused Deepslate");
    this.add(T7Blocks.ORDO_INFUSED_DEEPSLATE.get(), "Ordo Infused Deepslate");
    this.add(T7Blocks.PERDITIO_INFUSED_DEEPSLATE.get(), "Perditio Infused Deepslate");

    this.add(T7Blocks.ARCANUM_BLOCK.get(), "Arcanum Block");
    this.add(T7Blocks.ORICHALCUM_BLOCK.get(), "Orichalcum Block");

    this.add(T7Blocks.GREATWOOD_LOG.get(), "Greatwood Log");
    this.add(T7Blocks.GREATWOOD_LEAVES.get(), "Greatwood Leaves");
    this.add(T7Blocks.GREATWOOD_PLANKS.get(), "Greatwood Planks");
    this.add(T7Blocks.GREATWOOD_SAPLING.get(), "Greatwood Sapling");
    this.add(T7Blocks.SILVERWOOD_LOG.get(), "Silverwood Log");
    this.add(T7Blocks.SILVERWOOD_LEAVES.get(), "Silverwood Leaves");
    this.add(T7Blocks.SILVERWOOD_PLANKS.get(), "Silverwood Planks");
    this.add(T7Blocks.SILVERWOOD_SAPLING.get(), "Silverwood Sapling");

    this.add(T7Blocks.ESSENTIA_CONTAINER.get(), "Essentia Container");

    this.add("container." + Thavma.MODID + ".arcane_workbench", "Arcane Workbench");

    this.add(T7Attributes.INSTANCE.getREVEALING(), "Revealing");
  }

  private void add(DeferredHolder<Attribute, Attribute> attributeHolder, String name) {
    this.add(Util.makeDescriptionId(Registries.ATTRIBUTE.location().getPath(), attributeHolder.getId()), name);
  }
}
