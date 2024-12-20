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

class GreatwoodTrunkPlacer(pBaseHeight: Int, pHeightRandA: Int, pHeightRandB: Int) :
    GiantTrunkPlacer(pBaseHeight, pHeightRandA, pHeightRandB) {
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

        var i = pFreeTreeHeight - 2
        while (i > pFreeTreeHeight / 4) {
            val f = pRandom.nextFloat() * (Math.PI * 2).toFloat()
            var j: Int
            var k: Int

            for (l in 0..5) {
                j = (1.5f + Mth.cos(f) * l.toFloat()).toInt()
                k = (1.5f + Mth.sin(f) * l.toFloat()).toInt()
                val blockpos = pPos.offset(j, i - 3 + l / 2, k)
                this.placeLog(pLevel, pBlockSetter, pRandom, blockpos, pConfig)
                if (l == 6 - 1) list.add(FoliageAttachment(blockpos.above(3), 0, false))
            }
            i -= 1
        }

        return list
    }

    companion object {
        val CODEC =
            RecordCodecBuilder.mapCodec { builder ->
                trunkPlacerParts(builder).apply(
                    builder, ::GreatwoodTrunkPlacer
                )
            }
    }
}
