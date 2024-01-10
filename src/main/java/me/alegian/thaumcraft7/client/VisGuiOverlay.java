package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

public class VisGuiOverlay {
    private static final ResourceLocation DISK = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/disk.png");
    private static final ResourceLocation VIAL = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/vial.png");

    public static final IGuiOverlay VIS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        float scale = 0.15f;
        float width = (screenHeight*scale);

        guiGraphics.pose().pushPose();

        guiGraphics.pose().mulPoseMatrix(translationMatrix(screenHeight*0.02f, screenHeight*0.02f));

        guiGraphics.blit(DISK, 0, 0, 0, 0, (int)width, (int)width, (int)width, (int)width);

        guiGraphics.pose().mulPoseMatrix(translationMatrix(width/2, width/2));
        guiGraphics.pose().mulPoseMatrix(rotationMatrix(15));

        float ar = (float) 24/96;
        guiGraphics.blit(VIAL, (int)(-1*width*ar/2), (int) (width/2), 0, 0, (int)(width*ar), (int)width, (int)(width*ar), (int)width);

        guiGraphics.pose().popPose();
    });

    public static Matrix4f translationMatrix(float x, float y){
        return new Matrix4f().translate(x, y, 0);
    }

    public static Matrix4f rotationMatrix(float deg){
        return new Matrix4f().rotateZ((float) (deg/180 * Math.PI));
    }
}
