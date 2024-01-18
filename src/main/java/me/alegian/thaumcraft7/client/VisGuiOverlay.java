package me.alegian.thaumcraft7.client;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.aspects.Aspect;
import me.alegian.thaumcraft7.api.aspects.AspectList;
import me.alegian.thaumcraft7.api.capabilities.VisStorageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

public class VisGuiOverlay {
    private static final ResourceLocation DISK = new ResourceLocation(Thaumcraft.MODID, "textures/gui/overlay/disk.png");
    private static final ResourceLocation VIAL = new ResourceLocation(Thaumcraft.MODID, "textures/gui/overlay/vial.png");
    private static final ResourceLocation VIAL_CONTENT = new ResourceLocation(Thaumcraft.MODID, "textures/overlay/vial_content.png");

    public static boolean visible = false;
    public static AspectList vis;

    public static final IGuiOverlay VIS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if(visible && vis!=null){
            float scale = 0.12f;
            float diskSize = (screenHeight*scale);
            float vialSize = 0.7f*diskSize;

            guiGraphics.pose().pushPose();

            // draw the disk
            guiGraphics.pose().mulPoseMatrix(translationMatrix(screenHeight*0.02f, screenHeight*0.02f));
            guiGraphics.setColor(1,1,1,1);
            guiGraphics.blit(DISK, 0, 0, 0, 0, (int)diskSize, (int)diskSize, (int)diskSize, (int)diskSize);

            // draw the vials
            guiGraphics.pose().mulPoseMatrix(translationMatrix(diskSize/2, diskSize/2));
            guiGraphics.pose().mulPoseMatrix(rotationMatrix(15));
            float ar = (float) 0.35;

            for(Aspect a : vis.aspects.keySet()){
                var color = a.getColorRGB();
                guiGraphics.setColor((float) color[0] /255, (float) color[1] /255, (float) color[2] /255,1);
                guiGraphics.blit(VIAL_CONTENT, (int)(-1*vialSize*ar/2), (int) (diskSize/2), 0, 0, (int)(vialSize*ar), (int)vialSize*vis.aspects.get(a)/100, (int)(vialSize*ar), (int)vialSize);
                guiGraphics.setColor(1,1,1,1);
                guiGraphics.blit(VIAL, (int)(-1*vialSize*ar/2), (int) (diskSize/2), 0, 0, (int)(vialSize*ar), (int)vialSize, (int)(vialSize*ar), (int)vialSize);
                guiGraphics.pose().mulPoseMatrix(rotationMatrix(-24));
            }

            guiGraphics.pose().popPose();
        }
    });

    public static void update(Player player){
        var visItem = VisStorageHelper.getVisItemInHand(player);
        if(visItem != null){
            var amount = VisStorageHelper.getVisStored(visItem) / VisStorageHelper.getMaxVisStored(visItem);
            visible = true;
            vis = new AspectList()
                    .add(Aspect.PERDITIO, (int) (100*amount))
                    .add(Aspect.ORDO, (int) (100*amount))
                    .add(Aspect.AQUA, (int) (100*amount))
                    .add(Aspect.IGNIS, (int) (100*amount))
                    .add(Aspect.TERRA, (int) (100*amount))
                    .add(Aspect.AER, (int) (100*amount));
        }else{
            visible = false;
        }
    }

    public static Matrix4f translationMatrix(float x, float y){
        return new Matrix4f().translate(x, y, 0);
    }

    public static Matrix4f rotationMatrix(float deg){
        return new Matrix4f().rotateZ((float) (deg/180 * Math.PI));
    }
}
