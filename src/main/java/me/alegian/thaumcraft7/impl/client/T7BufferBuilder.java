package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class T7BufferBuilder {
  private final BufferBuilder parent;
  private PoseStack.Pose pose;

  public T7BufferBuilder(VertexConsumer parent) {
    if (!(parent instanceof BufferBuilder))
      throw new IllegalArgumentException("Thaumcraft Exception: parent must be a BufferBuilder");
    this.parent = (BufferBuilder) parent;
  }

  public @NotNull T7BufferBuilder setColor(float pRed, float pGreen, float pBlue, float pAlpha) {
    parent.setColor(pRed, pGreen, pBlue, pAlpha);
    return this;
  }

  public @NotNull T7BufferBuilder addVertex(PoseStack.Pose pPose, float pX, float pY, float pZ) {
    this.pose = pPose;
    parent.addVertex(pPose, pX, pY, pZ);
    return this;
  }

  public T7BufferBuilder setCenter() {
    long i = parent.beginElement(T7VertexFormats.CENTER);
    var center = pose.pose().transformPosition(0, 0, 0, new Vector3f());
    if (i != -1L) {
      MemoryUtil.memPutFloat(i, center.x());
      MemoryUtil.memPutFloat(i + 4L, center.y());
      MemoryUtil.memPutFloat(i + 8L, center.z());
    }

    return this;
  }
}
