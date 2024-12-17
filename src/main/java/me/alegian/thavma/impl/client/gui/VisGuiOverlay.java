package me.alegian.thavma.impl.client.gui;

import me.alegian.thavma.impl.client.T7GuiGraphics;
import me.alegian.thavma.impl.client.texture.Texture;
import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
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
    var aspectContainer = AspectContainer.getAspectContainerInHand(Minecraft.getInstance().player);
    if (aspectContainer == null || Minecraft.getInstance().options.hideGui) return;

    var vis = aspectContainer.getAspects();
    var maxAmount = aspectContainer.getCapacity();

    if (vis == null) return;

    final var graphics = new T7GuiGraphics(guiGraphics);

    graphics.push();

    // draw the star
    graphics.scaleXY(0.5f);
    graphics.translateXY(32, 16);
    graphics.resetColor();
    graphics.blit(VisGuiOverlay.STAR.location(), 0, 0, 0, 0, VisGuiOverlay.STAR.width(), VisGuiOverlay.STAR.height(), VisGuiOverlay.STAR.width(), VisGuiOverlay.STAR.height());

    // draw the bars
    graphics.translateXY(VisGuiOverlay.STAR.width() / 2f, VisGuiOverlay.STAR.height() / 2f);
    graphics.rotateZ(15);

    for (var deferredAspect : Aspects.INSTANCE.getPRIMAL_ASPECTS()) {
      Aspect a = deferredAspect.get();
      var color = a.getColorRGB();
      graphics.push();
      graphics.translateXY(0, VisGuiOverlay.STAR.height() / 2f);
      graphics.setColor((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
      graphics.blit(VisGuiOverlay.BAR_CONTENT.location(), -VisGuiOverlay.BAR_CONTENT.width() / 2, (VisGuiOverlay.BAR_FRAME.height() - VisGuiOverlay.BAR_CONTENT.height()) / 2, 0, 0, VisGuiOverlay.BAR_CONTENT.width(), VisGuiOverlay.BAR_CONTENT.height() * vis.get(a) / maxAmount, VisGuiOverlay.BAR_CONTENT.width(), VisGuiOverlay.BAR_CONTENT.height());
      graphics.resetColor();
      graphics.blit(VisGuiOverlay.BAR_FRAME.location(), -VisGuiOverlay.BAR_FRAME.width() / 2, 0, 0, 0, VisGuiOverlay.BAR_FRAME.width(), VisGuiOverlay.BAR_FRAME.height(), VisGuiOverlay.BAR_FRAME.width(), VisGuiOverlay.BAR_FRAME.height());
      graphics.pop();
      graphics.rotateZ(-24);
    }

    graphics.pop();
  });
}
