package me.alegian.thaumcraft7.event;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.capabilities.VisStorageHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CommonForgeEvents{
        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event){
            if(VisStorageHelper.hasVisInHand(event.player)){

            }
        }
    }
}