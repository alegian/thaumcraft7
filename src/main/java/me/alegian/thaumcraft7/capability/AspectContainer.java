package me.alegian.thaumcraft7.capability;

import me.alegian.thaumcraft7.api.aspects.AspectList;
import me.alegian.thaumcraft7.api.capabilities.IAspectContainer;

public class AspectContainer implements IAspectContainer {
    private final AspectList contents;

    public AspectContainer(AspectList contents){
        this.contents = contents;
    }

    @Override
    public AspectList getAspects() {
        return contents;
    }
}
