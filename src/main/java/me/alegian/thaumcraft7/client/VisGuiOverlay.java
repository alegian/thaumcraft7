package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

public class VisGuiOverlay {
    private static final ResourceLocation DISK = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/disk.png");
    private static final ResourceLocation VIAL = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/vial.png");

    public static final IGuiOverlay VIS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        dynamicBlit(guiGraphics, DISK, 0.5F, 05.F, 0.1F, 0.1F, 128, 128);
    });

    public static void dynamicBlit(GuiGraphics guiGraphics, ResourceLocation texture, float relativePositionX, float relativePositionY, float relativeWidth, float relativeHeight, int textureSizeX, int textureSizeY){
        var screenWidth = guiGraphics.guiWidth();
        var screenHeight = guiGraphics.guiHeight();

        guiGraphics.blit(texture, (int) (screenWidth*relativePositionX), (int) (screenHeight*relativePositionY), 0, 0, (int) (screenWidth*relativeWidth), (int) (screenHeight*relativeHeight), textureSizeX, textureSizeY);
    }
}
