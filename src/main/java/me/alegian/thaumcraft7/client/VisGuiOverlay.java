package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.aspects.Aspect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

public class VisGuiOverlay {
    private static final ResourceLocation DISK = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/disk.png");
    private static final ResourceLocation VIAL = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/vial.png");
    private static final ResourceLocation VIAL_CONTENT = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/vial_content.png");

    public static final IGuiOverlay VIS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        float scale = 0.12f;
        float diskSize = (screenHeight*scale);
        float vialSize = 0.7f*diskSize;

        guiGraphics.pose().pushPose();

        // draw the disk
        guiGraphics.pose().mulPoseMatrix(translationMatrix(screenHeight*0.02f, screenHeight*0.02f));
        guiGraphics.blit(DISK, 0, 0, 0, 0, (int)diskSize, (int)diskSize, (int)diskSize, (int)diskSize);

        // draw the vials
        guiGraphics.pose().mulPoseMatrix(translationMatrix(diskSize/2, diskSize/2));
        guiGraphics.pose().mulPoseMatrix(rotationMatrix(15));
        float ar = (float) 0.35;
        var aspects = Aspect.PRIMAL_ASPECTS;
        for(Aspect a : aspects){
            guiGraphics.blit(VIAL_CONTENT, (int)(-1*vialSize*ar/2), (int) (diskSize/2), 0, 0, (int)(vialSize*ar), (int)vialSize/2, (int)(vialSize*ar), (int)vialSize);
            guiGraphics.blit(VIAL, (int)(-1*vialSize*ar/2), (int) (diskSize/2), 0, 0, (int)(vialSize*ar), (int)vialSize, (int)(vialSize*ar), (int)vialSize);
            guiGraphics.pose().mulPoseMatrix(rotationMatrix(-24));
        }

        guiGraphics.pose().popPose();
    });

    public static Matrix4f translationMatrix(float x, float y){
        return new Matrix4f().translate(x, y, 0);
    }

    public static Matrix4f rotationMatrix(float deg){
        return new Matrix4f().rotateZ((float) (deg/180 * Math.PI));
    }
}
