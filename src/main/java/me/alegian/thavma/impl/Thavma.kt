package me.alegian.thavma.impl

import com.mojang.logging.LogUtils
import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.client.event.registerClientGameEvents
import me.alegian.thavma.impl.client.event.registerClientModEvents
import me.alegian.thavma.impl.common.event.registerCommonGameEvents
import me.alegian.thavma.impl.common.event.registerCommonModEvents
import me.alegian.thavma.impl.init.registries.deferred.*
import net.minecraft.resources.ResourceLocation
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import org.slf4j.Logger

// must match value in META-INF/mods.toml
@Mod(Thavma.MODID)
class Thavma(modEventBus: IEventBus) {
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    init {
        T7ArmorMaterials.REGISTRAR.register(modEventBus)
        T7Blocks.REGISTRAR.register(modEventBus)
        T7Items.REGISTRAR.register(modEventBus)
        T7BlockEntities.REGISTRAR.register(modEventBus)
        T7EntityTypes.REGISTRAR.register(modEventBus)
        T7Attachments.REGISTRAR.register(modEventBus)
        T7DataComponents.REGISTRAR.register(modEventBus)
        T7ParticleTypes.REGISTRAR.register(modEventBus)
        T7CreativeModeTabs.REGISTRAR.register(modEventBus)
        T7TrunkPlacerTypes.REGISTRAR.register(modEventBus)
        T7MenuTypes.REGISTRAR.register(modEventBus)
        T7RecipeTypes.REGISTRAR.register(modEventBus)
        T7RecipeSerializers.REGISTRAR.register(modEventBus)
        WandCoreMaterials.REGISTRAR.register(modEventBus)
        WandHandleMaterials.REGISTRAR.register(modEventBus)
        Aspects.REGISTRAR.register(modEventBus)
        Researches.REGISTRAR.register(modEventBus)
        T7Attributes.REGISTRAR.register(modEventBus)
        T7GlobalLootModifierSerializers.REGISTRAR.register(modEventBus)

        registerCommonModEvents()
        registerClientModEvents()
        registerCommonGameEvents()
        registerClientGameEvents()
    }

    companion object {
        const val MODID: String = "thavma"
        val LOGGER: Logger = LogUtils.getLogger()

        fun rl(path: String): ResourceLocation {
            return ResourceLocation.fromNamespaceAndPath(MODID, path)
        }
    }
}
