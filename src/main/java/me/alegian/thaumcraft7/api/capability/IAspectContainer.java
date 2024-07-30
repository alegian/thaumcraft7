package me.alegian.thaumcraft7.api.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;

/*
 * WARNING: this differs from the old Thaumcraft API
 * represents any Block, Item, Entity that contains Aspects that can be scanned with
 * a Thaumometer.
 */
public interface IAspectContainer {
  public AspectList getAspects();

  public boolean addAspect(Aspect aspect, int amount);
}
