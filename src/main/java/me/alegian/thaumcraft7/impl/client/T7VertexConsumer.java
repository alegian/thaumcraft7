package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
import org.jetbrains.annotations.NotNull;

public class T7VertexConsumer extends VertexConsumerWrapper {
  private Vec3 currentPosition;

  public T7VertexConsumer(VertexConsumer parent) {
    super(parent);
  }

  @Override
  public @NotNull T7VertexConsumer setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
    return (T7VertexConsumer) super.setColor(pRed, pGreen, pBlue, pAlpha);
  }

  @Override
  public @NotNull T7VertexConsumer addVertex(PoseStack.@NotNull Pose pPose, float pX, float pY, float pZ) {
    currentPosition = new Vec3(pX, pY, pZ);
    return (T7VertexConsumer) super.addVertex(pPose, pX, pY, pZ);
  }

  // assumes our pose is centered at the block center
  public T7VertexConsumer setRadius() {
    float radius = (float) currentPosition.length();
    return (T7VertexConsumer) misc(T7VertexFormats.RADIUS, Float.floatToIntBits(radius));
  }
}
