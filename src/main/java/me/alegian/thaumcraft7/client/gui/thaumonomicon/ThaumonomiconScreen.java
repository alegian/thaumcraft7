package me.alegian.thaumcraft7.client.gui.thaumonomicon;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.client.gui.GuiGraphicsWrapper;
import me.alegian.thaumcraft7.client.gui.thaumonomicon.grid.Grid;
import me.alegian.thaumcraft7.client.gui.thaumonomicon.grid.GridRenderable;
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
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else {
                tab.handleScroll((float) dragX, (float) dragY);
            }

            return true;
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        tab.zoom((float) scrollY);
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
    private static final float ZOOM_MULTIPLIER = 1.25F;
    private final Grid grid = new Grid(48);
    private static final ResourceLocation STARS = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/stars_layer1.png");

    public Tab(float maxScrollX, float maxScrollY) {
        this.maxScrollX = maxScrollX;
        this.maxScrollY = maxScrollY;
        // test research nodes
        grid.addCell(new Node(0,0));
        grid.addCell(new ArrowCorner3x3(1, -1, false, 90));
        grid.addCell(new ArrowHead(2, -1, 0));
        grid.addCell(new Node(2,-2));
        grid.addCell(new ArrowCorner1x1(3, -2, false, 0));
        grid.addCell(new ArrowHead(3, -2, 0));
        grid.addCell(new Node(3,-3));
    }

    public void handleScroll(double x, double y){
        scrollTo(scrollX-Math.pow(ZOOM_MULTIPLIER, zoom)*x, scrollY-Math.pow(ZOOM_MULTIPLIER, zoom)*y);
    }

    public void scrollTo(double x, double y){
        scrollX = Mth.clamp(x, -1*maxScrollX, maxScrollX);
        scrollY = Mth.clamp(y, -1*maxScrollY, maxScrollY);
    }

    public void zoom(float y){
        zoom = Mth.clamp(zoom-y, 0, 5);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        final var graphics = new GuiGraphicsWrapper(guiGraphics);
        graphics.push();

        int screenHeight = guiGraphics.guiHeight();
        int screenWidth = guiGraphics.guiWidth();

        graphics.enableCrop(screenWidth/128, screenHeight/64);

        // background stars
        graphics.translateXY(screenWidth*0.5f, screenHeight*0.5f);
        graphics.scaleXY((float) Math.pow(ZOOM_MULTIPLIER, -zoom));
        graphics.drawTexture(
            STARS,
            -3840,
            -2160,
            0,
            (float) scrollX,
            (float) scrollY,
            3840*2,
            2160*2,
            512,
            512
        );

        // contains research nodes and their connections
        grid.render(guiGraphics, mouseX, mouseY, tickDelta, scrollX, scrollY);

        graphics.disableCrop();
        graphics.pop();
    }
}

class Node extends GridRenderable {
    private static final ResourceLocation NODE = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/node.png");

    public Node(int x, int y) {
        super(NODE, x, y);
    }
}

class ArrowHead extends GridRenderable {
    private static final ResourceLocation ARROW_HEAD = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/arrow_head.png");

    public ArrowHead(int x, int y, int rotationDegrees) {
        super(ARROW_HEAD, x, y, rotationDegrees);
    }
}

class ArrowCorner1x1 extends GridRenderable {
    private static final ResourceLocation CORNER = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/corner1x1.png");

    public ArrowCorner1x1(int x, int y, boolean flip, int rotationDegrees) {
        super(CORNER, x, y, flip, rotationDegrees);
    }
}

class ArrowCorner3x3 extends GridRenderable {
    private static final ResourceLocation CORNER = new ResourceLocation(Thaumcraft.MODID, "textures/gui/thaumonomicon/corner3x3.png");

    public ArrowCorner3x3(int x, int y, boolean flip, int rotationDegrees) {
        super(CORNER, x, y, 3, 3, flip, rotationDegrees);
    }
}
