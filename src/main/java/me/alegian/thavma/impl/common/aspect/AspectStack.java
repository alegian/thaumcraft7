package me.alegian.thavma.impl.common.aspect;

/**
 * Used when converting AspectMaps to lists.
 * The element of these lists is the AspectStack.
 */
public record AspectStack(Aspect aspect, int amount) {
  public static final AspectStack EMPTY = new AspectStack(null, 0);

  public static AspectStack of(Aspect aspect, int amount) {
    return new AspectStack(aspect, amount);
  }

  public boolean isEmpty() {
    return this == AspectStack.EMPTY || this.amount == 0 || this.aspect == null;
  }
}
