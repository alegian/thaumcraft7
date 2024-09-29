package me.alegian.thaumcraft7.impl.common.block;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class InfusedStoneBlock extends Block {
  private final Supplier<Aspect> aspect;

  public InfusedStoneBlock(@NotNull Supplier<Aspect> aspect) {
    super(Properties.ofFullCopy(Blocks.IRON_ORE));
    this.aspect = aspect;
  }

  public Aspect getAspect() {
    return aspect.get();
  }
}
