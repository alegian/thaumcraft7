package me.alegian.thaumcraft7.data.gen;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import me.alegian.thaumcraft7.item.TCItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class TCLanguageProvider extends LanguageProvider {
  public TCLanguageProvider(PackOutput output, String locale) {
    super(output, Thaumcraft.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    add("thaumcraft", "Thaumcraft");

    add(TCItems.IRON_CAP.get(), "Iron Wand Cap");
    add(TCItems.IRON_WOOD_WAND.get(), "Iron Capped Wooden Wand");
    add(TCItems.THAUMOMETER.get(), "Thaumometer");
    add(TCItems.THAUMONOMICON.get(), "Thaumonomicon");
    add(TCItems.GOGGLES.get(), "Goggles Of Revealing");

    add(TCBlocks.AURA_NODE_BLOCK.get(), "Aura Node");
    add(TCBlocks.CRUCIBLE.get(), "Crucible");
  }
}
