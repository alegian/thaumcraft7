package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.aspect.AspectStack;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.T7GuiGraphics;
import me.alegian.thaumcraft7.impl.client.T7RenderStateShards;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector2f;

@OnlyIn(Dist.CLIENT)
public class AspectRenderer {
  public static final int ROW_SIZE = 5;
  public static final float QUAD_SIZE = .3f; // aspect width is .3 blocks
  private static final int PIXEL_RESOLUTION = 16; // pixels per block

  /**
   * Renders the Aspect contents of an AspectContainer, using GuiGraphics.<br>
   * GuiGraphics assumes integer pixel coordinates, so we multiply everything by 16 (so a block is 16x16).
   */
  public static void renderAfterWeather(AspectList aspects, PoseStack poseStack, Camera camera, BlockPos blockPos) {
    if (aspects.isEmpty()) return;

    poseStack.pushPose();
    var cameraPos = camera.getPosition();
    poseStack.translate(blockPos.getX() - cameraPos.x() + 0.5d, blockPos.getY() - cameraPos.y() + 1.25d + QUAD_SIZE / 2, blockPos.getZ() - cameraPos.z() + 0.5d);
    var angle = RenderHelper.calculatePlayerAngle(blockPos.getCenter());
    poseStack.mulPose(Axis.YP.rotation(angle));
    poseStack.scale(QUAD_SIZE, QUAD_SIZE, 1); // this puts us in "aspect space" where 1 means 1 aspect width

    // these offsets account for wrapping to new lines, and centering the aspects
    Vector2f[] offsets = calculateOffsets(aspects.size());
    int i = 0;
    for (AspectStack aspectStack : aspects.displayedAspects()) {
      poseStack.pushPose();
      poseStack.translate(offsets[i].x, offsets[i].y, 0);

      // gui rendering is done in pixel space
      poseStack.scale(1f / PIXEL_RESOLUTION, -1f / PIXEL_RESOLUTION, 1f);
      T7GuiGraphics guiGraphics = new T7GuiGraphics(Minecraft.getInstance(), poseStack, Minecraft.getInstance().renderBuffers().bufferSource());
      renderAspect(guiGraphics, aspectStack, -PIXEL_RESOLUTION / 2, -PIXEL_RESOLUTION / 2);

      poseStack.popPose();
      i++;
    }

    poseStack.popPose();
  }

  public static void renderAspect(T7GuiGraphics guiGraphics, AspectStack aspectStack, int pX, int pY) {
    blitAspectIcon(guiGraphics, aspectStack.aspect(), pX, pY);
    drawText(guiGraphics, String.valueOf(aspectStack.amount()), pX, pY);
  }

  public static void blitAspectIcon(T7GuiGraphics guiGraphics, Aspect aspect, int pX, int pY) {
    var sprite = AspectAtlas.sprite(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, aspect.getId()));

    var color = aspect.getColor();
    guiGraphics.blit(
        pX,
        pY,
        0,
        PIXEL_RESOLUTION,
        PIXEL_RESOLUTION,
        sprite,
        color,
        T7RenderStateShards.ASPECT_SHADER
    );
  }

  public static Vector2f[] calculateOffsets(int numAspects) {
    Vector2f[] offsets = new Vector2f[numAspects];

    int rows = (int) Math.ceil(1f * numAspects / ROW_SIZE);
    for (int i = 0; i < rows; i++) {
      int cols = Math.min(numAspects - (i * ROW_SIZE), ROW_SIZE);
      for (int j = 0; j < cols; j++) {
        float xOffset = (cols - 1) / -2f + j;
        offsets[i * ROW_SIZE + j] = new Vector2f(xOffset, i);
      }
    }

    return offsets;
  }

  public static void drawText(GuiGraphics guiGraphics, String text, int pX, int pY) {
    var poseStack = guiGraphics.pose();
    poseStack.pushPose();
    poseStack.translate(pX + PIXEL_RESOLUTION, pY + PIXEL_RESOLUTION, 0.0001f); // start bottom right, like item count. slightly increase Z to avoid z fighting
    poseStack.scale(0.5F, 0.5F, 1F);
    Font font = Minecraft.getInstance().font;

    guiGraphics.drawString(font, text, -font.width(text), -font.lineHeight, 0xFFFFFFFF);
    poseStack.popPose();
  }

  public static int getPixelResolution() {
    return PIXEL_RESOLUTION;
  }
}
