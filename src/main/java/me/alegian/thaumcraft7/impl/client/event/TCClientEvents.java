package me.alegian.thaumcraft7.impl.client.event;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.extension.ThaumometerItemExtensions;
import me.alegian.thaumcraft7.impl.client.extension.WandItemExtensions;
import me.alegian.thaumcraft7.impl.client.gui.VisGuiOverlay;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import me.alegian.thaumcraft7.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.AuraNodeBER;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.CrucibleBER;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeB;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCBlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCItems;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class TCClientEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
      event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "vis_overlay"), VisGuiOverlay.VIS_OVERLAY);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
      event.registerBlockEntityRenderer(TCBlockEntities.AURA_NODE.get(), ctx -> new AuraNodeBER());
      event.registerBlockEntityRenderer(TCBlockEntities.CRUCIBLE.get(), ctx -> new CrucibleBER());
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet(TCParticleTypes.CRUCIBLE_BUBBLE.get(), CrucibleBubbleParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new WandItemExtensions(), TCItems.IRON_WOOD_WAND.get());
      event.registerItem(new ThaumometerItemExtensions(), TCItems.THAUMOMETER.get());
    }

    @SubscribeEvent
    public static void registerReloadListenerEvent(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(AspectAtlas.INSTANCE);
    }
  }

  @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
  public static class ClientGameEvents {
    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Pre event) {
      VisGuiOverlay.update(event.getEntity());
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RenderGuiEvent.Pre event) {
      var player = Minecraft.getInstance().player;
      if (player == null) return;
      var hitResult = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 0, false);
      if (hitResult.getType() != HitResult.Type.BLOCK) return;

      //AspectsParticle.renderAsGUI(event.getGuiGraphics(), ((BlockHitResult) hitResult).getBlockPos());
    }

    @SubscribeEvent
    public static void renderBlockHighlight(RenderHighlightEvent.Block event) {
      var level = Minecraft.getInstance().level;
      if (level == null) return;

      if (level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof AuraNodeB)
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void renderLevelAfterWeather(RenderLevelStageEvent event) {
      var minecraft = Minecraft.getInstance();
      if (minecraft.level == null) return;
      if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) return;
      var hitResult = minecraft.hitResult;
      if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;
      var blockPos = ((BlockHitResult) hitResult).getBlockPos();
      if (!(minecraft.level.getBlockState(blockPos).getBlock() instanceof AuraNodeB)) return;

      AspectRenderer.renderAfterWeather(event.getPoseStack(), minecraft.renderBuffers().bufferSource(), event.getCamera(), blockPos);
    }
  }
}
