package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  // start
  public static Vector3f a = new Vector3f(0.5f, -58.5f, 0.5f);
  // end
  public static Vector3f b = new Vector3f(0.5f, -58.5f, 4.5f);
  // triangle resolution
  public static int N = 100;
  // delta vector (scaled b-a)
  public static Vector3f dx = b.sub(a).div(N);
  // delta angle
  public static double da = 2 * Math.PI / N;

  public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera, int tick, DeltaTracker deltaTracker) {
    poseStack.pushPose();
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.DEBUG_TRIANGLE_STRIP);
    poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
    // start at one end
    poseStack.translate(a.x, a.y, a.z);

    // some useful axes
    Axis mainAxis = Axis.of(dx);
    // the perpendicular axis, that is not in the direction of Y
    Vector3f zBasis = new Vector3f(dx).cross(new Vector3f(0,1,0)).normalize();

    double partialTick = deltaTracker.getGameTimeDeltaPartialTick(true);
    double ticks = tick + partialTick;
    double phase = (ticks / 60 * 2 * Math.PI) % 2 * Math.PI;

    // rotation phase to "animate" the spiral
    poseStack.mulPose(mainAxis.rotation((float) (phase)));
    for (int i = 0; i <= N; i++) {
      float thicknessOffset = (float) Math.sin(da / 2 * i) / 8;

      // the 2 vertices render with opposite offsets to give thickness
      poseStack.pushPose();
      poseStack.translate(0, thicknessOffset, 0);
      renderVertex(vc, poseStack);
      poseStack.popPose();

      poseStack.pushPose();
      poseStack.translate(0, -thicknessOffset, 0);
      renderVertex(vc, poseStack);
      poseStack.popPose();

      // move towards goal
      poseStack.translate(dx.x, dx.y, dx.z);
      // offset on the Z axis to create spiral
      Vector3f translation = new Vector3f(zBasis).mul((float) (Math.sin(da) / 4f));
      poseStack.translate(translation.x, translation.y, translation.z);
      // spiral vertices should be drawn rotated
      poseStack.mulPose(mainAxis.rotation((float) (da)));
    }

    poseStack.popPose();
  }

  public static void renderVertex(
      VertexConsumer vc,
      PoseStack pose
  ) {
    vc.addVertex(pose.last(), 0, 0, 0)
        .setColor(0xFF55FF88)
        .setLight(LightTexture.FULL_BRIGHT);
  }
}
