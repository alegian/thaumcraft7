package me.alegian.thavma.impl.client.event

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.client.T7Colors
import me.alegian.thavma.impl.client.T7RenderStateShards
import me.alegian.thavma.impl.client.T7VertexFormats
import me.alegian.thavma.impl.client.extension.BEWLRItemExtensionFactory
import me.alegian.thavma.impl.client.extension.OculusItemExtensions
import me.alegian.thavma.impl.client.extension.WandItemExtensions
import me.alegian.thavma.impl.client.gui.VisGuiOverlay
import me.alegian.thavma.impl.client.gui.tooltip.AspectClientTooltipComponent
import me.alegian.thavma.impl.client.gui.tooltip.AspectTooltipComponent
import me.alegian.thavma.impl.client.model.WithTransformParentModel
import me.alegian.thavma.impl.client.particle.CrucibleBubbleParticle
import me.alegian.thavma.impl.client.renderer.blockentity.*
import me.alegian.thavma.impl.client.renderer.entity.FancyItemER
import me.alegian.thavma.impl.client.renderer.entity.VisER
import me.alegian.thavma.impl.client.screen.WorkbenchScreen
import me.alegian.thavma.impl.client.texture.atlas.AspectAtlas
import me.alegian.thavma.impl.common.block.entity.MatrixBE
import me.alegian.thavma.impl.common.block.entity.PedestalBE
import me.alegian.thavma.impl.common.block.entity.PillarBE
import me.alegian.thavma.impl.common.block.entity.WorkbenchBE
import me.alegian.thavma.impl.common.item.TestaItem
import me.alegian.thavma.impl.init.registries.deferred.*
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.*
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import java.io.IOException

@EventBusSubscriber(modid = Thavma.MODID, value = [Dist.CLIENT], bus = EventBusSubscriber.Bus.MOD)
object T7ClientModEvents {
    @SubscribeEvent
    fun registerGuiLayers(event: RegisterGuiLayersEvent) {
        event.registerAboveAll(Thavma.rl("vis"), VisGuiOverlay.LAYER)
    }

    @SubscribeEvent
    fun registerEntityRenderers(event: RegisterRenderers) {
        event.registerBlockEntityRenderer(
            T7BlockEntities.AURA_NODE.get()
        ) { _: BlockEntityRendererProvider.Context? -> AuraNodeBER() }
        event.registerBlockEntityRenderer(
            T7BlockEntities.CRUCIBLE.get()
        ) { _: BlockEntityRendererProvider.Context? -> CrucibleBER() }
        event.registerBlockEntityRenderer(
            T7BlockEntities.WORKBENCH.get()
        ) { _: BlockEntityRendererProvider.Context? -> WorkbenchBER() }
        event.registerBlockEntityRenderer(
            T7BlockEntities.MATRIX.get()
        ) { _: BlockEntityRendererProvider.Context? -> MatrixBER() }
        event.registerBlockEntityRenderer(
            T7BlockEntities.PILLAR.get()
        ) { _: BlockEntityRendererProvider.Context? -> PillarBER() }
        event.registerBlockEntityRenderer(
            T7BlockEntities.PEDESTAL.get()
        ) { _: BlockEntityRendererProvider.Context? -> PedestalBER() }
        event.registerEntityRenderer(
            T7EntityTypes.FANCY_ITEM.get()
        ) { ctx: EntityRendererProvider.Context? -> FancyItemER(ctx) }
        event.registerEntityRenderer(
            T7EntityTypes.VIS.get()
        ) { ctx: EntityRendererProvider.Context? -> VisER(ctx) }
    }

    @SubscribeEvent
    fun registerParticleProviders(event: RegisterParticleProvidersEvent) {
        event.registerSpriteSet(
            T7ParticleTypes.CRUCIBLE_BUBBLE.get()
        ) { pSprites -> CrucibleBubbleParticle.Provider(pSprites) }
    }

    @SubscribeEvent
    fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
        for (wand in T7Items.WANDS.values()) event.registerItem(WandItemExtensions(), wand)

