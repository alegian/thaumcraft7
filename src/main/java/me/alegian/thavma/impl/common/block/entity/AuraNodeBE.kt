package me.alegian.thavma.impl.common.block.entity

import me.alegian.thavma.impl.common.aspect.AspectMap
import me.alegian.thavma.impl.common.data.capability.AspectContainer
import me.alegian.thavma.impl.common.util.updateBlockEntityS2C
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks
import me.alegian.thavma.impl.init.registries.deferred.T7DataComponents.ASPECTS
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponentType
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.SlabType
import net.neoforged.neoforge.common.Tags

class AuraNodeBE(pos: BlockPos?, blockState: BlockState?) :
    DataComponentBE(T7BlockEntities.AURA_NODE.get(), pos, blockState) {
    private val glassPositions = ArrayList<BlockPos>()
    private val slabPositions = ArrayList<BlockPos>()

    /**
     * The countdown in ticks after which a node will break
     * itself and drop a contained aura node.
     */
    var containingCountdown: Int = -1
        private set

    init {
        this.generateGlassPositions()
        this.generateSlabPositions()
    }

    override fun onLoad() {
        level?.run {
            if (!isClientSide() && this is ServerLevel) {
                AspectContainer.at(this, blockPos)
                    .filter { c -> c.areAspectsNull() }
                    .ifPresent { c -> c.aspects = AspectMap.randomPrimals() }
                updateBlockEntityS2C(blockPos)
            }
        }
    }

    /**
     * Cached possible positions of glass.
     * Used for contained aura node interactions.
     * 3x3x3 except center
     */
    private fun generateGlassPositions() {
        for (i in -1..1)
            for (j in -1..1)
                for (k in -1..1) {
                    if (i == 0 && j == 0 && k == 0) continue
                    glassPositions.add(this.blockPos.offset(i, j, k))
                }
    }

    /**
     * Cached possible positions of wooden slabs.
     * Used for contained aura node interactions.
     * 3x1x3, above the center
     */
    private fun generateSlabPositions() {
        for (i in -1..1)
            for (j in -1..1)
                slabPositions.add(this.blockPos.offset(i, 2, j))
    }

    /**
     * Returns true if the interaction was successful
     */
    fun jarInteraction(): Boolean {
        level?.let { level ->
            // check if glass exists
            for (pos in this.glassPositions)
                if (!level.getBlockState(pos).`is`(Tags.Blocks.GLASS_BLOCKS)) return false
            // check if slabs exist, and are bottom slabs
            for (pos in this.slabPositions) {
                val blockState = level.getBlockState(pos)
                if (blockState.`is`(BlockTags.WOODEN_SLABS) || blockState.getValue(BlockStateProperties.SLAB_TYPE) != SlabType.BOTTOM) return false
            }

            if (!level.isClientSide() && level is ServerLevel) {
                for (pos in this.glassPositions) level.removeBlock(pos, false)
                for (pos in this.slabPositions) level.removeBlock(pos, false)
            }

            this.containingCountdown = MAX_COUNTDOWN

            return true
        }
        return false
    }

    fun contain() {
        this.containingCountdown = -1
        level?.removeBlock(this.blockPos, false)
        level?.let {
            val itemEntity = ItemEntity(
                it,
                blockPos.x + 0.5,
                blockPos.y + 0.5,
                blockPos.z + 0.5,
                ItemStack(T7Blocks.AURA_NODE.get())
            )
            it.addFreshEntity(itemEntity)
        }
    }

    fun decrementContainingCountdown() {
        containingCountdown--
    }

    override fun getComponentTypes(): Array<DataComponentType<*>> {
        return arrayOf(ASPECTS.get())
    }

    companion object {
        const val MAX_COUNTDOWN: Int = 60
        fun tick(level: Level?, pos: BlockPos?, state: BlockState?, blockEntity: AuraNodeBE) {
            val countdown = blockEntity.containingCountdown
            if (countdown == 0) blockEntity.contain()
            else if (countdown > 0) blockEntity.decrementContainingCountdown()
        }
    }
}