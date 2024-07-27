package me.alegian.thaumcraft7.impl.client.event;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.extension.ThaumometerItemExtensions;
import me.alegian.thaumcraft7.impl.client.extension.WandItemExtensions;
import me.alegian.thaumcraft7.impl.client.gui.VisGuiOverlay;
import me.alegian.thaumcraft7.impl.client.particle.AspectsParticle;
import me.alegian.thaumcraft7.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.AuraNodeBER;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.CrucibleBER;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeB;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCBlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCItems;
import me.alegian.thaumcraft7.impl.init.registries.deferred.TCParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
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
      event.registerSpriteSet(TCParticleTypes.ASPECTS.get(), AspectsParticle.Provider::new);
      event.registerSpriteSet(TCParticleTypes.CRUCIBLE_BUBBLE.get(), CrucibleBubbleParticle.Provider::new);
      // and #registerSpecial, which maps to a Supplier<Particle>. See the source code of the event for further info.
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

      AspectsParticle.renderAsGUI(event.getGuiGraphics(), ((BlockHitResult) hitResult).getBlockPos());
    }

    @SubscribeEvent
    public static void renderBlockHighlight(RenderHighlightEvent.Block event) {
      var level = Minecraft.getInstance().level;
      if (level != null) {
        var blockPos = event.getTarget().getBlockPos();
        var block = level.getBlockState(blockPos).getBlock();
        boolean hasAspects = block instanceof AuraNodeB;
        if (hasAspects) {
          if (!blockPos.equals(AspectsParticle.blockPos)) {
            AspectsParticle.kill = false;
            level.addParticle(TCParticleTypes.ASPECTS.get(), blockPos.getX() + 0.5, blockPos.getY() + 1.25, blockPos.getZ() + 0.5, 0, 0, 0);
          }
        } else AspectsParticle.kill = true;
        AspectsParticle.blockPos = new BlockPos(blockPos);

        if (level.getBlockState(event.getTarget().getBlockPos()).getBlock() instanceof AuraNodeB)
          event.setCanceled(true);
      }
    }
  }
}
