package me.alegian.thaumcraft7.impl.common.menu.slot;

import java.util.Stack;

/**
 * Aims to be similar to vanilla Pose, but for slots.
 * Provides separate poseX and poseY stacks for utility.
 * For example, popping X is used for slot new-lines
 */
public class SlotPose {
  private Stack<Integer> poseX = new Stack<>();
  private Stack<Integer> poseY = new Stack<>();

  public SlotPose() {
    this.push(0, 0);
  }

  public void push(int x, int y) {
    this.poseX.push(x);
    this.poseY.push(y);
  }

  public void pushX() {
    this.poseX.push(this.poseX.peek());
  }

  public void translateX(int x) {
    this.poseX.push(this.poseX.pop() + x);
  }

  public void translateY(int y) {
    this.poseY.push(this.poseY.pop() + y);
  }

  public void popX() {
    this.poseX.pop();
  }

  public void popY() {
    this.poseY.pop();
  }

  public void pop() {
    this.poseX.pop();
    this.poseY.pop();
  }

  public int getX() {
    return this.poseX.peek();
  }

  public int getY() {
    return this.poseY.peek();
  }
}
