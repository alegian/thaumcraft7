package me.alegian.thaumcraft7.impl.init.data.worldgen.tree.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7TrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GreatwoodTrunkPlacer extends GiantTrunkPlacer {
  public static final MapCodec<GreatwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
      builder -> trunkPlacerParts(builder).apply(builder, GreatwoodTrunkPlacer::new)
  );

  public GreatwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
    super(pBaseHeight, pHeightRandA, pHeightRandB);
  }

  @Override
  protected TrunkPlacerType<?> type() {
    return T7TrunkPlacerTypes.GREATWOOD_TRUNK_PLACER_TYPE.get();
  }

  @Override
  public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader pLevel,
      BiConsumer<BlockPos, BlockState> pBlockSetter,
      RandomSource pRandom,
      int pFreeTreeHeight,
      BlockPos pPos,
      TreeConfiguration pConfig
  ) {
    List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
    list.addAll(super.placeTrunk(pLevel, pBlockSetter, pRandom, pFreeTreeHeight, pPos, pConfig));

    for (int i = pFreeTreeHeight - 2; i > pFreeTreeHeight / 4; i -= 1) {
      float f = pRandom.nextFloat() * (float) (Math.PI * 2);
      int j = 0;
      int k = 0;

      for (int l = 0; l < 6; l++) {
        j = (int) (1.5F + Mth.cos(f) * (float) l);
        k = (int) (1.5F + Mth.sin(f) * (float) l);
        BlockPos blockpos = pPos.offset(j, i - 3 + l / 2, k);
        this.placeLog(pLevel, pBlockSetter, pRandom, blockpos, pConfig);
        if (l == 6 - 1) list.add(new FoliagePlacer.FoliageAttachment(blockpos.above(3), 0, false));
      }
    }

    return list;
  }
}
