package me.alegian.thavma.impl.client.event;

import com.mojang.datafixers.util.Either;
import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.ClientHelper;
import me.alegian.thavma.impl.client.gui.tooltip.AspectTooltipComponent;
import me.alegian.thavma.impl.client.gui.tooltip.TooltipHelper;
import me.alegian.thavma.impl.client.renderer.AspectRenderer;
import me.alegian.thavma.impl.client.renderer.HammerHighlightRenderer;
import me.alegian.thavma.impl.common.block.AuraNodeBlock;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.data.capability.IAspectContainer;
import me.alegian.thavma.impl.common.item.HammerItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = Thavma.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class T7ClientGameEvents {
  private static boolean allowHammerOutlineEvents = true;

  @SubscribeEvent
  public static void renderBlockHighlight(RenderHighlightEvent.Block event) {
    var level = Minecraft.getInstance().level;
    if (level == null) return;
    var hitResult = event.getTarget();
    if (hitResult.getType() == HitResult.Type.MISS) return;
    var targetPos = hitResult.getBlockPos();
    var player = Minecraft.getInstance().player;
    if (player == null) return;
    var itemStack = player.getMainHandItem();
    var item = itemStack.getItem();

    if (T7ClientGameEvents.allowHammerOutlineEvents) if (item instanceof HammerItem hammer) {
      T7ClientGameEvents.allowHammerOutlineEvents = false;
      HammerHighlightRenderer.render(event, hammer, player, level, itemStack, hitResult);
      T7ClientGameEvents.allowHammerOutlineEvents = true;
    }

    // aura nodes have no outline
    if (level.getBlockState(targetPos).getBlock() instanceof AuraNodeBlock)
      event.setCanceled(true);
  }

  @SubscribeEvent
  public static void renderLevelAfterWeather(RenderLevelStageEvent event) {
    if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_WEATHER) return;

    // general purpose useful stuff
    var minecraft = Minecraft.getInstance();
    if (minecraft.level == null) return;
    var hitResult = minecraft.hitResult;
    if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;
    var blockPos = ((BlockHitResult) hitResult).getBlockPos();
    var player = minecraft.player;
    if (player == null) return;

    // aspect renderer
    if (!AspectContainer.isAspectContainer(minecraft.level, blockPos)) return;
    if (!ClientHelper.localPlayerHasRevealing()) return;

    AspectContainer.at(minecraft.level, blockPos)
        .map(IAspectContainer::getAspects)
        .ifPresent(
            aspects -> AspectRenderer.renderAfterWeather(aspects, event.getPoseStack(), event.getCamera(), blockPos)
        );
  }

  @SubscribeEvent
  public static void gatherTooltipComponents(RenderTooltipEvent.GatherComponents event) {
    if (!ClientHelper.localPlayerHasRevealing()) return;

    AspectContainer.from(event.getItemStack()).map(IAspectContainer::getAspects)
        .ifPresent(aspectMap ->
            event.getTooltipElements().add(Either.left(TooltipHelper.containedPrimals(aspectMap)))
        );

    if (!Screen.hasShiftDown()) return;

    event.getTooltipElements().addLast(Either.right(new AspectTooltipComponent(event.getItemStack())));
  }

  @SubscribeEvent
  public static void renderPlayerPre(RenderPlayerEvent.Pre event) {
    var model = event.getRenderer().getModel();

    // if chestplate exists, disable sleeves & jacket to prevent clipping with thin armors
    ClientHelper.getLocalPlayerEquipmentItem(EquipmentSlot.CHEST).ifPresent($ -> {
      model.leftSleeve.visible = false;
      model.rightSleeve.visible = false;
      model.jacket.visible = false;
    });
    // if leggings exist, disable pants to prevent clipping with thin armors
    ClientHelper.getLocalPlayerEquipmentItem(EquipmentSlot.LEGS).ifPresent($ -> {
      model.leftPants.visible = false;
      model.rightPants.visible = false;
    });
  }
}
