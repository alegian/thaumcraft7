package me.alegian.thaumcraft7.impl.client.gui.thaumonomicon.widget;

import me.alegian.thaumcraft7.impl.client.T7GuiGraphics;
import me.alegian.thaumcraft7.impl.client.texture.T7Textures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

// the wooden frame around the contents
@OnlyIn(Dist.CLIENT)
public class Frame implements Renderable {

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
    final int screenHeight = guiGraphics.guiHeight();
    final int screenWidth = guiGraphics.guiWidth();
    final var graphics = new T7GuiGraphics(guiGraphics);

    graphics.blitSimple(
        T7Textures.Thaumonomicon.FRAME.location(),
        0,
        0,
        screenWidth,
        screenHeight
    );
  }
}
