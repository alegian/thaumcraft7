package me.alegian.thaumcraft7.api.aspect;

import java.util.LinkedHashMap;
import java.util.Map;

public class AspectList {
  public Map<Aspect, Integer> map = new LinkedHashMap<>();

  public AspectList add(Aspect aspect, int amount) {
    map.put(aspect, amount);
    return this;
  }

  public int size() {
    return map.size();
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    map.forEach((k, v) ->
        str.append(k.id).append(v)
    );
    return str.toString();
  }
}
