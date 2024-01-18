package me.alegian.thaumcraft7.client;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ThaumonomiconScreen extends Screen {
    public ThaumonomiconScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableOnly(new Frame());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        super.render(guiGraphics, mouseX, mouseY, tickDelta);
    }
}

class Frame implements Renderable{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/frame.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        int screenHeight = guiGraphics.guiHeight();
        int screenWidth = guiGraphics.guiWidth();

        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }
}
