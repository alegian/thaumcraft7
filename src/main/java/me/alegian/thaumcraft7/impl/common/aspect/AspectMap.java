package me.alegian.thaumcraft7.impl.common.aspect;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Immutable.
 * Represents a map of Aspects to their amounts.
 * Any operation that adds or removes Aspects should return a new AspectMap.
 * <p>
 * Internally uses a LinkedHashMap (i.e. a SequencedMap) for deterministic iteration order.
 * Using a non-sequenced map might cause undefined behavior.
 */
public class AspectMap {
  private final LinkedHashMap<Aspect, Integer> map;
  public static final AspectMap EMPTY = new AspectMap(new LinkedHashMap<>());

  /**
   * This argument should ideally be a LinkedHashMap, but is a Map instead,
   * to make codecs easier
   */
  private AspectMap(Map<Aspect, Integer> map) {
    this.map = new LinkedHashMap<>(map);
  }

  public static final Codec<Pair<Aspect, Integer>> PAIR_CODEC = Codec.pair(
      Aspect.CODEC.fieldOf("aspect").codec(),
      Codec.INT.fieldOf("amount").codec()
  );

  public static final Codec<List<Pair<Aspect, Integer>>> PAIR_LIST_CODEC = PAIR_CODEC.listOf();

  public static final Codec<AspectMap> CODEC = new Codec<>() {
    @Override
    public <T> DataResult<Pair<AspectMap, T>> decode(DynamicOps<T> dynamicOps, T t) {
      var optionalListOfPairs = PAIR_LIST_CODEC.parse(dynamicOps, t)
          .resultOrPartial(System.out::println);

      return optionalListOfPairs.map(o ->
              o.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
          )
          .map(AspectMap::new)
          .map(m -> new Pair<>(m, t))
          .map(DataResult::success)
          .orElse(DataResult.success(new Pair<>(AspectMap.EMPTY, t)));
    }

    @Override
    public <T> DataResult<T> encode(AspectMap aspectMap, DynamicOps<T> dynamicOps, T t) {
      var listOfPairs = aspectMap.map.entrySet().stream().filter(e -> e.getValue() > 0).map(e -> Pair.of(e.getKey(), e.getValue())).toList();
      return PAIR_LIST_CODEC.encode(listOfPairs, dynamicOps, t);
    }
  };

  public static final StreamCodec<ByteBuf, LinkedHashMap<Aspect, Integer>> MAP_STREAM_CODEC =
      ByteBufCodecs.map(
          LinkedHashMap::new,
          Aspect.STREAM_CODEC,
          ByteBufCodecs.INT
      );

  public static final StreamCodec<ByteBuf, AspectMap> STREAM_CODEC =
      StreamCodec.composite(
          MAP_STREAM_CODEC, AspectMap::getMap,
          AspectMap::new
      );

  public AspectMap add(Aspect aspect, int amount) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(map);
    newMap.put(aspect, amount);
    return new AspectMap(newMap);
  }

  public AspectMap scale(int scale) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>();
    map.forEach((k, v) -> newMap.put(k, v * scale));
    return new AspectMap(newMap);
  }

  public AspectMap merge(AspectMap other) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(map);
    other.getMap().forEach((k, v) -> newMap.merge(k, v, Integer::sum));
    return new AspectMap(newMap);
  }

  public AspectMap subtract(AspectMap other) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(map);
    other.getMap().forEach((k, v) -> newMap.merge(k, v, (a, b) -> nullIfZero(a - b)));
    return new AspectMap(newMap);
  }

  private static Integer nullIfZero(Integer amount) {
    return amount == 0 ? null : amount;
  }

  /**
   * Whether this AspectMap contains all aspects (in greater quantity) than another.
   * Useful for recipe checks
   */
  public boolean contains(AspectMap other) {
    for (Aspect a : other.getMap().keySet()) {
      if (this.getMap().get(a) < other.getMap().get(a)) return false;
    }
    return true;
  }

  /**
   * AspectMap.map is also immutable.
   * This is read-only access to copy the map into a new one.
   */
  protected LinkedHashMap<Aspect, Integer> getMap() {
    return map;
  }

  public static AspectMap of(AspectStack... aspectStacks) {
    LinkedHashMap<Aspect, Integer> map = new LinkedHashMap<>();
    for (AspectStack stack : aspectStacks) {
      map.put(stack.aspect(), stack.amount());
    }
    return new AspectMap(map);
  }

  public static AspectMap randomPrimals() {
    LinkedHashMap<Aspect, Integer> map = new LinkedHashMap<>();
    for (var a : Aspects.PRIMAL_ASPECTS) {
      map.put(a.get(), (int) (Math.random() * 16 + 1));
    }
    return new AspectMap(map);
  }

  public int get(Aspect aspect) {
    return map.getOrDefault(aspect, 0);
  }

  public ImmutableList<AspectStack> displayedAspects() {
    if (this == AspectMap.EMPTY) return ImmutableList.of();
    return Aspects.REGISTRAR.getEntries().stream().map(Supplier::get).filter(a -> get(a) > 0).map(a -> AspectStack.of(a, get(a))).collect(ImmutableList.toImmutableList());
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return this == AspectMap.EMPTY || map.values().stream().noneMatch(i -> i > 0);
  }

  public Tag save(HolderLookup.Provider lookupProvider) {
    return CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this)
        .getOrThrow();
  }

  public static AspectMap parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalAspectMap = CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalAspectMap.orElse(AspectMap.EMPTY);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    map.forEach((k, v) ->
        str.append(k).append(v)
    );
    return str.toString();
  }
}
