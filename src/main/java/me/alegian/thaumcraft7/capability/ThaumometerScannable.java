package me.alegian.thaumcraft7.capability;

import me.alegian.thaumcraft7.ThaumcraftAspects;

public class ThaumometerScannable implements IThaumometerScannable{
    private final Iterable<ThaumcraftAspects.Contained> contents;

    public ThaumometerScannable(Iterable<ThaumcraftAspects.Contained> contents){
        this.contents = contents;
    }

    @Override
    public Iterable<ThaumcraftAspects.Contained> getAspects() {
        return contents;
    }
}
