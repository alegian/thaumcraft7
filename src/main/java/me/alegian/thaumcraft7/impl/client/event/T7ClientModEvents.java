package me.alegian.thaumcraft7.impl.client.event;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.T7Colors;
import me.alegian.thaumcraft7.impl.client.T7RenderStateShards;
import me.alegian.thaumcraft7.impl.client.extension.OculusItemExtensions;
import me.alegian.thaumcraft7.impl.client.extension.WandItemExtensions;
import me.alegian.thaumcraft7.impl.client.gui.VisGuiOverlay;
import me.alegian.thaumcraft7.impl.client.gui.tooltip.AspectClientTooltipComponent;
import me.alegian.thaumcraft7.impl.client.gui.tooltip.AspectTooltipComponent;
import me.alegian.thaumcraft7.impl.client.model.CubeOverlayModel;
import me.alegian.thaumcraft7.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.AuraNodeBER;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.CrucibleBER;
import me.alegian.thaumcraft7.impl.client.renderer.entity.FancyItemER;
import me.alegian.thaumcraft7.impl.client.renderer.entity.VisER;
import me.alegian.thaumcraft7.impl.client.screen.ArcaneWorkbenchScreen;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import me.alegian.thaumcraft7.impl.common.item.TestaItem;
import me.alegian.thaumcraft7.impl.init.registries.deferred.*;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.io.IOException;

@EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class T7ClientModEvents {
  @SubscribeEvent
  public static void registerGuiLayers(RegisterGuiLayersEvent event) {
    event.registerAboveAll(Thaumcraft.id("vis"), VisGuiOverlay.LAYER);
  }

  @SubscribeEvent
  public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(T7BlockEntities.AURA_NODE.get(), ctx -> new AuraNodeBER());
    event.registerBlockEntityRenderer(T7BlockEntities.CRUCIBLE.get(), ctx -> new CrucibleBER());
    event.registerEntityRenderer(T7EntityTypes.FANCY_ITEM.get(), FancyItemER::new);
    event.registerEntityRenderer(T7EntityTypes.VIS.get(), VisER::new);
  }

  @SubscribeEvent
  public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(T7ParticleTypes.CRUCIBLE_BUBBLE.get(), CrucibleBubbleParticle.Provider::new);
  }

  @SubscribeEvent
  public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
    for (var wand : T7Items.WANDS.values()) {
      event.registerItem(new WandItemExtensions(), wand);
    }
    event.registerItem(new OculusItemExtensions(), T7Items.OCULUS.get());
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
          if (tintIndex == 0) return ((TestaItem) stack.getItem()).getAspect().getColor();
          return 0xFFFFFFFF;
        },
        T7Items.IGNIS_TESTA.get(),
        T7Items.AER_TESTA.get(),
        T7Items.TERRA_TESTA.get(),
        T7Items.AQUA_TESTA.get(),
        T7Items.ORDO_TESTA.get(),
        T7Items.PERDITIO_TESTA.get()
    );
    event.register((stack, tintIndex) -> {
          if (tintIndex == 0) return T7Colors.GREATWOOD_LEAVES;
          return 0xFFFFFFFF;
        },
        T7Blocks.GREATWOOD_LEAVES.get()
    );
    event.register((stack, tintIndex) -> {
          if (tintIndex == 0) return T7Colors.SILVERWOOD_LEAVES;
          return 0xFFFFFFFF;
        },
        T7Blocks.SILVERWOOD_LEAVES.get()
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
    event.register((blockState, blockAndTintGetter, blockPos, tintIndex) -> {
          if (tintIndex == 0) return T7Colors.SILVERWOOD_LEAVES;
          return 0xFFFFFFFF;
        },
        T7Blocks.SILVERWOOD_LEAVES.get()
    );
  }

  @SubscribeEvent
  public static void registerShaders(RegisterShadersEvent event) throws IOException {
    event.registerShader(new ShaderInstance(event.getResourceProvider(), Thaumcraft.id("aura_node"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
      T7RenderStateShards.auraNodeShader = shaderInstance;
    });
  }

  @SubscribeEvent
  public static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
    event.register(AspectTooltipComponent.class, AspectClientTooltipComponent::new);
  }

  @SubscribeEvent
  public static void registerScreens(RegisterMenuScreensEvent event) {
    event.register(T7MenuTypes.ARCANE_WORKBENCH.get(), ArcaneWorkbenchScreen::new);
  }
}
