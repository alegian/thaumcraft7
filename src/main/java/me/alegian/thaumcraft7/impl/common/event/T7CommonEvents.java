package me.alegian.thaumcraft7.impl.common.event;

import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.entity.EntityHelper;
import me.alegian.thaumcraft7.impl.init.data.providers.*;
import me.alegian.thaumcraft7.impl.init.registries.T7AttributeModifiers;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;

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
      event.register(T7DataMaps.AspectContent.BLOCK);
      event.register(T7DataMaps.AspectContent.ITEM);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
      var generator = event.getGenerator();
      var lookupProvider = event.getLookupProvider();
      var existingFileHelper = event.getExistingFileHelper();
      var packOutput = generator.getPackOutput();

      generator.addProvider(event.includeServer(), new T7DatapackBuiltinEntriesProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new T7DataMapProvider(packOutput, lookupProvider));
      generator.addProvider(event.includeServer(), new T7RecipeProvider(packOutput, lookupProvider));
      var blockTagProvider = generator.addProvider(true, new T7BlockTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new T7ItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
      generator.addProvider(event.includeServer(), new T7FluidTagProvider(packOutput, lookupProvider, existingFileHelper));
      generator.addProvider(event.includeServer(), new T7LootTableProvider(packOutput, List.of(
          new LootTableProvider.SubProviderEntry(
              T7BlockLootSubProvider::new,
              LootContextParamSets.BLOCK
          )
      ), lookupProvider));

      generator.addProvider(event.includeClient(), new T7BlockStateProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeClient(), new T7ItemModelProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeClient(), new T7ParticleDescriptionProvider(packOutput, existingFileHelper));
      generator.addProvider(event.includeClient(), new T7LanguageProvider(packOutput, "en_us"));
    }
  }

  @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.GAME)
  public static class CommonGameEvents {
    @SubscribeEvent
    public static void entityTickPre(EntityTickEvent.Pre event) {
      if (event.getEntity() instanceof LivingEntity livingEntity) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
        if (attribute == null) return;

        if (!EntityHelper.isEntityWearingBoots(livingEntity))
          attribute.removeModifier(T7AttributeModifiers.STEP_HEIGHT);
        else attribute.addOrUpdateTransientModifier(T7AttributeModifiers.STEP_HEIGHT);
      }
    }
  }
}