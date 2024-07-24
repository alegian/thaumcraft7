package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.client.TCRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;

public class BERHelper {
  public static void renderAuraNodeLayer(PoseStack poseStack, MultiBufferSource bufferSource, float radius, int triangleResolution, float r, float g, float b, float a) {
    VertexConsumer buffer = bufferSource.getBuffer(TCRenderTypes.SIMPLE_TRIANGLE);

    var angleDelta = 2 * Math.PI / triangleResolution;
    for (int i = 0; i < triangleResolution; i++) {
      buffer.addVertex(poseStack.last(), (float) Math.cos(angleDelta * (i + 1)) * radius, (float) Math.sin(angleDelta * (i + 1)) * radius, 0).setColor(r, g, b, a);
      buffer.addVertex(poseStack.last(), 0, 0, 0).setColor(r, g, b, a);
      buffer.addVertex(poseStack.last(), (float) Math.cos(angleDelta * i) * radius, (float) Math.sin(angleDelta * i) * radius, 0).setColor(r, g, b, a);
    }
  }
}
