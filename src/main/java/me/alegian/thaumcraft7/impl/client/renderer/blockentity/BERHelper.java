package me.alegian.thaumcraft7.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.impl.client.T7BufferBuilder;
import me.alegian.thaumcraft7.impl.client.T7RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BERHelper {
  public static void renderAuraNodeLayer(PoseStack poseStack, MultiBufferSource bufferSource, float radius, int triangleResolution, float r, float g, float b, float a) {
    T7BufferBuilder buffer = new T7BufferBuilder(bufferSource.getBuffer(T7RenderTypes.AURA_NODE));

    buffer.addVertex(poseStack.last(), -radius, -radius, 0).setColor(r, g, b, a).setCenter();
    buffer.addVertex(poseStack.last(), radius, -radius, 0).setColor(r, g, b, a).setCenter();
    buffer.addVertex(poseStack.last(), radius, radius, 0).setColor(r, g, b, a).setCenter();
    buffer.addVertex(poseStack.last(), -radius, radius, 0).setColor(r, g, b, a).setCenter();
  }
}
