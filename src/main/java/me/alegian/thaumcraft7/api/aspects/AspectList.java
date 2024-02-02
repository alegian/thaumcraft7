package me.alegian.thaumcraft7.api.aspects;

import java.util.LinkedHashMap;
import java.util.Map;

public class AspectList {
    public Map<Aspect,Integer> aspects = new LinkedHashMap<>();

    public AspectList add(Aspect aspect, int amount){
        aspects.put(aspect, amount);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        aspects.forEach((k,v)->
            str.append(k.id).append(v)
        );
        return str.toString();
    }
}
