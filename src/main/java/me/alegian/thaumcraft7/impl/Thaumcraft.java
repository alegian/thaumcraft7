package me.alegian.thaumcraft7.impl;

import com.mojang.logging.LogUtils;
import me.alegian.thaumcraft7.impl.init.registries.deferred.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// must match value in META-INF/mods.toml
@Mod(Thaumcraft.MODID)
public class Thaumcraft {
  public static final String MODID = "thaumcraft7";
  public static final Logger LOGGER = LogUtils.getLogger();

  // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
  public Thaumcraft(IEventBus modEventBus) {
    T7ArmorMaterials.REGISTRAR.register(modEventBus);
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
    WandCoreMaterials.REGISTRAR.register(modEventBus);
    WandHandleMaterials.REGISTRAR.register(modEventBus);
    Aspects.REGISTRAR.register(modEventBus);
    Researches.REGISTRAR.register(modEventBus);
    T7Attributes.REGISTRAR.register(modEventBus);
  }

  public static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
  }
}
