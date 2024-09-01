package me.alegian.thaumcraft7.impl.client.gui.tooltip;

import com.google.common.collect.ImmutableList;
import me.alegian.thaumcraft7.api.aspect.AspectStack;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

public class AspectClientTooltipComponent implements ClientTooltipComponent {
  private final ImmutableList<AspectStack> displayedAspects;
  private static final int WIDTH = AspectRenderer.getPixelResolution();
  private static final int PADDING = 3;

  public AspectClientTooltipComponent(AspectTooltipComponent tooltip) {
    displayedAspects = tooltip.getAspectList().displayedAspects();
  }

  @Override
  public int getHeight() {
    return WIDTH + PADDING * 2;
  }

  @Override
  public int getWidth(Font pFont) {
    return (WIDTH + PADDING) * displayedAspects.size();
  }

  private boolean isEmpty() {
    return displayedAspects.isEmpty();
  }

  @Override
  public void renderImage(Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
    if (isEmpty()) return;

    int i = 0;
    for (AspectStack aspectStack : displayedAspects) {
      AspectRenderer.renderAspect(pGuiGraphics, aspectStack, pX + i * (WIDTH + PADDING), pY + PADDING);
      i++;
    }
  }
}
