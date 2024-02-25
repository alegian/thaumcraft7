package me.alegian.thaumcraft7.client.gui.thaumonomicon.widget;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.client.gui.GuiGraphicsWrapper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.resources.ResourceLocation;

// the wooden frame around the contents
public class Frame implements Renderable {
    private static final ResourceLocation FRAME = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/frame.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        final int screenHeight = guiGraphics.guiHeight();
        final int screenWidth = guiGraphics.guiWidth();
        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.drawSimpleTexture(
                FRAME,
                0,
                0,
                screenWidth,
                screenHeight
        );
    }
}
