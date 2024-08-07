package me.alegian.thaumcraft7.impl.client.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import me.alegian.thaumcraft7.api.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.T7Colors;
import me.alegian.thaumcraft7.impl.client.T7RenderStateShards;
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
import me.alegian.thaumcraft7.impl.common.item.ShardItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.io.IOException;

public class T7ClientEvents {
  @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class T7ClientModEvents {
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
      event.register(CubeOverlayModel.ID, CubeOverlayModel.Loader.INSTANCE);
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
      event.register((stack, tintIndex) -> {
            if (tintIndex == 0) return ((ShardItem) stack.getItem()).getAspect().getColor();
            return 0xFFFFFFFF;
          },
          T7Items.IGNIS_SHARD.get(),
          T7Items.AER_SHARD.get(),
          T7Items.TERRA_SHARD.get(),
          T7Items.AQUA_SHARD.get(),
          T7Items.ORDO_SHARD.get(),
          T7Items.PERDITIO_SHARD.get()
      );
      event.register((stack, tintIndex) -> {
            if (tintIndex == 0) return T7Colors.GREATWOOD_LEAVES;
            return 0xFFFFFFFF;
          },
          T7Blocks.GREATWOOD_LEAVES_ITEM.get()
      );
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
      event.register((blockState, blockAndTintGetter, blockPos, tintIndex) -> {
            if (tintIndex == 0) return T7Colors.GREATWOOD_LEAVES;
            return 0xFFFFFFFF;
          },
          T7Blocks.GREATWOOD_LEAVES.get()
      );
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
      event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "custom_shader"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
        T7RenderStateShards.customShader = shaderInstance;
      });
    }
  }

  @EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
  public static class T7ClientGameEvents {
    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Pre event) {
      VisGuiOverlay.update(event.getEntity());
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
