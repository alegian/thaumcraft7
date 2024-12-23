package me.alegian.thavma.impl.init.data.worldgen.tree.trunk

import com.mojang.serialization.codecs.RecordCodecBuilder
import me.alegian.thavma.impl.init.registries.deferred.T7TrunkPlacerTypes.GREATWOOD
import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.level.LevelSimulatedReader
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType
import java.util.function.BiConsumer

class GreatwoodTrunkPlacer(pBaseHeight: Int, pHeightRandA: Int, pHeightRandB: Int) : GiantTrunkPlacer(pBaseHeight, pHeightRandA, pHeightRandB) {
    override fun type(): TrunkPlacerType<*> {
        return GREATWOOD.get()
    }

    override fun placeTrunk(
        pLevel: LevelSimulatedReader,
        pBlockSetter: BiConsumer<BlockPos, BlockState>,
        pRandom: RandomSource,
        pFreeTreeHeight: Int,
        pPos: BlockPos,
        pConfig: TreeConfiguration
    ): List<FoliageAttachment> {
        val list = ArrayList<FoliageAttachment>()
        list.addAll(super.placeTrunk(pLevel, pBlockSetter, pRandom, pFreeTreeHeight, pPos, pConfig))

        var currY = pFreeTreeHeight - 2
        while (currY > pFreeTreeHeight / 4) {
            repeat(2) {
                branch(pRandom, pPos, currY, pLevel, pBlockSetter, pConfig, list)
            }
            currY--
        }

        return list
    }

    private fun branch(pRandom: RandomSource, pPos: BlockPos, currY: Int, pLevel: LevelSimulatedReader, pBlockSetter: BiConsumer<BlockPos, BlockState>, pConfig: TreeConfiguration, list: ArrayList<FoliageAttachment>) {
        val f = pRandom.nextFloat() * (Math.PI * 2).toFloat()
        var j: Int
        var k: Int

        val branchLength = pRandom.nextIntBetweenInclusive(4, 7)
        for (l in 0..branchLength) {
            j = (Mth.cos(f) * l).toInt()
            k = (Mth.sin(f) * l).toInt()
            val blockpos = pPos.offset(j, currY + l / 2 - 3, k)
            this.placeLog(pLevel, pBlockSetter, pRandom, blockpos, pConfig)
            if (l == branchLength) list.add(FoliageAttachment(blockpos.above(3), 0, false))
        }
    }

    companion object {
        val CODEC = RecordCodecBuilder.mapCodec { builder -> trunkPlacerParts(builder).apply(builder, ::GreatwoodTrunkPlacer) }
    }
}
