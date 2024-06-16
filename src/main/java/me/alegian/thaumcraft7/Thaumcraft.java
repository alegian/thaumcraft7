package me.alegian.thaumcraft7;

import com.mojang.logging.LogUtils;
import me.alegian.thaumcraft7.data.attachment.TCAttachments;
import me.alegian.thaumcraft7.block.TCBlocks;
import me.alegian.thaumcraft7.blockentity.TCBlockEntities;
import me.alegian.thaumcraft7.item.TCCreativeModeTabs;
import me.alegian.thaumcraft7.item.TCItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// must match value in META-INF/mods.toml
@Mod(Thaumcraft.MODID)
public class Thaumcraft {
    public static final String MODID = "thaumcraft7";
    public static final Logger LOGGER = LogUtils.getLogger();

    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Thaumcraft(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Thaumcraft::registerCapabilities);

        TCBlocks.REGISTRAR.register(modEventBus);
        TCItems.REGISTRAR.register(modEventBus);
        TCBlockEntities.REGISTRAR.register(modEventBus);
        TCAttachments.REGISTRAR.register(modEventBus);

        TCCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        TCItems.registerCapabilities(event);
        TCBlocks.registerCapabilities(event);
    }
}
