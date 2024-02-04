package me.alegian.thaumcraft7.client.gui.thaumonomicon.grid;

import me.alegian.thaumcraft7.client.gui.GuiGraphicsWrapper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GridRenderable {
    private final ResourceLocation texture;
    private final int x;
    private final int y;
    private final int sizeX;
    private final int sizeY;

    public GridRenderable(ResourceLocation texture, int x, int y, int sizeX, int sizeY) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public GridRenderable(ResourceLocation texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.sizeX = 1;
        this.sizeY = 1;
    }

    public void render(GuiGraphics guiGraphics, int cellSize, double scrollX, double scrollY, boolean hovered, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.push();
        graphics.translateXY((float) -cellSize*sizeX /2, (float) -cellSize*sizeY /2);
        graphics.drawSimpleTexture(
                texture,
                (int) (cellSize*x-scrollX),
                (int) (cellSize*y-scrollY),
                cellSize*sizeX,
                cellSize*sizeY
        );
        graphics.pop();
    }
}
