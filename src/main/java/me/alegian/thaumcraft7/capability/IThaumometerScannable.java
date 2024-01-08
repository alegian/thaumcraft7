package me.alegian.thaumcraft7.capability;

import me.alegian.thaumcraft7.ThaumcraftAspects;

public interface IThaumometerScannable {
    public Iterable<ThaumcraftAspects.Contained> getAspects();
}
