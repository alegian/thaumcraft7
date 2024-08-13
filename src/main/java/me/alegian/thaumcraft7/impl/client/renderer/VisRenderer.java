package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.impl.client.T7PoseStack;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  // triangle resolution
  public static int N = 100;
  // delta angle
  public static double da = 2 * Math.PI / N;

  // assumes the pose is at player hand position
  public static void render(Vec3 blockPos, T7PoseStack t7pose, MultiBufferSource bufferSource, float partialTicks) {
    Vector3f a = new Vector3f();
    Vector3f b = relative(t7pose, blockPos);
    // delta vector (scaled b-a)
    Vector3f dx = b.sub(a).div(N);

    t7pose.push();
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.DEBUG_TRIANGLE_STRIP);
    // start at one end
    t7pose.translate(a);

    // some useful axes
    Axis mainAxis = Axis.of(dx);
    // the perpendicular axis, that is not in the direction of Y
    Vector3f zBasis = new Vector3f(dx).cross(new Vector3f(0, 1, 0)).normalize();

    double phase = (partialTicks / 20 / 4 * 2 * Math.PI) % 2 * Math.PI;

    // rotation phase to "animate" the spiral
    t7pose.rotate(mainAxis, phase);
    for (int i = 0; i <= N; i++) {
      double thicknessOffset = Math.sin(da / 2 * i) / 8;

      // the 2 vertices render with opposite offsets to give thickness
      t7pose.push();
      t7pose.translateY(thicknessOffset);
      renderVertex(vc, t7pose);
      t7pose.pop();

      t7pose.push();
      t7pose.translateY(-thicknessOffset);
      renderVertex(vc, t7pose);
      t7pose.pop();

      // move towards goal
      t7pose.translate(dx);
      // offset on the Z axis to create spiral
      Vector3f translation = new Vector3f(zBasis).mul((float) (Math.sin(da) / 4f));
      t7pose.translate(translation);
      // spiral vertices should be drawn rotated
      t7pose.rotate(mainAxis, da);
    }

    t7pose.pop();
  }

  public static void renderVertex(
      VertexConsumer vc,
      T7PoseStack t7pose
  ) {
    // keep the praecantatio color with some alpha
    vc.addVertex(t7pose.pose(), 0, 0, 0)
        .setColor(Aspect.PRAECANTATIO.getColor() & 0xFFFFFF | 0x88000000);
  }

  // the player hand position is given to us in the pose, which contains the camera position
  // so we have to temporarily undo it
  private static Vector3f relative(T7PoseStack t7pose, Vec3 absoluteB) {
    t7pose.push();
    t7pose.translateCamera();
    Vector3f absoluteA = t7pose.transformOrigin();
    t7pose.pop();
    return absoluteB.toVector3f().sub(absoluteA);
  }
}
