package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class TestaItem extends Item {
  private final Supplier<Aspect> aspect;

  public TestaItem(Supplier<Aspect> aspect) {
    super(new Properties());
    this.aspect = aspect;
  }

  public Aspect getAspect() {
    return aspect.get();
  }
}
