package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import net.minecraft.world.item.Item;

public class TestaItem extends Item {
  private final Aspect aspect;

  public TestaItem(Aspect aspect) {
    super(new Properties());
    this.aspect = aspect;
  }

  public Aspect getAspect() {
    return aspect;
  }
}
