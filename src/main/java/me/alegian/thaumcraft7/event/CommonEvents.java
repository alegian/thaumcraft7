package me.alegian.thaumcraft7.event;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import me.alegian.thaumcraft7.blockentity.TCBlockEntities;
import me.alegian.thaumcraft7.item.TCItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class  CommonEvents {
    @EventBusSubscriber(modid = Thaumcraft.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents{
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            TCItems.registerCapabilities(event);
            TCBlocks.registerCapabilities(event);
            TCBlockEntities.registerCapabilities(event);
        }
    }
}