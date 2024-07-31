package me.alegian.thaumcraft7.impl.common.event;

import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.data.providers.*;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class T7CommonEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.MOD)
  public static class CommonModEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
      T7Items.registerCapabilities(event);
      T7Blocks.registerCapabilities(event);
      T7BlockEntities.registerCapabilities(event);
    }

    @SubscribeEvent
    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
      event.register(T7DataMaps.ASPECT_CONTENT);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
      var generator = event.getGenerator();
      var lookupProvider = event.getLookupProvider();
      var existingFileHelper = event.getExistingFileHelper();
      var packOutput = generator.getPackOutput();

      generator.addProvider(true, new T7BlockStateProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new T7ItemModelProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new T7DataMapProvider(packOutput, lookupProvider));
      generator.addProvider(true, new T7ParticleDescriptionProvider(packOutput, existingFileHelper));
      generator.addProvider(true, new T7BlockTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(true, new T7FluidTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(true, new T7LanguageProvider(packOutput, "en_us"));
    }
  }
}