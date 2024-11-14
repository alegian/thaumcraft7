package me.alegian.thavma.impl.common.data.capability;

import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.common.aspect.AspectMap;

import javax.annotation.Nullable;

/**
 * WARNING: this differs from the old Thaumcraft API
 * used by any Block, Item, Entity that contains Aspects
 * (not to be confused with the Aspect DataMap which
 * holds the scannable aspects)
 */
public interface IAspectContainer {
  AspectMap getAspects();

  boolean areAspectsNull();

  void setAspects(AspectMap aspects);

  int insert(Aspect aspect, int amount, boolean simulate);

  boolean insert(@Nullable AspectMap aspects);

  void extract(AspectMap aspects);

  int extract(Aspect aspect, int amount, boolean simulate);

  int getCapacity();

  boolean isVisSource();

  boolean isEssentiaSource();
}
