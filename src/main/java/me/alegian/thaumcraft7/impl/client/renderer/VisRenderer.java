package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  public static Vector3f a = new Vector3f(0.5f, -58.5f, 0.5f);
  public static Vector3f b = new Vector3f(4.5f, -58.5f, 0.5f);
  public static int N = 1000;
  public static Vector3f dx = b.sub(a).div(N);
  public static double angle = 2 * Math.PI / N;

  public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera) {
    poseStack.pushPose();
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.DEBUG_TRIANGLE_STRIP);
    poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
    poseStack.translate(a.x, a.y, a.z);

    for (int i = 0; i <= N; i++) {
      float thicknessOffset = (float) Math.sin(angle / 2 * i) / 8;

      poseStack.pushPose();
      poseStack.translate(0, thicknessOffset, 0);
      renderVertex(vc, poseStack);
      poseStack.popPose();

      poseStack.pushPose();
      poseStack.translate(0, -thicknessOffset, 0);
      renderVertex(vc, poseStack);
      poseStack.popPose();

      poseStack.translate(dx.x, dx.y, dx.z);
      poseStack.translate(0, 0, Math.sin(angle) / 4f);
      poseStack.mulPose(Axis.XP.rotation((float) angle));
    }

    poseStack.popPose();
  }

  public static void renderVertex(
      VertexConsumer vc,
      PoseStack pose
  ) {
    vc.addVertex(pose.last(), 0, 0, 0)
        .setColor(0x5555FF88)
        .setLight(LightTexture.FULL_BRIGHT);
  }
}
