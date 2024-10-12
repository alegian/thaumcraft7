package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

/**
 * PoseStack Wrapper to make Renderer code more concise
 */
@OnlyIn(Dist.CLIENT)
public class T7PoseStack {
  PoseStack poseStack;

  public T7PoseStack(PoseStack poseStack) {
    this.poseStack = poseStack;
  }

  public T7PoseStack() {
    this.poseStack = new PoseStack();
  }

  public void push() {
    poseStack.pushPose();
  }

  public void pop() {
    poseStack.popPose();
  }

  public void translate(double x, double y, double z) {
    poseStack.translate(x, y, z);
  }

  public void translateY(double y) {
    poseStack.translate(0, y, 0);
  }

  public void translate(Vector3f offset) {
    translate(offset.x, offset.y, offset.z);
  }

  public void translate(Vec3 offset) {
    translate(offset.toVector3f());
  }

  public void translateNegative(Vector3f offset) {
    translate(new Vector3f(offset).negate());
  }

  public void translateNegative(Vec3 offset) {
    translateNegative(offset.toVector3f());
  }

  public void rotate(Axis axis, double angleRadians) {
    poseStack.mulPose(axis.rotation((float) angleRadians));
  }

  public PoseStack.Pose pose() {
    return poseStack.last();
  }

  public PoseStack poseStack() {
    return poseStack;
  }

  public Vector3f transformOrigin() {
    return poseStack.last().pose().transformPosition(new Vector3f());
  }

  public Vector3f transformPosition(Vector3f position) {
    return poseStack.last().pose().transformPosition(position);
  }

  public Vector3f transformPosition(Vec3 position) {
    return transformPosition(position.toVector3f());
  }

  public void translateCamera() {
    translate(getCameraPosition());
  }

  public void translateNegativeCamera() {
    translateNegative(getCameraPosition());
  }

  public void scale(float scale) {
    poseStack.scale(scale, scale, scale);
  }

  private Vec3 getCameraPosition() {
    return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
  }
}
