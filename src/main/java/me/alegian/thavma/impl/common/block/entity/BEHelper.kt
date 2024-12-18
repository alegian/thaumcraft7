package me.alegian.thavma.impl.common.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

fun updateServerBlockEntity(level: Level, blockPos: BlockPos) {
    level.getBlockEntity(blockPos)?.let { be ->
        be.setChanged()
        level.sendBlockUpdated(blockPos, be.blockState, be.blockState, Block.UPDATE_CLIENTS)
    }
}

