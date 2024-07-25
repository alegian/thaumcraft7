package me.alegian.thaumcraft7.impl.common.event;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.data.providers.*;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCBlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCBlocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class TCCommonEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.MOD)
  public static class CommonModEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      TCItems.registerCapabilities(event);
      TCBlocks.registerCapabilities(event);
      TCBlockEntities.registerCapabilities(event);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
      var generator = event.getGenerator();
      var lookupProvider = event.getLookupProvider();
      var existingFileHelper = event.getExistingFileHelper();
      var packOutput = generator.getPackOutput();

      generator.addProvider(true, new TCBlockStateProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new TCItemModelProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new TCParticleDescriptionProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new TCBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(true, new TCFluidTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(true, new TCLanguageProvider(packOutput, "en_us"));
    }
  }
}