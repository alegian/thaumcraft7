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
    add("thaumcraft", "Thaumcraft");

    add(T7Items.IRON_CAP.get(), "Iron Wand Cap");
    add(T7Items.IRON_WOOD_WAND.get(), "Iron Capped Wooden Wand");
    add(T7Items.THAUMOMETER.get(), "Thaumometer");
    add(T7Items.THAUMONOMICON.get(), "Thaumonomicon");
    add(T7Items.GOGGLES.get(), "Goggles Of Revealing");

    add(T7Blocks.AURA_NODE_BLOCK.get(), "Aura Node");
    add(T7Blocks.CRUCIBLE.get(), "Crucible");
  }
}
