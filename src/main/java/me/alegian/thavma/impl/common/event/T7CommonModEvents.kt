package me.alegian.thavma.impl.common.event

import me.alegian.thavma.impl.init.data.providers.*
import me.alegian.thavma.impl.init.registries.T7DataMaps
import me.alegian.thavma.impl.init.registries.T7Registries
import me.alegian.thavma.impl.init.registries.deferred.T7Attributes
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities
import me.alegian.thavma.impl.init.registries.deferred.T7Items
import me.alegian.thavma.impl.init.registries.deferred.callback.WandCoreCombinations
import me.alegian.thavma.impl.init.registries.deferred.callback.WandHandleCombinations
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.data.event.GatherDataEvent
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent
import net.neoforged.neoforge.registries.ModifyRegistriesEvent
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS as KFF_MOD_BUS

private fun registerRegistries(event: NewRegistryEvent) {
    event.register(T7Registries.WAND_HANDLE)
    event.register(T7Registries.WAND_CORE)
    event.register(T7Registries.ASPECT)
    event.register(T7Registries.RESEARCH)
}

private fun modifyRegistries(event: ModifyRegistriesEvent) {
    val itemRegistry = event.getRegistry(Registries.ITEM)
    val coreRegistry = event.getRegistry(T7Registries.WAND_CORE.key())
    val handleRegistry = event.getRegistry(T7Registries.WAND_HANDLE.key())

    coreRegistry.addCallback(WandCoreCombinations(itemRegistry, handleRegistry))
    handleRegistry.addCallback(WandHandleCombinations(itemRegistry, coreRegistry))
}

private fun registerCapabilities(event: RegisterCapabilitiesEvent) {
    T7Items.registerCapabilities(event)
    T7BlockEntities.registerCapabilities(event)
}

private fun registerDataMapTypes(event: RegisterDataMapTypesEvent) {
    event.register(T7DataMaps.AspectContent.BLOCK)
    event.register(T7DataMaps.AspectContent.ITEM)
}

private fun gatherData(event: GatherDataEvent) {
    val generator = event.generator
    val lookupProvider = event.lookupProvider
    val existingFileHelper = event.existingFileHelper
    val packOutput = generator.packOutput

    generator.addProvider(event.includeServer(), T7DatapackBuiltinEntriesProvider(packOutput, lookupProvider))
    generator.addProvider(event.includeServer(), T7DataMapProvider(packOutput, lookupProvider))
    generator.addProvider(event.includeServer(), T7RecipeProvider(packOutput, lookupProvider))
    val blockTagProvider = generator.addProvider(
        event.includeServer(),
        T7BlockTagProvider(packOutput, lookupProvider, existingFileHelper)
    )
    generator.addProvider(
        event.includeServer(),
        T7ItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
    )
    generator.addProvider(
        event.includeServer(),
        T7DamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper)
    )
    generator.addProvider(event.includeServer(), T7FluidTagProvider(packOutput, lookupProvider, existingFileHelper))
    generator.addProvider(event.includeServer(), T7GlobalLootModifierProvider(packOutput, lookupProvider))
    generator.addProvider(
        event.includeServer(), T7LootTableProvider(
            packOutput, listOf(
                SubProviderEntry(
                    {
                        T7BlockLootSubProvider(it)
                    },
                    LootContextParamSets.BLOCK
                )
            ), lookupProvider
        )
    )

    generator.addProvider(event.includeClient(), T7BlockStateProvider(packOutput, existingFileHelper))
    generator.addProvider(event.includeClient(), T7ItemModelProvider(packOutput, existingFileHelper))
    generator.addProvider(event.includeClient(), T7ParticleDescriptionProvider(packOutput, existingFileHelper))
    generator.addProvider(event.includeClient(), T7LanguageProvider(packOutput, "en_us"))
}

private fun modifyDefaultComponents(event: ModifyDefaultComponentsEvent) {
    event.modify(
        T7Items.ARCANUM_HAMMER
    ) {
        it.set(
            DataComponents.MAX_DAMAGE,
            T7Items.ARCANUM_HAMMER.get().tier.uses * 2
        )
    }
}

private fun entityAttributeModification(event: EntityAttributeModificationEvent) {
    if (!event.has(EntityType.PLAYER, T7Attributes.REVEALING))
        event.add(EntityType.PLAYER, T7Attributes.REVEALING)
}

fun registerCommonModEvents() {
    KFF_MOD_BUS.addListener(::registerRegistries)
    KFF_MOD_BUS.addListener(::modifyRegistries)
    KFF_MOD_BUS.addListener(::registerCapabilities)
    KFF_MOD_BUS.addListener(::registerDataMapTypes)
    KFF_MOD_BUS.addListener(::gatherData)
    KFF_MOD_BUS.addListener(::modifyDefaultComponents)
    KFF_MOD_BUS.addListener(::entityAttributeModification)
}