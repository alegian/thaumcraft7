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
        this(texture, x, y, 1, 1);
    }

    public void render(GuiGraphics guiGraphics, int cellSize, double scrollX, double scrollY, boolean hovered, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.push();
        graphics.drawSimpleTexture(
                texture,
                (int) (cellSize*(x-sizeX/2f)-scrollX),
                (int) (cellSize*(y-sizeY/2f)-scrollY),
                cellSize*sizeX,
                cellSize*sizeY
        );
        graphics.pop();
    }
}
