package me.alegian.thavma.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thavma.impl.client.T7BufferBuilder;
import me.alegian.thavma.impl.client.T7RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BERHelper {
  public static void renderAuraNodeLayer(PoseStack poseStack, MultiBufferSource bufferSource, float radius, float r, float g, float b, float a) {
    int triangleResolution = 256;
    T7BufferBuilder buffer = new T7BufferBuilder(bufferSource.getBuffer(T7RenderTypes.AURA_NODE));

    double baseAngle = Math.PI * 2 / triangleResolution;
    for (int i = 0; i < triangleResolution; i++)
      BERHelper.nodeTriangle(poseStack, radius, baseAngle, i, r, g, b, a, buffer);
  }

  private static void nodeTriangle(PoseStack poseStack, float radius, double baseAngle, int index, float r, float g, float b, float a, T7BufferBuilder buffer) {
    BERHelper.nodeVertex(poseStack, 0, 0, r, g, b, a, buffer, (float) baseAngle * index);
    BERHelper.nodeVertex(poseStack, (float) (Math.cos(baseAngle * index) * radius), (float) (Math.sin(baseAngle * index) * radius), r, g, b, a, buffer, (float) baseAngle * index);
    BERHelper.nodeVertex(poseStack, (float) (Math.cos(baseAngle * (index + 1)) * radius), (float) (Math.sin(baseAngle * (index + 1)) * radius), r, g, b, a, buffer, (float) baseAngle * (index + 1));
  }

  private static void nodeVertex(PoseStack poseStack, float x, float y, float r, float g, float b, float a, T7BufferBuilder buffer, float angle) {
    buffer.addVertex(poseStack.last(), x, y, 0).setColor(r, g, b, a).setCenter().setAngle(angle);
  }
}
