package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.enchantment.ShriekResistance;
import me.alegian.thavma.impl.init.data.worldgen.ore.*;
import me.alegian.thavma.impl.init.data.worldgen.tree.GreatwoodTree;
import me.alegian.thavma.impl.init.data.worldgen.tree.SilverwoodTree;
import me.alegian.thavma.impl.init.registries.T7Tags;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.TagPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class T7DatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
  private static final RegistrySetBuilder builder = new RegistrySetBuilder()
      .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
        GreatwoodTree.registerConfigured(bootstrap);
        SilverwoodTree.registerConfigured(bootstrap);

        IgnisOre.registerConfigured(bootstrap);
        TerraOre.registerConfigured(bootstrap);
        AerOre.registerConfigured(bootstrap);
        AquaOre.registerConfigured(bootstrap);
        OrdoOre.registerConfigured(bootstrap);
        PerditioOre.registerConfigured(bootstrap);

        InfusedStoneOre.registerConfigured(bootstrap);
      })
      .add(Registries.PLACED_FEATURE, bootstrap -> {
        GreatwoodTree.registerPlaced(bootstrap);
        SilverwoodTree.registerPlaced(bootstrap);

        IgnisOre.registerPlaced(bootstrap);
        TerraOre.registerPlaced(bootstrap);
        AerOre.registerPlaced(bootstrap);
        AquaOre.registerPlaced(bootstrap);
        OrdoOre.registerPlaced(bootstrap);
        PerditioOre.registerPlaced(bootstrap);

        InfusedStoneOre.registerPlaced(bootstrap);
      })
      .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, bootstrap -> {
        GreatwoodTree.registerBiomeModifier(bootstrap);
        SilverwoodTree.registerBiomeModifier(bootstrap);
        InfusedStoneOre.registerBiomeModifier(bootstrap);
      })
      .add(Registries.ENCHANTMENT, bootstrap -> {
        var itemRegistry = bootstrap.lookup(Registries.ITEM);
        bootstrap.register(
            ResourceKey.create(
                Registries.ENCHANTMENT,
                ShriekResistance.INSTANCE.getLOCATION()
            ),
            new Enchantment(
                Component.literal("Shriek Resistance"),
                new Enchantment.EnchantmentDefinition(
                    itemRegistry.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                    Optional.empty(),
                    5,
                    4,
                    new Enchantment.Cost(10, 8),
                    new Enchantment.Cost(18, 8),
                    2,
                    List.of(EquipmentSlotGroup.ARMOR)
                ),
                HolderSet.empty(),
                DataComponentMap.builder()
                    .set(EnchantmentEffectComponents.DAMAGE_PROTECTION, List.of(
                        new ConditionalEffect<>(
                            new AddValue(LevelBasedValue.perLevel(2.0F)),
                            Optional.of(DamageSourceCondition.hasDamageSource(
                                DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(T7Tags.INSTANCE.getSONIC()))
                            ).build())
                        ))
                    ).build()
            )
        );
      });

  public T7DatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, T7DatapackBuiltinEntriesProvider.builder, Set.of(Thavma.MODID));
  }
}
