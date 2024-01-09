package me.alegian.thaumcraft7.api.capabilities;

import me.alegian.thaumcraft7.api.aspects.AspectList;

/*
 * WARNING: this differs from the old Thaumcraft API
 * represents any Block, Item, Entity that contains Aspects that can be scanned with
 * a Thaumometer.
 */
public interface IAspectContainer {
    public AspectList getAspects();
}
