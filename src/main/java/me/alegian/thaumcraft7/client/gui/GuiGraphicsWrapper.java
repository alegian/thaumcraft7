package me.alegian.thaumcraft7.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;


public class GuiGraphicsWrapper {
    private final GuiGraphics graphics;

    public GuiGraphicsWrapper(GuiGraphics graphics) {
        this.graphics = graphics;
    }

    // assumes PNG resource
    public void drawTexture(ResourceLocation resourceLocation, int screenLeftX, int screenTopY, int zIndex, float textureLeftX, float textureTopY, int drawWidth, int drawHeight, int unitWidth, int unitHeight){
        graphics.blit(
            resourceLocation,
            screenLeftX,
            screenTopY,
            zIndex,
            textureLeftX,
            textureTopY,
            drawWidth,
            drawHeight,
            unitWidth,
            unitHeight
        );
    }

    // assumes no z-index, no texture offset and no cropping
    public void drawSimpleTexture(ResourceLocation resourceLocation, int screenLeftX, int screenTopY, int drawWidth, int drawHeight){
        drawTexture(
            resourceLocation,
            screenLeftX,
            screenTopY,
            0,
            0,
            0,
            drawWidth,
            drawHeight,
            drawWidth,
            drawHeight
        );
    }

    public void enableCrop(int leftX, int topY){
        int screenHeight = graphics.guiHeight();
        int screenWidth = graphics.guiWidth();

        graphics.enableScissor(leftX, topY, screenWidth-leftX, screenHeight-topY);
    }

    public void disableCrop(){
        graphics.disableScissor();
    }

    public void translateXY(float x, float y){
        graphics.pose().mulPoseMatrix(new Matrix4f().translate(x, y, 0));
    }

    public void rotateZ(float deg){
        graphics.pose().mulPoseMatrix(new Matrix4f().rotateZ((float) (deg/180 * Math.PI)));
    }

    public void scaleXY(float scale){
        graphics.pose().mulPoseMatrix(new Matrix4f().scale(scale, scale, 1));
    }

    public void push(){
        graphics.pose().pushPose();
    }

    public void pop(){
        graphics.pose().popPose();
    }
}
