package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.MemoryUtil;

public class T7BufferBuilder {
  private Vec3 currentPosition;
  private final BufferBuilder parent;

  public T7BufferBuilder(VertexConsumer parent) {
    if (!(parent instanceof BufferBuilder))
      throw new IllegalArgumentException("Thaumcraft Exception: parent must be a BufferBuilder");
    this.parent = (BufferBuilder) parent;
  }

  public @NotNull T7BufferBuilder setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
    parent.setColor(pRed, pGreen, pBlue, pAlpha);
    return this;
  }

  public @NotNull T7BufferBuilder addVertex(PoseStack.@NotNull Pose pPose, float pX, float pY, float pZ) {
    currentPosition = new Vec3(pX, pY, pZ);
    parent.addVertex(pPose, pX, pY, pZ);
    return this;
  }

  // assumes our pose is centered at the block center
  public T7BufferBuilder setRadius() {
    float radius = (float) currentPosition.length();
    long i = parent.beginElement(T7VertexFormats.RADIUS);
    if (i != -1L) {
      MemoryUtil.memPutFloat(i, radius);
    }

    return this;
  }
}
