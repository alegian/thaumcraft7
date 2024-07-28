package me.alegian.thaumcraft7.api.aspect;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AspectList {
  private Map<Aspect, Integer> map = new LinkedHashMap<>();

  public AspectList add(Aspect aspect, int amount) {
    map.put(aspect, amount);
    return this;
  }

  public int get(Aspect aspect) {
    return map.get(aspect);
  }

  public Set<Aspect> aspectSet(){
    return map.keySet();
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
