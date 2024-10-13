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
    push(0, 0);
  }

  public void push(int x, int y) {
    poseX.push(x);
    poseY.push(y);
  }

  public void pushX() {
    poseX.push(poseX.peek());
  }

  public void translateX(int x) {
    poseX.push(poseX.pop() + x);
  }

  public void translateY(int y) {
    poseY.push(poseY.pop() + y);
  }

  public void popX() {
    poseX.pop();
  }

  public void popY() {
    poseY.pop();
  }

  public void pop() {
    poseX.pop();
    poseY.pop();
  }

  public int getX() {
    return poseX.peek();
  }

  public int getY() {
    return poseY.peek();
  }
}
