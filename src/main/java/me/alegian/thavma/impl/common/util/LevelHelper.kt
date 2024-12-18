package me.alegian.thavma.impl.common.util

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

fun <T : BlockEntity?> getSafeBE(
    level: Level,
    blockPos: BlockPos,
    blockEntityType: BlockEntityType<T>
): T? {
    val block = level.getBlockState(blockPos).block

    if (blockEntityType.validBlocks.contains(block)) return level.getBlockEntity(blockPos, blockEntityType)
        .orElse(null)
    return null
}
