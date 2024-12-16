package me.alegian.thavma.impl.client.event

import com.mojang.datafixers.util.Either
import me.alegian.thavma.impl.client.ClientHelper
import me.alegian.thavma.impl.client.gui.tooltip.AspectTooltipComponent
import me.alegian.thavma.impl.client.gui.tooltip.TooltipHelper
import me.alegian.thavma.impl.client.renderer.AspectRenderer
import me.alegian.thavma.impl.client.renderer.HammerHighlightRenderer
import me.alegian.thavma.impl.common.block.AuraNodeBlock
import me.alegian.thavma.impl.common.data.capability.AspectContainer
import me.alegian.thavma.impl.common.data.capability.IAspectContainer
import me.alegian.thavma.impl.common.item.HammerItem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.neoforged.api.distmarker.Dist
import net.neoforged.neoforge.client.event.RenderHighlightEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent
import net.neoforged.neoforge.client.event.RenderPlayerEvent
import net.neoforged.neoforge.client.event.RenderTooltipEvent.GatherComponents
import thedarkcolour.kotlinforforge.neoforge.forge.DIST
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS as KFF_GAME_BUS


private var allowHammerOutlineEvents = true

fun renderBlockHighlight(event: RenderHighlightEvent.Block) {
    val level = Minecraft.getInstance().level ?: return
    val hitResult = event.target
    if (hitResult.type == HitResult.Type.MISS) return
    val targetPos = hitResult.blockPos
    val player = Minecraft.getInstance().player ?: return
    val itemStack = player.mainHandItem
    val item = itemStack.item

    if (allowHammerOutlineEvents) if (item is HammerItem) {
        allowHammerOutlineEvents = false
        HammerHighlightRenderer.render(event, item, player, level, itemStack, hitResult)
        allowHammerOutlineEvents = true
    }

    // aura nodes have no outline
    if (level.getBlockState(targetPos).block is AuraNodeBlock) event.isCanceled = true
}

fun renderLevelAfterWeather(event: RenderLevelStageEvent) {
    if (event.stage !== RenderLevelStageEvent.Stage.AFTER_WEATHER) return

    // general purpose useful stuff
    val minecraft = Minecraft.getInstance()
    if (minecraft.level == null) return
    val hitResult = minecraft.hitResult
    if (hitResult == null || hitResult.type != HitResult.Type.BLOCK) return
    val blockPos = (hitResult as BlockHitResult).blockPos

    // aspect renderer
    if (!AspectContainer.isAspectContainer(minecraft.level, blockPos)) return
    if (!ClientHelper.localPlayerHasRevealing()) return

    AspectContainer.at(minecraft.level, blockPos)
        .map(IAspectContainer::getAspects)
        .ifPresent { aspects ->
            AspectRenderer.renderAfterWeather(
                aspects,
                event.poseStack,
                event.camera,
                blockPos
            )
        }
}

fun gatherTooltipComponents(event: GatherComponents) {
    if (!ClientHelper.localPlayerHasRevealing()) return

    AspectContainer.from(event.itemStack).map(IAspectContainer::getAspects)
        .ifPresent { aspectMap ->
            event.tooltipElements.add(
                Either.left(
                    TooltipHelper.containedPrimals(aspectMap)
                )
            )
        }

    if (!Screen.hasShiftDown()) return

    event.tooltipElements.addLast(Either.right(AspectTooltipComponent(event.itemStack)))
}

fun renderPlayerPre(event: RenderPlayerEvent.Pre) {
    val model = event.renderer.model

    // if chestplate exists, disable sleeves & jacket to prevent clipping with thin armors
    ClientHelper.getLocalPlayerEquipmentItem(EquipmentSlot.CHEST).ifPresent {
        model.leftSleeve.visible = false
        model.rightSleeve.visible = false
        model.jacket.visible = false
    }
    // if leggings exist, disable pants to prevent clipping with thin armors
    ClientHelper.getLocalPlayerEquipmentItem(EquipmentSlot.LEGS).ifPresent {
        model.leftPants.visible = false
        model.rightPants.visible = false
    }
}

fun registerClientGameEvents() {
    if (DIST != Dist.CLIENT) return

    KFF_GAME_BUS.addListener(::renderBlockHighlight)
    KFF_GAME_BUS.addListener(::renderLevelAfterWeather)
    KFF_GAME_BUS.addListener(::gatherTooltipComponents)
    KFF_GAME_BUS.addListener(::renderPlayerPre)
}