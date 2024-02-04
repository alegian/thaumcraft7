package me.alegian.thaumcraft7.client.gui.thaumonomicon.grid;

import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class Grid{
    private final int cellSize;
    private final List<GridRenderable> contents = new ArrayList<>();

    public Grid(int cellSize) {
        this.cellSize = cellSize;
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, double scrollX, double scrollY) {
        for(GridRenderable r : contents){
            r.render(pGuiGraphics, cellSize, scrollX, scrollY, false, pPartialTick);
        }
    }

    public void addCell(GridRenderable r){
        contents.add(r);
    }
}
