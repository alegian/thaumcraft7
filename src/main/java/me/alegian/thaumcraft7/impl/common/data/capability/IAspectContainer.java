package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.aspect.AspectStack;

import javax.annotation.Nullable;

/*
 * WARNING: this differs from the old Thaumcraft API
 * represents any Block, Item, Entity that contains Aspects
 */
public interface IAspectContainer {
  AspectMap getAspects();

  boolean addAspect(AspectStack aspect);

  boolean addAspects(@Nullable AspectMap aspects);

  void subtract(AspectMap aspects);

  AspectStack subtract(AspectStack aspect);

  int getMaxAmount();

  boolean isVisSource();

  boolean isEssentiaSource();
}
