package me.alegian.thaumcraft7.impl.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

public class LevelHelper {
  public static <T extends BlockEntity> Optional<T> getSafeBE(Level level, BlockPos blockPos, BlockEntityType<T> blockEntityType) {
    var block = level.getBlockState(blockPos).getBlock();

    if(blockEntityType.getValidBlocks().contains(block)) return level.getBlockEntity(blockPos, blockEntityType);
    return Optional.empty();
  }
}
