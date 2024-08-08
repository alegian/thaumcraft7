package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  public static Vector3f a = new Vector3f(0, -59, 0);
  public static Vector3f b = new Vector3f(8, -59, 0);
  public static int N = 100;
  public static Vector3f dx = b.sub(a).div(N);

  public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera) {
    poseStack.pushPose();
    poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

    for (int i = 0; i < N; i++) {
      renderQuad(bufferSource, poseStack.last(),
          offsetY(add(a, offset(mul(dx, i), i)), 0),
          offsetY(add(a, offset(mul(dx, i + 1), i+1)), 0),
          offsetY(add(a, offset(mul(dx, i + 1), i+1)), 1),
          offsetY(add(a, offset(mul(dx, i), i)), 1)
      );
    }

    poseStack.popPose();
  }

  private static Vector3f offset(Vector3f v1, int i) {
    Vector3f offset = calculateOffset(i);
    return new Vector3f(v1).add(offset);
  }

  private static Vector3f calculateOffset(int i) {
    float Y = (float) Math.sin((double) i / N * Math.PI * 2);
    return new Vector3f(0, Y, 0);
  }

  private static Vector3f offsetY(Vector3f v1, float offset) {
    return new Vector3f(v1).add(0, offset, 0);
  }

  private static Vector3f add(Vector3f v1, Vector3f v2) {
    return new Vector3f(v1).add(v2);
  }

  private static Vector3f mul(Vector3f v1, float scalar) {
    return new Vector3f(v1).mul(scalar);
  }

  public static void renderQuad(MultiBufferSource bufferSource, PoseStack.Pose pose, Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4) {
    VertexConsumer vc = bufferSource.getBuffer(RenderType.DEBUG_QUADS);

    renderVertex(vc, pose, v1);
    renderVertex(vc, pose, v2);
    renderVertex(vc, pose, v3);
    renderVertex(vc, pose, v4);
  }

  public static void renderVertex(
      VertexConsumer vc,
      PoseStack.Pose pose,
      Vector3f position
  ) {
    vc.addVertex(pose, position)
        .setColor(0xFFFFFFFF)
        .setLight(LightTexture.FULL_BRIGHT);
  }
}
