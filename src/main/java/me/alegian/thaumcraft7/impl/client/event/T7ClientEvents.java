package me.alegian.thaumcraft7.impl.client.event;

import me.alegian.thaumcraft7.api.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.extension.ThaumometerItemExtensions;
import me.alegian.thaumcraft7.impl.client.extension.WandItemExtensions;
import me.alegian.thaumcraft7.impl.client.gui.VisGuiOverlay;
import me.alegian.thaumcraft7.impl.client.model.CubeOverlayModel;
import me.alegian.thaumcraft7.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.AuraNodeBER;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.CrucibleBER;
import me.alegian.thaumcraft7.impl.client.renderer.entity.FancyItemRenderer;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.block.CrucibleBlock;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7EntityTypes;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7ParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class T7ClientEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
      event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "vis_overlay"), VisGuiOverlay.VIS_OVERLAY);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
      event.registerBlockEntityRenderer(T7BlockEntities.AURA_NODE.get(), ctx -> new AuraNodeBER());
      event.registerBlockEntityRenderer(T7BlockEntities.CRUCIBLE.get(), ctx -> new CrucibleBER());
      event.registerEntityRenderer(T7EntityTypes.FANCY_ITEM.get(), FancyItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet(T7ParticleTypes.CRUCIBLE_BUBBLE.get(), CrucibleBubbleParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(new WandItemExtensions(), T7Items.IRON_WOOD_WAND.get());
      event.registerItem(new ThaumometerItemExtensions(), T7Items.THAUMOMETER.get());
    }

    @SubscribeEvent
    public static void registerReloadListenerEvent(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(AspectAtlas.INSTANCE);
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
      event.register(CubeOverlayModel.ID, CubeOverlayModel.GeometryLoader.INSTANCE);
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

      if (level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof AuraNodeBlock)
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
      if (!(minecraft.level.getBlockState(blockPos).getBlock() instanceof CrucibleBlock)) return;
      var player = minecraft.player;
      if (player == null) return;
      var helmet = player.getInventory().armor.get(EquipmentSlot.HEAD.getIndex());
      if (helmet.getCapability(T7Capabilities.REVEALING) == null) return;

      AspectContainerHelper.getAspects(minecraft.level, blockPos).ifPresent(
          aspects -> AspectRenderer.renderAfterWeather(aspects, event.getPoseStack(), minecraft.renderBuffers().bufferSource(), event.getCamera(), blockPos)
      );
    }
  }
}
