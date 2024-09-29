package me.alegian.thaumcraft7.impl.common.event;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.entity.EntityHelper;
import me.alegian.thaumcraft7.impl.common.item.HammerItem;
import me.alegian.thaumcraft7.impl.init.data.providers.*;
import me.alegian.thaumcraft7.impl.init.registries.T7AttributeModifiers;
import me.alegian.thaumcraft7.impl.init.registries.T7DataMaps;
import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;

public class T7CommonEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.MOD)
  public static class CommonModEvents {
    @SubscribeEvent
    static void registerRegistries(NewRegistryEvent event) {
      event.register(T7Registries.WAND_HANDLE);
      event.register(T7Registries.WAND_CORE);
      event.register(T7Registries.ASPECT);
    }

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

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
      event.modify(T7Items.ARCANUM_HAMMER, builder -> builder.set(DataComponents.MAX_DAMAGE, T7Items.ARCANUM_HAMMER.get().getTier().getUses() * 2));
    }
  }

  @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.GAME)
  public static class CommonGameEvents {
    private static boolean allowHammerBreakEvents = true;

    @SubscribeEvent
    public static void entityTickPre(EntityTickEvent.Pre event) {
      if (event.getEntity() instanceof LivingEntity livingEntity) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
        if (attribute == null) return;

        boolean hasStepHeightFromOtherModifier = attribute.getValue() >= 1.0 && !attribute.hasModifier(T7AttributeModifiers.StepHeight.LOCATION);

        if (!EntityHelper.isEntityWearingBoots(livingEntity) || hasStepHeightFromOtherModifier || livingEntity.isCrouching())
          attribute.removeModifier(T7AttributeModifiers.StepHeight.MODIFIER);
        else attribute.addOrUpdateTransientModifier(T7AttributeModifiers.StepHeight.MODIFIER);
      }
    }

    @SubscribeEvent
    public static void livingDamagePost(LivingDamageEvent.Post event) {
      var itemStack = event.getSource().getWeaponItem();
      if (itemStack == null) return;
      if (!itemStack.getItem().equals(T7Items.ARCANUM_KATANA.get())) return;

      var entity = event.getEntity();
      if (entity.getHealth() <= 10) {
        entity.kill();
        if (event.getSource().getEntity() instanceof ServerPlayer player)
          entity.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 1.0F, 1.0F);
      }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
      var player = event.getPlayer();
      var itemStack = player.getMainHandItem();
      var item = itemStack.getItem();
      var level = event.getLevel();

      if (player instanceof ServerPlayer serverPlayer && item instanceof HammerItem hammer) {
        // disallow nested hammer break events, to avoid infinite recursion
        if (!allowHammerBreakEvents) return;
        allowHammerBreakEvents = false;

        hammer.tryBreak3x3exceptOrigin(serverPlayer, level, itemStack);

        allowHammerBreakEvents = true;
      }
    }
  }
}