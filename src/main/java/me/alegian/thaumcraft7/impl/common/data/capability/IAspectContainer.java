package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectList;

import javax.annotation.Nullable;

/*
 * WARNING: this differs from the old Thaumcraft API
 * represents any Block, Item, Entity that contains Aspects that can be scanned with
 * a Thaumometer.
 */
public interface IAspectContainer {
  AspectList getAspects();

  boolean addAspect(Aspect aspect, int amount);

  boolean addAspects(@Nullable AspectList aspects);

  int getMaxAmount();

  boolean isVisSource();

  boolean isEssentiaSource();
}
