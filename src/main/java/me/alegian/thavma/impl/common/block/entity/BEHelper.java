package me.alegian.thavma.impl.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BEHelper {
  public static void updateServerBlockEntity(Level level, BlockPos blockPos) {
    var be = level.getBlockEntity(blockPos);
    if (be != null) {
      be.setChanged();
      level.sendBlockUpdated(blockPos, be.getBlockState(), be.getBlockState(), Block.UPDATE_CLIENTS);
    }
  }
}
