package me.alegian.thaumcraft7.event;
import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.AuraNodeB;
import me.alegian.thaumcraft7.block.TCBlocks;
import me.alegian.thaumcraft7.blockentity.TCBlockEntities;
import me.alegian.thaumcraft7.client.blockentity.renderer.AuraNodeBER;
import me.alegian.thaumcraft7.client.gui.VisGuiOverlay;
import me.alegian.thaumcraft7.particle.AspectsParticle;
import me.alegian.thaumcraft7.particle.TCParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ClientEvents {
    @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event){
            event.registerAboveAll(new ResourceLocation(Thaumcraft.MODID, "vis_overlay"), VisGuiOverlay.VIS_OVERLAY);
        }

        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(TCBlockEntities.AURA_NODE.get(), ctx -> new AuraNodeBER());
        }

        @SubscribeEvent
        public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
            event.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageWaterColor(level, pos) : -1,
                    TCBlocks.WATER_CRUCIBLE.get());
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(TCParticleTypes.ASPECTS.get(), AspectsParticle.Provider::new);
            // and #registerSpecial, which maps to a Supplier<Particle>. See the source code of the event for further info.
        }
    }

    @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ClientGameEvents{
        @SubscribeEvent
        public static void playerTick(PlayerTickEvent.Pre event){
            VisGuiOverlay.update(event.getEntity());
        }

        @SubscribeEvent
        public static void renderBlockHighlightEvent(RenderHighlightEvent.Block event){
            var level = Minecraft.getInstance().level;
            if(level != null) {
                var blockPos = event.getTarget().getBlockPos();
                var block = level.getBlockState(blockPos).getBlock();
                if (block instanceof AuraNodeB) {
                    AspectsParticle.toRemove = false;
                    level.addParticle(TCParticleTypes.ASPECTS.get(), blockPos.getX() + 0.5, blockPos.getY() + 1.25, blockPos.getZ() + 0.5, 0, 0, 0);
                }
                else AspectsParticle.toRemove = true;

                if (level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof AuraNodeB)
                    event.setCanceled(true);
            }
        }
    }
}
