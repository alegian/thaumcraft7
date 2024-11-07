package me.alegian.thavma.impl.common.util;

import java.util.Collection;
import java.util.HashMap;

public class DoubleMap<K1, K2, V> {
  private final HashMap<K1, HashMap<K2, V>> map = new HashMap<>();

  public V put(K1 k1, K2 k2, V v) {
    if (!map.containsKey(k1)) {
      map.put(k1, new HashMap<>());
    }
    return map.get(k1).put(k2, v);
  }

  public V get(K1 k1, K2 k2) {
    if (!map.containsKey(k1)) {
      return null;
    }
    return map.get(k1).get(k2);
  }

  public Collection<V> values() {
    var maps = map.values();
    return maps.stream().flatMap(m -> m.values().stream()).toList();
  }
}
