package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector2f;

@OnlyIn(Dist.CLIENT)
public class AspectRenderer {
  public static final int ROW_SIZE = 5;
  public static final float QUAD_SIZE = .3f;
  public static AspectList aspects = new AspectList();

  static {
    aspects.add(Aspect.IGNIS, 4);
    aspects.add(Aspect.AER, 1);
    aspects.add(Aspect.MORTUUS, 2);
    aspects.add(Aspect.METALLUM, 4);
    aspects.add(Aspect.LUX, 7);
    aspects.add(Aspect.VINCULUM, 2);
  }

  public static void renderAfterWeather(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera, BlockPos blockPos) {
    poseStack.pushPose();
    var cameraPos = camera.getPosition();
    poseStack.translate(blockPos.getX() - cameraPos.x() + 0.5d, blockPos.getY() - cameraPos.y() + 1.25d + QUAD_SIZE / 2, blockPos.getZ() - cameraPos.z() + 0.5d);

    var angle = RenderHelper.calculatePlayerAngle(blockPos.getCenter());
    poseStack.mulPose(Axis.YP.rotation(angle));
    poseStack.scale(QUAD_SIZE, QUAD_SIZE, 1);

    Vector2f[] offsets = calculateOffsets();
    int i = 0;
    for (Aspect aspect : aspects.aspectSet()) {
      poseStack.pushPose();
      poseStack.translate(offsets[i].x, offsets[i].y, 0);

      renderQuad(bufferSource, poseStack.last(), aspect);
      renderText(bufferSource, poseStack, String.valueOf(aspects.get(aspect)));

      poseStack.popPose();
      i++;
    }

    poseStack.popPose();
  }

  public static Vector2f[] calculateOffsets() {
    Vector2f[] offsets = new Vector2f[aspects.size()];

    int rows = (int) Math.ceil(1f * aspects.size() / ROW_SIZE);
    for (int i = 0; i < rows; i++) {
      int cols = Math.min(aspects.size() - (i * ROW_SIZE), ROW_SIZE);
      for (int j = 0; j < cols; j++) {
        float xOffset = (cols - 1) / -2f + j;
        offsets[i * ROW_SIZE + j] = new Vector2f(xOffset, i);
      }
    }

    return offsets;
  }

  public static void renderText(MultiBufferSource bufferSource, PoseStack poseStack, String text) {
    poseStack.pushPose();
    poseStack.translate(.5f, -.5f, 0.0001f);
    poseStack.scale(0.036F, -0.036F, 0.1F);
    Font font = Minecraft.getInstance().font;

    font.drawInBatch(text, -font.width(text), -font.lineHeight, 0xFFFFFFFF, true, poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, LightTexture.FULL_BRIGHT, false);
    poseStack.popPose();
  }

  public static void renderQuad(MultiBufferSource bufferSource, PoseStack.Pose pose, Aspect aspect) {
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.ASPECT_QUAD);
    var sprite = AspectAtlas.sprite(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, aspect.getId()));
    float f1 = sprite.getU0();
    float f2 = sprite.getU1();
    float f3 = sprite.getV0();
    float f4 = sprite.getV1();
    var color = aspect.getColor();

    renderVertex(vc, pose, .5F, -.5F, 0, f2, f4, color);
    renderVertex(vc, pose, .5F, .5F, 0, f2, f3, color);
    renderVertex(vc, pose, -.5F, .5F, 0, f1, f3, color);
    renderVertex(vc, pose, -.5F, -.5F, 0, f1, f4, color);
  }

  public static void renderVertex(
      VertexConsumer vc,
      PoseStack.Pose pose,
      float x,
      float y,
      float z,
      float pU,
      float pV,
      int color
  ) {
    vc.addVertex(pose, x, y, z)
        .setUv(pU, pV)
        .setColor(color)
        .setLight(LightTexture.FULL_BRIGHT);
  }
}
