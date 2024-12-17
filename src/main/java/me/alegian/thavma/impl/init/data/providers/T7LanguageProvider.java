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

    this.add(T7Items.INSTANCE.getIRON_HANDLE().get(), "Iron Wand Handle");
    this.add(T7Items.INSTANCE.getGOLD_HANDLE().get(), "Gold Wand Handle");
    this.add(T7Items.INSTANCE.getORICHALCUM_HANDLE().get(), "Orichalcum Wand Handle");
    this.add(T7Items.INSTANCE.getARCANUM_HANDLE().get(), "Arcanum Wand Handle");

    this.add(T7Items.INSTANCE.getEYE_OF_WARDEN().get(), "Eye of Warden");

    this.add(T7Items.INSTANCE.getGREATWOOD_CORE().get(), "Greatwood Wand Core");
    this.add(T7Items.INSTANCE.getSILVERWOOD_CORE().get(), "Silverwood Wand Core");

    this.add(T7Items.INSTANCE.getRUNE().get(), "Rune");
    this.add(T7Items.INSTANCE.getARCANUM_INGOT().get(), "Arcanum Ingot");
    this.add(T7Items.INSTANCE.getARCANUM_NUGGET().get(), "Arcanum Nugget");
    this.add(T7Items.INSTANCE.getORICHALCUM_INGOT().get(), "Orichalcum Ingot");
    this.add(T7Items.INSTANCE.getORICHALCUM_NUGGET().get(), "Orichalcum Nugget");
    this.add(T7Items.INSTANCE.getRESEARCH_SCROLL().get(), "Research Scroll");
    this.add(T7Items.INSTANCE.getCOMPLETED_RESEARCH().get(), "Completed Research");
    this.add(T7Items.INSTANCE.getOCULUS().get(), "Oculus");
    this.add(T7Items.INSTANCE.getTHAUMONOMICON().get(), "Thaumonomicon");

    this.add(T7Items.INSTANCE.getGOGGLES().get(), "Goggles Of Revealing");
    this.add(T7Items.INSTANCE.getGOGGLES_ACCESSORY().get(), "Goggles Of Revealing (Accessory)");
    this.add(T7Items.INSTANCE.getDAWN_CHARM().get(), "Charm of the Dawn");
    this.add(T7Items.INSTANCE.getRESEARCHER_BOOTS().get(), "Researcher Boots");
    this.add(T7Items.INSTANCE.getRESEARCHER_CHESTPLATE().get(), "Researcher Chestplate");
    this.add(T7Items.INSTANCE.getRESEARCHER_LEGGINGS().get(), "Researcher Leggings");

    this.add(T7Items.INSTANCE.getARCANUM_BOOTS().get(), "Arcanum Boots");
    this.add(T7Items.INSTANCE.getARCANUM_HELMET().get(), "Arcanum Helmet");
    this.add(T7Items.INSTANCE.getARCANUM_CHESTPLATE().get(), "Arcanum Chestplate");
    this.add(T7Items.INSTANCE.getARCANUM_LEGGINGS().get(), "Arcanum Leggings");

    this.add(T7Items.INSTANCE.getCUSTOS_ARCANUM_BOOTS().get(), "Custos Arcanum Boots");
    this.add(T7Items.INSTANCE.getCUSTOS_ARCANUM_HELMET().get(), "Custos Arcanum Helmet");
    this.add(T7Items.INSTANCE.getCUSTOS_ARCANUM_CHESTPLATE().get(), "Custos Arcanum Chestplate");
    this.add(T7Items.INSTANCE.getCUSTOS_ARCANUM_LEGGINGS().get(), "Custos Arcanum Leggings");

    this.add(T7Items.INSTANCE.getIGNIS_TESTA().get(), "Ignis Testa");
    this.add(T7Items.INSTANCE.getAER_TESTA().get(), "Aer Testa");
    this.add(T7Items.INSTANCE.getTERRA_TESTA().get(), "Terra Testa");
    this.add(T7Items.INSTANCE.getAQUA_TESTA().get(), "Aqua Testa");
    this.add(T7Items.INSTANCE.getORDO_TESTA().get(), "Ordo Testa");
    this.add(T7Items.INSTANCE.getPERDITIO_TESTA().get(), "Perditio Testa");

    this.add(T7Items.INSTANCE.getARCANUM_SWORD().get(), "Arcanum Sword");
    this.add(T7Items.INSTANCE.getARCANUM_AXE().get(), "Arcanum Axe");
    this.add(T7Items.INSTANCE.getARCANUM_PICKAXE().get(), "Arcanum Pickaxe");
    this.add(T7Items.INSTANCE.getARCANUM_HAMMER().get(), "Arcanum Hammer");
    this.add(T7Items.INSTANCE.getARCANUM_SHOVEL().get(), "Arcanum Shovel");
    this.add(T7Items.INSTANCE.getARCANUM_HOE().get(), "Arcanum Hoe");
    this.add(T7Items.INSTANCE.getARCANUM_KATANA().get(), "Arcanum Katana");
    this.add(T7Items.INSTANCE.getZEPHYR().get(), "Zephyr");

    Map<WandHandleMaterial, String> handleNames = new HashMap<>();
    handleNames.put(WandHandleMaterials.INSTANCE.getIRON().get(), "Iron Handle");
    handleNames.put(WandHandleMaterials.INSTANCE.getGOLD().get(), "Gold Handle");
    handleNames.put(WandHandleMaterials.INSTANCE.getORICHALCUM().get(), "Orichalcum Handle");
    handleNames.put(WandHandleMaterials.INSTANCE.getARCANUM().get(), "Arcanum Handle");

    Map<WandCoreMaterial, String> coreNames = new HashMap<>();
    coreNames.put(WandCoreMaterials.INSTANCE.getWOOD().get(), "Wooden");
    coreNames.put(WandCoreMaterials.INSTANCE.getGREATWOOD().get(), "Greatwood");
    coreNames.put(WandCoreMaterials.INSTANCE.getSILVERWOOD().get(), "Silverwood");

    for (var handleEntry : handleNames.entrySet())
      for (var coreEntry : coreNames.entrySet()) {
        WandItem wand = T7Items.INSTANCE.wandOrThrow(handleEntry.getKey(), coreEntry.getKey());
        this.add(wand, handleEntry.getValue() + " " + coreEntry.getValue() + " Wand");
      }

    this.add(T7Blocks.INSTANCE.getAURA_NODE().get(), "Aura Node");
    this.add(T7Blocks.INSTANCE.getCRUCIBLE().get(), "Crucible");
    this.add(T7Blocks.INSTANCE.getARCANE_WORKBENCH().get(), "Arcane Workbench");
    this.add(T7Blocks.INSTANCE.getMATRIX().get(), "Infusion Matrix");
    this.add(T7Blocks.INSTANCE.getPILLAR().get(), "Infusion Pillar");
    this.add(T7Blocks.INSTANCE.getPEDESTAL().get(), "Infusion Pedestal");
    this.add(T7Blocks.INSTANCE.getRESEARCH_TABLE().get(), "Research Table");
    this.add(T7Blocks.INSTANCE.getELEMENTAL_STONE().get(), "Elemental Stone");

    this.add(T7Blocks.INSTANCE.getIGNIS_INFUSED_STONE().get(), "Ignis Infused Stone");
    this.add(T7Blocks.INSTANCE.getAER_INFUSED_STONE().get(), "Aer Infused Stone");
    this.add(T7Blocks.INSTANCE.getTERRA_INFUSED_STONE().get(), "Terra Infused Stone");
    this.add(T7Blocks.INSTANCE.getAQUA_INFUSED_STONE().get(), "Aqua Infused Stone");
    this.add(T7Blocks.INSTANCE.getORDO_INFUSED_STONE().get(), "Ordo Infused Stone");
    this.add(T7Blocks.INSTANCE.getPERDITIO_INFUSED_STONE().get(), "Perditio Infused Stone");
    this.add(T7Blocks.INSTANCE.getIGNIS_INFUSED_DEEPSLATE().get(), "Ignis Infused Deepslate");
    this.add(T7Blocks.INSTANCE.getAER_INFUSED_DEEPSLATE().get(), "Aer Infused Deepslate");
    this.add(T7Blocks.INSTANCE.getTERRA_INFUSED_DEEPSLATE().get(), "Terra Infused Deepslate");
    this.add(T7Blocks.INSTANCE.getAQUA_INFUSED_DEEPSLATE().get(), "Aqua Infused Deepslate");
    this.add(T7Blocks.INSTANCE.getORDO_INFUSED_DEEPSLATE().get(), "Ordo Infused Deepslate");
    this.add(T7Blocks.INSTANCE.getPERDITIO_INFUSED_DEEPSLATE().get(), "Perditio Infused Deepslate");

    this.add(T7Blocks.INSTANCE.getARCANUM_BLOCK().get(), "Arcanum Block");
    this.add(T7Blocks.INSTANCE.getORICHALCUM_BLOCK().get(), "Orichalcum Block");

    this.add(T7Blocks.INSTANCE.getGREATWOOD_LOG().get(), "Greatwood Log");
    this.add(T7Blocks.INSTANCE.getGREATWOOD_LEAVES().get(), "Greatwood Leaves");
    this.add(T7Blocks.INSTANCE.getGREATWOOD_PLANKS().get(), "Greatwood Planks");
    this.add(T7Blocks.INSTANCE.getGREATWOOD_SAPLING().get(), "Greatwood Sapling");
    this.add(T7Blocks.INSTANCE.getSILVERWOOD_LOG().get(), "Silverwood Log");
    this.add(T7Blocks.INSTANCE.getSILVERWOOD_LEAVES().get(), "Silverwood Leaves");
    this.add(T7Blocks.INSTANCE.getSILVERWOOD_PLANKS().get(), "Silverwood Planks");
    this.add(T7Blocks.INSTANCE.getSILVERWOOD_SAPLING().get(), "Silverwood Sapling");

    this.add(T7Blocks.INSTANCE.getESSENTIA_CONTAINER().get(), "Essentia Container");

    this.add("container." + Thavma.MODID + ".arcane_workbench", "Arcane Workbench");

    this.add(T7Attributes.INSTANCE.getREVEALING(), "Revealing");
  }

  private void add(DeferredHolder<Attribute, Attribute> attributeHolder, String name) {
    this.add(Util.makeDescriptionId(Registries.ATTRIBUTE.location().getPath(), attributeHolder.getId()), name);
  }
}
