package me.alegian.thavma.impl.common.block

import me.alegian.thavma.impl.common.block.entity.PillarBE
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.PushReaction

class PillarBlock : Block(Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.BLOCK)),
    EntityBlock {
    init {
        this.registerDefaultState(
            stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
        )
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        val stateForPlacement =
            defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.horizontalDirection)
        val pos = context.clickedPos
        val facing = stateForPlacement.getValue(BlockStateProperties.HORIZONTAL_FACING)

        if (!context.level.getBlockState(pos.relative(facing)).canBeReplaced()) return null
        if (!context.level.getBlockState(pos.relative(facing).above()).canBeReplaced()) return null
        return stateForPlacement
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.ENTITYBLOCK_ANIMATED
    }

    override fun newBlockEntity(pos: BlockPos, blockState: BlockState): BlockEntity {
        return PillarBE(pos, blockState)
    }
}
