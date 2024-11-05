package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;

import javax.annotation.Nullable;

/**
 * WARNING: this differs from the old Thaumcraft API
 * used by any Block, Item, Entity that contains Aspects
 * (not to be confused with the Aspect DataMap which
 * holds the scannable aspects)
 */
public interface IAspectContainer {
  AspectMap getAspects();

  int insert(Aspect aspect, int amount, boolean simulate);

  boolean insert(@Nullable AspectMap aspects);

  void extract(AspectMap aspects);

  int extract(Aspect aspect, int amount, boolean simulate);

  int getMaxAmount();

  boolean isVisSource();

  boolean isEssentiaSource();
}
