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
    private final boolean offset;

    public GridRenderable(ResourceLocation texture, int x, int y, int sizeX, int sizeY, boolean offset) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.offset = offset;
    }

    public GridRenderable(ResourceLocation texture, int x, int y, int sizeX, int sizeY) {
        this(texture, x, y, sizeX, sizeY, false);
    }

    public GridRenderable(ResourceLocation texture, int x, int y) {
        this(texture, x, y, 1, 1);
    }

    public void render(GuiGraphics guiGraphics, int cellSize, double scrollX, double scrollY, boolean hovered, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.push();
        if(!offset) graphics.translateXY((float) -cellSize /2, (float) -cellSize /2);
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