        event.registerItem(OculusItemExtensions(), T7Items.OCULUS.get())
        event.registerItem(BEWLRItemExtensionFactory.create(WorkbenchBE()), T7Blocks.ARCANE_WORKBENCH.get().asItem())
        event.registerItem(BEWLRItemExtensionFactory.create(MatrixBE()), T7Blocks.MATRIX.get().asItem())
        event.registerItem(BEWLRItemExtensionFactory.create(PillarBE()), T7Blocks.PILLAR.get().asItem())
        event.registerItem(BEWLRItemExtensionFactory.create(PedestalBE()), T7Blocks.PEDESTAL.get().asItem())
    }

    @SubscribeEvent
    fun registerReloadListenerEvent(event: RegisterClientReloadListenersEvent) {
        event.registerReloadListener(AspectAtlas.INSTANCE)
    }

    @SubscribeEvent
    fun registerGeometryLoaders(event: RegisterGeometryLoaders) {
        event.register(WithTransformParentModel.ID, WithTransformParentModel.Loader.INSTANCE)
    }

    @SubscribeEvent
    fun registerItemColorHandlers(event: RegisterColorHandlersEvent.Item) {
        event.register(
            { stack, tintIndex ->
                when (tintIndex) {
                    0 -> (stack.item as TestaItem).aspect.color
                    else -> 0xFFFFFFFF.toInt()
                }
            },
            T7Items.IGNIS_TESTA.get(),
            T7Items.AER_TESTA.get(),
            T7Items.TERRA_TESTA.get(),
            T7Items.AQUA_TESTA.get(),
            T7Items.ORDO_TESTA.get(),
            T7Items.PERDITIO_TESTA.get()
        )
        event.register(
            { _, tintIndex ->
                when (tintIndex) {
                    0 -> T7Colors.GREATWOOD_LEAVES
                    else -> 0xFFFFFFFF.toInt()
                }
            },
            T7Blocks.GREATWOOD_LEAVES.get()
        )
        event.register(
            { _, tintIndex ->
                when (tintIndex) {
                    0 -> T7Colors.SILVERWOOD_LEAVES
                    else -> 0xFFFFFFFF.toInt()
                }
            },
            T7Blocks.SILVERWOOD_LEAVES.get()
        )
        for (infusedBlock in T7Blocks.INFUSED_BLOCKS)
            event.register(
                { _, tintIndex ->
                    when (tintIndex) {
                        0 -> infusedBlock.get().aspect.color
                        else -> 0xFFFFFFFF.toInt()
                    }
                }, infusedBlock.get()
            )
    }

    @SubscribeEvent
    fun registerBlockColorHandlers(event: RegisterColorHandlersEvent.Block) {
        event.register(
            { blockState: BlockState?, blockAndTintGetter: BlockAndTintGetter?, blockPos: BlockPos?, tintIndex: Int ->
                if (tintIndex == 0) return@register T7Colors.GREATWOOD_LEAVES
                -0x1
            },
            T7Blocks.GREATWOOD_LEAVES.get()
        )
        event.register(
            { blockState: BlockState?, blockAndTintGetter: BlockAndTintGetter?, blockPos: BlockPos?, tintIndex: Int ->
                if (tintIndex == 0) return@register T7Colors.SILVERWOOD_LEAVES
                -0x1
            },
            T7Blocks.SILVERWOOD_LEAVES.get()
        )
        for (infusedBlock in T7Blocks.INFUSED_BLOCKS) event.register({ blockState: BlockState?, blockAndTintGetter: BlockAndTintGetter?, blockPos: BlockPos?, tintIndex: Int ->
            if (tintIndex == 0) return@register infusedBlock.get().aspect.color
            -0x1
        }, infusedBlock.get())
    }

    @SubscribeEvent
    @Throws(IOException::class)
    fun registerShaders(event: RegisterShadersEvent) {
        event.registerShader(
            ShaderInstance(event.resourceProvider, Thavma.rl("aura_node"), T7VertexFormats.AURA_NODE)
        ) { T7RenderStateShards.auraNodeShader = it }
    }

    @SubscribeEvent
    fun registerClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
        event.register(
            AspectTooltipComponent::class.java, ::AspectClientTooltipComponent
        )
    }

    @SubscribeEvent
    fun registerScreens(event: RegisterMenuScreensEvent) {
        event.register(
            T7MenuTypes.ARCANE_WORKBENCH.get(), ::WorkbenchScreen
        )
    }
}
