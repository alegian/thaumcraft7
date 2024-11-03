package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;

import javax.annotation.Nullable;

/*
 * WARNING: this differs from the old Thaumcraft API
 * represents any Block, Item, Entity that contains Aspects
 */
public interface IAspectContainer {
  AspectMap getAspects();

  boolean addAspect(Aspect aspect, int amount);

  boolean addAspects(@Nullable AspectMap aspects);

  void subtract(AspectMap aspects);

  int getMaxAmount();

  boolean isVisSource();

  boolean isEssentiaSource();
}
