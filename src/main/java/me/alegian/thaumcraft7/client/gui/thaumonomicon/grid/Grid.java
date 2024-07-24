package me.alegian.thaumcraft7.client.gui.thaumonomicon.grid;

import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

/*
 * The invisible grid that renders the contents of the Thaumonomicon.
 * The research nodes are by definition 1x1.
 * Contains a list of GridRenderable objects that are drawn.
 */
public class Grid {
  private final int cellSize;
  private final List<GridRenderable> contents = new ArrayList<>();

  public Grid(int cellSize) {
    this.cellSize = cellSize;
  }

  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, double scrollX, double scrollY) {
    for (GridRenderable r : contents) {
      r.render(pGuiGraphics, cellSize, scrollX, scrollY, false, pPartialTick);
    }
  }

  public void renderDebug(GuiGraphics guiGraphics, double scrollX, double scrollY) {
    for (int i = -31; i < 32; i++)
      guiGraphics.hLine(-1000, 1000, (int) (i * cellSize - scrollY), 0xFFFFFFFF);

    for (int i = -31; i < 32; i++)
      guiGraphics.vLine((int) (i * cellSize - scrollX), -1000, 1000, 0xFFFFFFFF);
  }

  public void addCell(GridRenderable r) {
    contents.add(r);
  }
}
