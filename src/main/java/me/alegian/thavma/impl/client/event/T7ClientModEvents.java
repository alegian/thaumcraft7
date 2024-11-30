package me.alegian.thavma.impl.client.event;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.T7Colors;
import me.alegian.thavma.impl.client.T7RenderStateShards;
import me.alegian.thavma.impl.client.T7VertexFormats;
import me.alegian.thavma.impl.client.extension.BEWLRItemExtensionFactory;
import me.alegian.thavma.impl.client.extension.OculusItemExtensions;
import me.alegian.thavma.impl.client.extension.WandItemExtensions;
import me.alegian.thavma.impl.client.gui.VisGuiOverlay;
import me.alegian.thavma.impl.client.gui.tooltip.AspectClientTooltipComponent;
import me.alegian.thavma.impl.client.gui.tooltip.AspectTooltipComponent;
import me.alegian.thavma.impl.client.model.CubeOverlayModel;
import me.alegian.thavma.impl.client.model.WithTransformParentModel;
import me.alegian.thavma.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thavma.impl.client.renderer.blockentity.*;
import me.alegian.thavma.impl.client.renderer.entity.FancyItemER;
import me.alegian.thavma.impl.client.renderer.entity.VisER;
import me.alegian.thavma.impl.client.screen.WorkbenchScreen;
import me.alegian.thavma.impl.client.texture.atlas.AspectAtlas;
import me.alegian.thavma.impl.common.block.entity.MatrixBE;
import me.alegian.thavma.impl.common.block.entity.PillarBE;
import me.alegian.thavma.impl.common.block.entity.WorkbenchBE;
import me.alegian.thavma.impl.common.item.TestaItem;
import me.alegian.thavma.impl.init.registries.deferred.*;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.io.IOException;

@EventBusSubscriber(modid = Thavma.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class T7ClientModEvents {
  @SubscribeEvent
  public static void registerGuiLayers(RegisterGuiLayersEvent event) {
    event.registerAboveAll(Thavma.rl("vis"), VisGuiOverlay.LAYER);
  }

  @SubscribeEvent
  public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(T7BlockEntities.AURA_NODE.get(), ctx -> new AuraNodeBER());
    event.registerBlockEntityRenderer(T7BlockEntities.CRUCIBLE.get(), ctx -> new CrucibleBER());
    event.registerBlockEntityRenderer(T7BlockEntities.WORKBENCH.get(), ctx -> new WorkbenchBER());
    event.registerBlockEntityRenderer(T7BlockEntities.MATRIX.get(), ctx -> new MatrixBER());
    event.registerBlockEntityRenderer(T7BlockEntities.PILLAR.get(), ctx -> new PillarBER());
    event.registerEntityRenderer(T7EntityTypes.FANCY_ITEM.get(), FancyItemER::new);
    event.registerEntityRenderer(T7EntityTypes.VIS.get(), VisER::new);
  }

  @SubscribeEvent
  public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(T7ParticleTypes.CRUCIBLE_BUBBLE.get(), CrucibleBubbleParticle.Provider::new);
  }

  @SubscribeEvent
  public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
    for (var wand : T7Items.WANDS.values()) event.registerItem(new WandItemExtensions(), wand);
    event.registerItem(new OculusItemExtensions(), T7Items.OCULUS.get());
    event.registerItem(BEWLRItemExtensionFactory.create(new WorkbenchBE()), T7Blocks.ARCANE_WORKBENCH.get().asItem());
    event.registerItem(BEWLRItemExtensionFactory.create(new MatrixBE()), T7Blocks.MATRIX.get().asItem());
    event.registerItem(BEWLRItemExtensionFactory.create(new PillarBE()), T7Blocks.PILLAR.get().asItem());
  }

  @SubscribeEvent
  public static void registerReloadListenerEvent(RegisterClientReloadListenersEvent event) {
    event.registerReloadListener(AspectAtlas.INSTANCE);
  }

  @SubscribeEvent
  public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
    event.register(CubeOverlayModel.ID, CubeOverlayModel.Loader.INSTANCE);
    event.register(WithTransformParentModel.ID, WithTransformParentModel.Loader.INSTANCE);
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
    event.registerShader(new ShaderInstance(event.getResourceProvider(), Thavma.rl("aura_node"), T7VertexFormats.AURA_NODE), shaderInstance -> T7RenderStateShards.auraNodeShader = shaderInstance);
  }

  @SubscribeEvent
  public static void registerClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
    event.register(AspectTooltipComponent.class, AspectClientTooltipComponent::new);
  }

  @SubscribeEvent
  public static void registerScreens(RegisterMenuScreensEvent event) {
    event.register(T7MenuTypes.ARCANE_WORKBENCH.get(), WorkbenchScreen::new);
  }
}
