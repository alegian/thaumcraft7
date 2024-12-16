package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.common.aspect.Aspect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class InfusedBlock extends Block {
  private final Supplier<Aspect> aspect;
  private final Supplier<Block> baseBlock;

  public InfusedBlock(@NotNull Supplier<Aspect> aspect, BlockBehaviour.Properties properties, Supplier<Block> baseBlock) {
    super(properties);
    this.aspect = aspect;
    this.baseBlock = baseBlock;
  }

  public Aspect getAspect() {
    return this.aspect.get();
  }

  public Block getBaseBlock() {
    return this.baseBlock.get();
  }
}
