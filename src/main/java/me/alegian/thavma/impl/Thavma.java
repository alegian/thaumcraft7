package me.alegian.thavma.impl;

import com.mojang.logging.LogUtils;
import me.alegian.thavma.impl.client.event.T7ClientGameEventsKt;
import me.alegian.thavma.impl.client.event.T7ClientModEventsKt;
import me.alegian.thavma.impl.common.event.T7CommonGameEventsKt;
import me.alegian.thavma.impl.common.event.T7CommonModEventsKt;
import me.alegian.thavma.impl.init.registries.deferred.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// must match value in META-INF/mods.toml
@Mod(Thavma.MODID)
public class Thavma {
  public static final String MODID = "thavma";
  public static final Logger LOGGER = LogUtils.getLogger();

  // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
  public Thavma(IEventBus modEventBus) {
    T7ArmorMaterials.INSTANCE.getREGISTRAR().register(modEventBus);
    T7Blocks.REGISTRAR.register(modEventBus);
    T7Items.REGISTRAR.register(modEventBus);
    T7BlockEntities.REGISTRAR.register(modEventBus);
    T7EntityTypes.REGISTRAR.register(modEventBus);
    T7Attachments.REGISTRAR.register(modEventBus);
    T7DataComponents.REGISTRAR.register(modEventBus);
    T7ParticleTypes.REGISTRAR.register(modEventBus);
    T7CreativeModeTabs.REGISTRAR.register(modEventBus);
    T7TrunkPlacerTypes.REGISTRAR.register(modEventBus);
    T7MenuTypes.REGISTRAR.register(modEventBus);
    T7RecipeTypes.REGISTRAR.register(modEventBus);
    T7RecipeSerializers.REGISTRAR.register(modEventBus);
    WandCoreMaterials.INSTANCE.getREGISTRAR().register(modEventBus);
    WandHandleMaterials.INSTANCE.getREGISTRAR().register(modEventBus);
    Aspects.REGISTRAR.register(modEventBus);
    Researches.INSTANCE.getREGISTRAR().register(modEventBus);
    T7Attributes.INSTANCE.getREGISTRAR().register(modEventBus);
    T7GlobalLootModifierSerializers.INSTANCE.getREGISTRAR().register(modEventBus);

    T7CommonModEventsKt.registerCommonModEvents();
    T7ClientModEventsKt.registerClientModEvents();
    T7CommonGameEventsKt.registerCommonGameEvents();
    T7ClientGameEventsKt.registerClientGameEvents();
  }

  public static ResourceLocation rl(String path) {
    return ResourceLocation.fromNamespaceAndPath(Thavma.MODID, path);
  }
}
