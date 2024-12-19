package me.alegian.thavma.impl.common.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

fun Level.updateServerBlockEntity(blockPos: BlockPos) {
    getBlockEntity(blockPos)?.let { be ->
        be.setChanged()
        sendBlockUpdated(blockPos, be.blockState, be.blockState, Block.UPDATE_CLIENTS)
    }
}

