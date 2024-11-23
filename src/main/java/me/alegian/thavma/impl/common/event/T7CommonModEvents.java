package me.alegian.thavma.impl.common.event;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.init.data.providers.*;
import me.alegian.thavma.impl.init.registries.T7DataMaps;
import me.alegian.thavma.impl.init.registries.T7Registries;
import me.alegian.thavma.impl.init.registries.deferred.T7Attributes;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import me.alegian.thavma.impl.init.registries.deferred.callback.WandCoreCombinations;
import me.alegian.thavma.impl.init.registries.deferred.callback.WandHandleCombinations;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;

@EventBusSubscriber(modid = Thavma.MODID, bus = EventBusSubscriber.Bus.MOD)
public class T7CommonModEvents {
  @SubscribeEvent
  static void registerRegistries(NewRegistryEvent event) {
    event.register(T7Registries.WAND_HANDLE);
    event.register(T7Registries.WAND_CORE);
    event.register(T7Registries.ASPECT);
    event.register(T7Registries.RESEARCH);
  }

  @SubscribeEvent
  static void modifyRegistries(ModifyRegistriesEvent event) {
    var itemRegistry = event.getRegistry(Registries.ITEM);
    var coreRegistry = event.getRegistry(T7Registries.WAND_CORE.key());
    var handleRegistry = event.getRegistry(T7Registries.WAND_HANDLE.key());

    coreRegistry.addCallback(new WandCoreCombinations(itemRegistry, handleRegistry));
    handleRegistry.addCallback(new WandHandleCombinations(itemRegistry, coreRegistry));
  }

  @SubscribeEvent
  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    T7Items.registerCapabilities(event);
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
    var blockTagProvider = generator.addProvider(event.includeServer(), new T7BlockTagProvider(packOutput, lookupProvider, existingFileHelper));
    generator.addProvider(event.includeServer(), new T7ItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
    generator.addProvider(event.includeServer(), new T7DamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
    generator.addProvider(event.includeServer(), new T7FluidTagProvider(packOutput, lookupProvider, existingFileHelper));
    generator.addProvider(event.includeServer(), new T7GlobalLootModifierProvider(packOutput, lookupProvider));
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

  @SubscribeEvent
  public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
    event.modify(T7Items.ARCANUM_HAMMER, builder -> builder.set(DataComponents.MAX_DAMAGE, T7Items.ARCANUM_HAMMER.get().getTier().getUses() * 2));
  }

  @SubscribeEvent
  public static void entityAttributeModification(EntityAttributeModificationEvent event) {
    if (!event.has(EntityType.PLAYER, T7Attributes.REVEALING))
      event.add(EntityType.PLAYER, T7Attributes.REVEALING);
  }
}
