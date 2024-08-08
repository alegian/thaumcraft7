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
  public static final Vector3f a = new Vector3f(0, -60, 0);
  public static final Vector3f b = new Vector3f(8, -60, 0);

  public static void render(PoseStack poseStack, MultiBufferSource bufferSource, Camera camera) {
    poseStack.pushPose();
    poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

    renderQuad(bufferSource, poseStack.last(),
        a,
        b,
        (new Vector3f(b)).add(0, 1, 0),
        (new Vector3f(a)).add(0, 1, 0)
    );

    poseStack.popPose();
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
