package me.alegian.thaumcraft7.impl.client.gui;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.VisStorageHelper;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;


public class VisGuiOverlay {
  private static final ResourceLocation DISK = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/gui/overlay/disk.png");
  private static final ResourceLocation VIAL = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/gui/overlay/vial.png");
  private static final ResourceLocation VIAL_CONTENT = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/gui/overlay/vial_content.png");

  public static boolean visible = false;
  public static AspectList vis;

  public static final LayeredDraw.Layer VIS_OVERLAY = ((guiGraphics, partialTick) -> {
    if (visible && vis != null) {
      float scale = 0.12f;
      int screenHeight = guiGraphics.guiHeight();
      float diskSize = (screenHeight * scale);
      float vialSize = 0.7f * diskSize;
      final var graphics = new GuiGraphicsWrapper(guiGraphics);

      guiGraphics.pose().pushPose();

      // draw the disk
      graphics.translateXY(screenHeight * 0.02f, screenHeight * 0.02f);
      guiGraphics.setColor(1, 1, 1, 1);
      guiGraphics.blit(DISK, 0, 0, 0, 0, (int) diskSize, (int) diskSize, (int) diskSize, (int) diskSize);

      // draw the vials
      graphics.translateXY(diskSize / 2, diskSize / 2);
      graphics.rotateZ(15);
      float ar = (float) 0.35;

      for (Aspect a : vis.map.keySet()) {
        var color = a.getColorRGB();
        guiGraphics.setColor((float) color[0] / 255, (float) color[1] / 255, (float) color[2] / 255, 1);
        guiGraphics.blit(VIAL_CONTENT, (int) (-1 * vialSize * ar / 2), (int) (diskSize / 2), 0, 0, (int) (vialSize * ar), (int) vialSize * vis.map.get(a) / 100, (int) (vialSize * ar), (int) vialSize);
        guiGraphics.setColor(1, 1, 1, 1);
        guiGraphics.blit(VIAL, (int) (-1 * vialSize * ar / 2), (int) (diskSize / 2), 0, 0, (int) (vialSize * ar), (int) vialSize, (int) (vialSize * ar), (int) vialSize);
        graphics.rotateZ(-24);
      }

      guiGraphics.pose().popPose();
    }
  });

  public static void update(Player player) {
    var visItem = VisStorageHelper.getVisItemInHand(player);
    if (visItem != null) {
      var amount = VisStorageHelper.getVisStored(visItem) / VisStorageHelper.getMaxVisStored(visItem);
      visible = true;
      vis = new AspectList()
          .add(Aspect.PERDITIO, (int) (100 * amount))
          .add(Aspect.ORDO, (int) (100 * amount))
          .add(Aspect.AQUA, (int) (100 * amount))
          .add(Aspect.IGNIS, (int) (100 * amount))
          .add(Aspect.TERRA, (int) (100 * amount))
          .add(Aspect.AER, (int) (100 * amount));
    } else {
      visible = false;
    }
  }
}
