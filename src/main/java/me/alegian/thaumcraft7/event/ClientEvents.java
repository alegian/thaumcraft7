package me.alegian.thaumcraft7.event;
import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.blockentity.AuraNodeBlockEntity;
import me.alegian.thaumcraft7.blockentity.BlockEntityIndex;
import me.alegian.thaumcraft7.blockentity.renderer.AuraNodeBER;
import me.alegian.thaumcraft7.client.VisGuiOverlay;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.event.TickEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll(new ResourceLocation(Thaumcraft.MODID, "vis_overlay"), VisGuiOverlay.VIS_OVERLAY);
        }

        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(BlockEntityIndex.AURA_NODE.get(), ctx -> new AuraNodeBER());
        }
    }

    @Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientForgeEvents{
        @SubscribeEvent
        public static void playerTick(TickEvent.PlayerTickEvent event){
            VisGuiOverlay.update(event.player);
        }
    }
}
