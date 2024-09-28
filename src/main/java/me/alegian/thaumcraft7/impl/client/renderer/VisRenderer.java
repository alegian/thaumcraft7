package me.alegian.thaumcraft7.impl.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.impl.client.T7PoseStack;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

/**
 * The Renderer for the Vis flowing out of an Aura Node into a Wand.
 * It renders as a spiral, by slowly rotating its pose.
 */
@OnlyIn(Dist.CLIENT)
public class VisRenderer {
  // triangle resolution
  public static int N = 100;
  // delta angle: how fast we rotate the spiral
  public static double da = 2 * Math.PI / N;

  /**
   * Assumes the Pose is at player hand position, with no rotation.
   * BlockPos is the absolute world position of the center of the Aura Node.
   */
  public static void render(Vec3 blockPos, T7PoseStack t7pose, MultiBufferSource bufferSource, float partialTicks) {
    // delta vector (scaled b-a)
    Vector3f dv = relative(t7pose, blockPos).div(N);

    t7pose.push();
    VertexConsumer vc = bufferSource.getBuffer(T7RenderTypes.TRANSLUCENT_TRIANGLES);

    // we translate along this axis, and rotate around it
    Axis mainAxis = Axis.of(dv);
    // the vector that forms an orthogonal basis with the main axis and Y
    Vector3f zBasis = new Vector3f(dv).cross(new Vector3f(0, 1, 0)).normalize();

    // rotation phase to "animate" the spiral
    double phase = (partialTicks / 20 / 4 * 2 * Math.PI) % 2 * Math.PI;
    t7pose.rotate(mainAxis, phase);

    for (int i = 0; i <= N; i++) {
      // this goes from 0 to 1 and back to 0, giving thickness
      double thicknessYOffset = Math.sin(da / 2 * i) / 8;

      // the 2 vertices render with opposite offsets to give thickness
      t7pose.push();
      t7pose.translateY(thicknessYOffset);
      renderVertex(vc, t7pose);
      t7pose.pop();

      t7pose.push();
      t7pose.translateY(-thicknessYOffset);
      renderVertex(vc, t7pose);
      t7pose.pop();

      // move towards goal
      t7pose.translate(dv);
      // offset on the Z axis to create spiral
      Vector3f translation = new Vector3f(zBasis).mul((float) (Math.sin(da) / 4f));
      t7pose.translate(translation);
      // spiral vertices should be drawn rotated
      t7pose.rotate(mainAxis, da);
    }

    t7pose.pop();
  }

  /**
   * Assumes the Pose encodes the entire position of the vertex.
   * Color is always the praecantatio color with some alpha
   */
  public static void renderVertex(
      VertexConsumer vc,
      T7PoseStack t7pose
  ) {
    vc.addVertex(t7pose.pose(), 0, 0, 0)
        .setColor(Aspect.PRAECANTATIO.getColor() & 0xFFFFFF | 0x88000000);
  }

  /**
   * Calculates the position of B, relative to the position of A
   * encoded in the pose as translation.
   * The Pose also contains the camera position
   * so we have to temporarily undo it.
   */
  private static Vector3f relative(T7PoseStack t7pose, Vec3 absoluteB) {
    t7pose.push();
    t7pose.translateCamera();
    Vector3f absoluteA = t7pose.transformOrigin();
    t7pose.pop();
    return absoluteB.toVector3f().sub(absoluteA);
  }
}
