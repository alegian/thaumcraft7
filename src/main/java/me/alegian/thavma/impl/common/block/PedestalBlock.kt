package me.alegian.thavma.impl.common.block

import me.alegian.thavma.impl.common.block.entity.PedestalBE
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.PushReaction

class PedestalBlock : Block(Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.BLOCK)),
    EntityBlock {
    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun newBlockEntity(pos: BlockPos, blockState: BlockState): BlockEntity {
        return PedestalBE(pos, blockState)
    }
}
