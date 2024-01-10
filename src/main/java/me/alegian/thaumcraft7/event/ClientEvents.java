package me.alegian.thaumcraft7.event;
import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.client.VisGuiOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll(new ResourceLocation(Thaumcraft.MODID, "vis_overlay"), VisGuiOverlay.VIS_OVERLAY);
        }
    }
}
