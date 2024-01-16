package me.alegian.thaumcraft7.event;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.aspects.Aspect;
import me.alegian.thaumcraft7.api.aspects.AspectList;
import me.alegian.thaumcraft7.api.capabilities.VisStorageHelper;
import me.alegian.thaumcraft7.client.VisGuiOverlay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CommonForgeEvents{
        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event){
            var visItem = VisStorageHelper.getVisItemInHand(event.player);
            if(visItem != null){
                var amount = VisStorageHelper.getVisStored(visItem) / VisStorageHelper.getMaxVisStored(visItem);
                VisGuiOverlay.visible = true;
                VisGuiOverlay.vis = new AspectList()
                        .add(Aspect.PERDITIO, (int) (100*amount))
                        .add(Aspect.ORDO, (int) (100*amount))
                        .add(Aspect.AQUA, (int) (100*amount))
                        .add(Aspect.IGNIS, (int) (100*amount))
                        .add(Aspect.TERRA, (int) (100*amount))
                        .add(Aspect.AER, (int) (100*amount));
            }else{
                VisGuiOverlay.visible = false;
            }
        }
    }
}