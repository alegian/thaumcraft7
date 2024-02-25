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
    private final int rotationDegrees;

    public GridRenderable(ResourceLocation texture, int x, int y, int sizeX, int sizeY, boolean flip, int rotationDegrees) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.sizeX = flip ? -sizeX : sizeX;
        this.sizeY = flip ? -sizeY : sizeY;
        this.rotationDegrees = rotationDegrees;
    }

    public GridRenderable(ResourceLocation texture, int x, int y, int sizeX, int sizeY) {
        this(texture, x, y, sizeX, sizeY, false, 0);
    }

    public GridRenderable(ResourceLocation texture, int x, int y, int rotationDegrees) {
        this(texture, x, y, 1, 1, false, rotationDegrees);
    }

    public GridRenderable(ResourceLocation texture, int x, int y, boolean flip, int rotationDegrees) {
        this(texture, x, y, 1, 1, flip, rotationDegrees);
    }

    public GridRenderable(ResourceLocation texture, int x, int y) {
        this(texture, x, y, 1, 1);
    }

    public void render(GuiGraphics guiGraphics, int cellSize, double scrollX, double scrollY, boolean hovered, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);
        double xPos = cellSize * (x - sizeX / 2f);
        double yPos = cellSize * (y - sizeY / 2f);

        graphics.push();
        graphics.translateXY((float) -scrollX, (float) -scrollY);
        graphics.rotateZ(rotationDegrees);
        graphics.translateXY((float) xPos, (float) yPos);
        graphics.drawSimpleTexture(
                texture,
                0,
                0,
                cellSize*sizeX,
                cellSize*sizeY
        );
        graphics.pop();
    }
}
