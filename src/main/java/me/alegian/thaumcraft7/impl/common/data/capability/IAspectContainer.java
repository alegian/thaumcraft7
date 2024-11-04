package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.aspect.AspectStack;

import javax.annotation.Nullable;

/**
 * WARNING: this differs from the old Thaumcraft API
 * used by any Block, Item, Entity that contains Aspects
 * (not to be confused with the Aspect DataMap which
 * holds the scannable aspects)
 */
public interface IAspectContainer {
  AspectMap getAspects();

  AspectStack insert(AspectStack aspect);

  boolean insert(@Nullable AspectMap aspects);

  void extract(AspectMap aspects);

  AspectStack extract(AspectStack aspect);

  AspectStack extractRandom(int amount);

  int getMaxAmount();

  boolean isVisSource();

  boolean isEssentiaSource();
}
