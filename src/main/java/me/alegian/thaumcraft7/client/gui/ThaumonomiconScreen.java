package me.alegian.thaumcraft7.client.gui;

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

    public ThaumonomiconScreen() {
        super(Component.literal("Thaumonomicon"));
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
                tab.handleScroll((float) x, (float) y);
            }

            return true;
        }
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double x, double y) {
        tab.zoom((float) y);
        return true;
    }
}

class Frame implements Renderable{
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

class Tab implements Renderable{
    public double scrollX = 0;
    public double scrollY = 0;
    private final float maxScrollX;
    private final float maxScrollY;
    private float zoom = 2;
    private static final ResourceLocation STARS = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/stars_layer1.png");

    public Tab(float maxScrollX, float maxScrollY) {
        this.maxScrollX = maxScrollX;
        this.maxScrollY = maxScrollY;
    }

    public void handleScroll(double x, double y){
        scrollTo(scrollX-x, scrollY-y);
    }

    public void scrollTo(double x, double y){
        scrollX = Mth.clamp(x, -1*maxScrollX, maxScrollX);
        scrollY = Mth.clamp(y, -1*maxScrollY, maxScrollY);
    }

    public void zoom(float y){
        zoom = Mth.clamp(zoom-y, 0, 5);
    }

    private double getBGSize(int screenWidth){
        return 4*screenWidth/Math.pow(1.25f, zoom);
    }

    public double getTileSize(int screenWidth){
        return getBGSize(screenWidth)/64;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        int screenHeight = guiGraphics.guiHeight();
        int screenWidth = guiGraphics.guiWidth();

        final var graphics = new GuiGraphicsWrapper(guiGraphics);

        graphics.push();
        graphics.enableCrop(screenWidth/64, screenHeight/32);

        // background stars
        graphics.push();
        graphics.translateXY(screenWidth*0.5f, screenHeight*0.5f);
        graphics.drawTexture(
            STARS,
            (int) getBGSize(screenWidth)/-2,
            (int) getBGSize(screenWidth)/-2,
            0,
            (float) scrollX,
            (float) scrollY,
            (int) getBGSize(screenWidth),
            (int) getBGSize(screenWidth),
            (int) getBGSize(screenWidth)/8,
            (int) getBGSize(screenWidth)/8
        );
        // research nodes
        new Node(this, 0,0).render(guiGraphics, mouseX, mouseY, tickDelta);
        new Node(this, 1,2).render(guiGraphics, mouseX, mouseY, tickDelta);
        new Node(this, -2,3).render(guiGraphics, mouseX, mouseY, tickDelta);
        new Node(this, -3,-3).render(guiGraphics, mouseX, mouseY, tickDelta);
        new Node(this, -6,-3).render(guiGraphics, mouseX, mouseY, tickDelta);
        graphics.pop();

        graphics.disableCrop();
        graphics.pop();
    }
}

class Node implements Renderable{
    private static final ResourceLocation NODE = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/node.png");
    private final Tab tab;
    private final int x;
    private final int y;

    public Node(Tab tab, int x, int y) {
        this.tab = tab;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);
        int screenWidth = guiGraphics.guiWidth();
        var size = tab.getTileSize(screenWidth);
        var scrollX = tab.scrollX;
        var scrollY = tab.scrollY;

        graphics.push();
        graphics.translateXY((float) (-size/2), (float) (-size/2));
        graphics.drawSimpleTexture(
            NODE,
            (int) (size*x-scrollX),
            (int) (size*y-scrollY),
            (int) size,
            (int) size
        );
        graphics.pop();
    }
}
