package me.alegian.thavma.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thavma.impl.client.T7BufferBuilder;
import me.alegian.thavma.impl.client.T7RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BERHelper {
  public static void renderAuraNodeLayer(PoseStack poseStack, MultiBufferSource bufferSource, float radius, int packedColor, float a, float depth) {
    T7BufferBuilder buffer = new T7BufferBuilder(bufferSource.getBuffer(T7RenderTypes.AURA_NODE));

    // circles inscribed in triangles are only half as small
    radius = radius * 2;

    float r = FastColor.ARGB32.red(packedColor) / 255f;
    float g = FastColor.ARGB32.green(packedColor) / 255f;
    float b = FastColor.ARGB32.blue(packedColor) / 255f;

    // render a single triangle. everything else is core shaders
    double baseAngle = 2 * Math.PI / 3;
    BERHelper.nodeVertex(poseStack, radius, 0, depth, r, g, b, a, buffer);
    BERHelper.nodeVertex(poseStack, (float) (Math.cos(baseAngle * 2) * radius), (float) (Math.sin(baseAngle * 2) * radius), depth, r, g, b, a, buffer);
    BERHelper.nodeVertex(poseStack, (float) (Math.cos(baseAngle) * radius), (float) (Math.sin(baseAngle) * radius), depth, r, g, b, a, buffer);
  }

  private static void nodeVertex(PoseStack poseStack, float x, float y, float depth, float r, float g, float b, float a, T7BufferBuilder buffer) {
    buffer.addVertex(poseStack.last(), x, y, depth).setColor(r, g, b, a).setCenter();
  }
}
