package me.alegian.thaumcraft7.capability;

import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;

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
