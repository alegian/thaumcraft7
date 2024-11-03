package me.alegian.thaumcraft7.impl.client.gui;

import me.alegian.thaumcraft7.impl.client.T7GuiGraphics;
import me.alegian.thaumcraft7.impl.client.texture.Texture;
import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class VisGuiOverlay {
  private static final Texture STAR = new Texture("gui/overlay/star", 130, 130);
  private static final Texture BAR_FRAME = new Texture("gui/overlay/bar_frame", 96, 96);
  private static final Texture BAR_CONTENT = new Texture("gui/overlay/bar_content", 18, 64);

  public static final LayeredDraw.Layer LAYER = ((guiGraphics, partialTick) -> {
    var aspectContainer = AspectContainerHelper.getAspectContainerInHand(Minecraft.getInstance().player);
    if (aspectContainer == null || Minecraft.getInstance().options.hideGui) return;

    var vis = aspectContainer.getAspects();
    var maxAmount = aspectContainer.getMaxAmount();

    if (vis == null) return;

    final var graphics = new T7GuiGraphics(guiGraphics);

    graphics.push();

    // draw the disk
    graphics.scaleXY(0.5f);
    graphics.translateXY(32, 16);
    graphics.resetColor();
    graphics.blit(STAR.location(), 0, 0, 0, 0, STAR.width(), STAR.height(), STAR.width(), STAR.height());

    // draw the vials
    graphics.translateXY(STAR.width() / 2f, STAR.height() / 2f);
    graphics.rotateZ(15);

    for (var deferredAspect : Aspects.PRIMAL_ASPECTS) {
      Aspect a = deferredAspect.get();
      var color = a.getColorRGB();
      graphics.push();
      graphics.translateXY(0, STAR.height() / 2f);
      graphics.setColor((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
      graphics.blit(BAR_CONTENT.location(), -BAR_CONTENT.width() / 2, (BAR_FRAME.height() - BAR_CONTENT.height()) / 2, 0, 0, BAR_CONTENT.width(), BAR_CONTENT.height() * vis.get(a) / maxAmount, BAR_CONTENT.width(), BAR_CONTENT.height());
      graphics.resetColor();
      graphics.blit(BAR_FRAME.location(), -BAR_FRAME.width() / 2, 0, 0, 0, BAR_FRAME.width(), BAR_FRAME.height(), BAR_FRAME.width(), BAR_FRAME.height());
      graphics.pop();
      graphics.rotateZ(-24);
    }

    graphics.pop();
  });
}
