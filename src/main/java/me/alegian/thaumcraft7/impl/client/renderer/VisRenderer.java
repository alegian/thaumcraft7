package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  public static Vector3f a = new Vector3f(0.5f, -58.5f, 0.5f);
  public static Vector3f b = new Vector3f(8.5f, -58.5f, 0.5f);
  public static int N = 100;
  public static Vector3f dx = b.sub(a).div(N);

  public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera) {
    poseStack.pushPose();
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.DEBUG_TRIANGLE_STRIP);
    poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

    for (int i = 0; i <= N; i++) {
      renderVertex(vc, poseStack, offsetPerpendicular(add(a, offsetSpiral(mul(dx, i), i)), i));
      renderVertex(vc, poseStack, offsetPerpendicular(add(a, offsetSpiral(mul(dx, i), i)), -i));
    }

    poseStack.popPose();
  }

  private static Vector3f offsetSpiral(Vector3f v1, int i) {
    Vector3f offset = calculateSpiralOffset(i);
    return new Vector3f(v1).add(offset);
  }

  private static Vector3f calculateSpiralOffset(int i) {
    float offset = (float) Math.sin((double) i / N * Math.PI * 2);
    return new Vector3f(0, offset, offset);
  }

  private static Vector3f offsetPerpendicular(Vector3f v1, int i) {
    float offset = calculateOffsetY(i);
    return new Vector3f(v1).add(0, offset, 0);
  }

  private static float calculateOffsetY(int i) {
    return (float) Math.sin((double) i / N * Math.PI)/4;
  }

  private static Vector3f add(Vector3f v1, Vector3f v2) {
    return new Vector3f(v1).add(v2);
  }

  private static Vector3f mul(Vector3f v1, float scalar) {
    return new Vector3f(v1).mul(scalar);
  }

  public static void renderVertex(
      VertexConsumer vc,
      PoseStack pose,
      Vector3f position
  ) {
    vc.addVertex(pose.last(), position)
        .setColor(0xFFFFFFFF)
        .setLight(LightTexture.FULL_BRIGHT);
  }
}
