package me.alegian.thaumcraft7.event;
import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.AuraNodeB;
import me.alegian.thaumcraft7.blockentity.BlockEntityIndex;
import me.alegian.thaumcraft7.client.blockentity.renderer.AuraNodeBER;
import me.alegian.thaumcraft7.client.gui.VisGuiOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
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

        @SubscribeEvent
        public static void renderBlockHighlightEvent(RenderHighlightEvent.Block event){
            var level = Minecraft.getInstance().level;
                if(level != null && level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof AuraNodeB)
                    event.setCanceled(true);
        }
    }
}
