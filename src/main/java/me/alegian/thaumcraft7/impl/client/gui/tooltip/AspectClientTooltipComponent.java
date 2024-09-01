package me.alegian.thaumcraft7.impl.client.gui.tooltip;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectStack;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

public class AspectClientTooltipComponent implements ClientTooltipComponent {
  public AspectClientTooltipComponent(AspectTooltipComponent tooltip) {

  }

  @Override
  public int getHeight() {
    return 16;
  }

  @Override
  public int getWidth(Font pFont) {
    return 16;
  }

  @Override
  public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
    AspectRenderer.renderAspect(pGuiGraphics, AspectStack.of(Aspect.IGNIS, 4), pX, pY);
  }
}
