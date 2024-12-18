package me.alegian.thavma.impl.common.util

class DoubleMap<K1, K2, V> {
    private val map = HashMap<K1, HashMap<K2, V>>()

    fun put(k1: K1, k2: K2, v: V): V? {
        if (!map.containsKey(k1)) {
            map[k1] = HashMap()
        }
        return map[k1]?.put(k2, v)
    }

    operator fun get(k1: K1, k2: K2): V? {
        if (!map.containsKey(k1)) {
            return null
        }
        return map[k1]?.get(k2)
    }

    fun values(): Collection<V> {
        val maps = map.values
        return maps.stream().flatMap { m -> m.values.stream() }.toList()
    }
}
