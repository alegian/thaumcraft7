package me.alegian.thaumcraft7.client.gui.thaumonomicon.widget;

import me.alegian.thaumcraft7.client.gui.GuiGraphicsWrapper;
import me.alegian.thaumcraft7.client.texture.TCTextures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;

// the wooden frame around the contents
public class Frame implements Renderable {

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        final int screenHeight = guiGraphics.guiHeight();
        final int screenWidth = guiGraphics.guiWidth();
        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.drawSimpleTexture(
                TCTextures.Thaumonomicon.FRAME.location(),
                0,
                0,
                screenWidth,
                screenHeight
        );
    }
}
