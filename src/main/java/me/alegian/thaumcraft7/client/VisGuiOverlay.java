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
        int width = (int)(screenHeight*scale);

        guiGraphics.pose().pushPose();

        var translationMatrix = new Matrix4f()
                .translate(screenHeight*0.02f, screenHeight*0.02f, 0);
        guiGraphics.pose().mulPoseMatrix(translationMatrix);

        guiGraphics.blit(DISK, 0, 0, 0, 0, width, width, width, width);

        guiGraphics.pose().mulPoseMatrix(rotationMatrix(15));
        guiGraphics.blit(VIAL, 0, 0, 0, 0, 24, 24, 96, 96);

        guiGraphics.pose().popPose();
    });

    public static Matrix4f rotationMatrix(int deg){
        return new Matrix4f().rotateZ(deg);
    }
}
