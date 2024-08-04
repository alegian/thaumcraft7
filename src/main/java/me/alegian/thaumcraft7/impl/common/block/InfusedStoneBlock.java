package me.alegian.thaumcraft7.impl.common.block;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class InfusedStoneBlock extends Block {
  private final Aspect aspect;

  public InfusedStoneBlock(@NotNull Aspect aspect) {
    super(Properties.ofFullCopy(Blocks.IRON_ORE));
    this.aspect = aspect;
  }

  public Aspect getAspect() {
    return aspect;
  }
}
