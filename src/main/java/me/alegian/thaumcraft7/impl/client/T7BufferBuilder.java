package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
import org.jetbrains.annotations.NotNull;

public class T7BufferBuilder {
  private Vec3 currentPosition;
  private BufferBuilder parent;

  public T7BufferBuilder(VertexConsumer parent) {
    if(!(parent instanceof BufferBuilder)) throw new IllegalArgumentException("Thaumcraft Exception: parent must be a BufferBuilder");
    this.parent = (BufferBuilder) parent;
  }

  public @NotNull T7BufferBuilder setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
    return (T7BufferBuilder) parent.setColor(pRed, pGreen, pBlue, pAlpha);
  }

  public @NotNull T7BufferBuilder addVertex(PoseStack.@NotNull Pose pPose, float pX, float pY, float pZ) {
    currentPosition = new Vec3(pX, pY, pZ);
    return (T7BufferBuilder) parent.addVertex(pPose, pX, pY, pZ);
  }

  // assumes our pose is centered at the block center
  public T7BufferBuilder setRadius() {
    float radius = (float) currentPosition.length();
    return (T7BufferBuilder) misc(T7VertexFormats.RADIUS, Float.floatToIntBits(radius));
  }
}
