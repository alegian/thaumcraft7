package me.alegian.thaumcraft7.client;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThaumonomiconScreen extends Screen {
    private final Tab tab = new Tab(300, 300);
    private boolean isScrolling;

    public ThaumonomiconScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableOnly(tab);
        this.addRenderableOnly(new Frame());
    }

    @Override
    public boolean mouseDragged(double p_97347_, double p_97348_, int p_97349_, double x, double y) {
        if (p_97349_ != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else {
                tab.scroll((float) x, (float) y);
            }

            return true;
        }
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
        final int screenHeight = guiGraphics.guiHeight();
        final int screenWidth = guiGraphics.guiWidth();
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
    }
}

class Tab implements Renderable{
    private float scrollX = 0;
    private float scrollY = 0;
    private final float maxScrollX;
    private final float maxScrollY;

    public Tab(float maxScrollX, float maxScrollY) {
        this.maxScrollX = maxScrollX;
        this.maxScrollY = maxScrollY;
    }

    public void scroll(float x, float y){
        scrollX = Mth.clamp(scrollX-x, -1*maxScrollX, maxScrollX);
        scrollY = Mth.clamp(scrollY-y, -1*maxScrollY, maxScrollY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        final ResourceLocation STARS = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/stars_layer1.png");

        int screenHeight = guiGraphics.guiHeight();
        int screenWidth = guiGraphics.guiWidth();

        final float scaleX = (float) screenWidth / 1024;
        final float scaleY = (float) screenHeight / 512;

        guiGraphics.enableScissor((int) (16*scaleX), (int) (16*scaleY), (int) (screenWidth-16*scaleX), (int) (screenHeight-16*scaleY));
        guiGraphics.fill(0, 0, screenWidth, screenHeight, 0xff17161e); // ARGB color
        guiGraphics.blit(STARS, 0, 0, scrollX, scrollY, screenWidth, screenHeight, screenWidth/4, screenHeight/4);
        guiGraphics.disableScissor();
    }
}
