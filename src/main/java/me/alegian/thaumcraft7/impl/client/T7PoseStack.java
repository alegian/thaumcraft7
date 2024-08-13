package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class T7PoseStack {
  PoseStack poseStack;

  public T7PoseStack(PoseStack poseStack) {
    this.poseStack = poseStack;
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

  public PoseStack.Pose pose(){
    return poseStack.last();
  }
}
