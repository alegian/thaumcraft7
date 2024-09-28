package me.alegian.thaumcraft7.impl.client.renderer;

import me.alegian.thaumcraft7.impl.common.item.HammerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

public class HammerHighlightRenderer {
  /**
   * Used in highlight event.
   * Render outline for the block positions supplied by a hammer.
   * Calls recursive highlight events for each one
   */
  public static void render(RenderHighlightEvent.Block event, HammerItem hammer, LocalPlayer player, ClientLevel level, ItemStack itemStack, BlockHitResult hitResult) {
    var levelRenderer = event.getLevelRenderer();
    var camera = event.getCamera();

    for (var blockPos : hammer.getValid3x3PositionsExceptOrigin(event.getTarget(), level, itemStack)) {
      var currHitResult = new BlockHitResult(hitResult.getLocation(), hitResult.getDirection(), blockPos, hitResult.isInside());
      if (!ClientHooks.onDrawHighlight(levelRenderer, camera, currHitResult, event.getDeltaTracker(), event.getPoseStack(), event.getMultiBufferSource()))
        levelRenderer.renderHitOutline(
            event.getPoseStack(),
            event.getMultiBufferSource().getBuffer(RenderType.LINES),
            player,
            camera.getPosition().x,
            camera.getPosition().y,
            camera.getPosition().z,
            blockPos,
            level.getBlockState(blockPos)
        );
    }
  }
}
