package me.alegian.thaumcraft7.impl.common.aspect;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Immutable.
 * Represents a list of Aspects and their amounts.
 * Any operation that adds or removes Aspects should return a new AspectList.
 */
public class AspectList {
  private final Map<Aspect, Integer> map;
  public static final AspectList EMPTY = new AspectList(new HashMap<>());

  public AspectList(Map<Aspect, Integer> map) {
    this.map = new HashMap<>(map);
  }

  public static final Codec<Pair<Aspect, Integer>> PAIR_CODEC = Codec.pair(
      Aspect.CODEC.fieldOf("aspect").codec(),
      Codec.INT.fieldOf("amount").codec()
  );

  public static final Codec<List<Pair<Aspect, Integer>>> PAIR_LIST_CODEC = PAIR_CODEC.listOf();

  public static final Codec<AspectList> CODEC = new Codec<>() {
    @Override
    public <T> DataResult<Pair<AspectList, T>> decode(DynamicOps<T> dynamicOps, T t) {
      var optionalListOfPairs = PAIR_LIST_CODEC.parse(dynamicOps, t)
          .resultOrPartial(System.out::println);

      return optionalListOfPairs.map(o ->
              o.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
          )
          .map(AspectList::new)
          .map(m -> new Pair<>(m, t))
          .map(DataResult::success)
          .orElse(DataResult.success(new Pair<>(AspectList.EMPTY, t)));
    }

    @Override
    public <T> DataResult<T> encode(AspectList aspectList, DynamicOps<T> dynamicOps, T t) {
      var listOfPairs = aspectList.map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue())).toList();
      return PAIR_LIST_CODEC.encode(listOfPairs, dynamicOps, t);
    }
  };

  public static final StreamCodec<ByteBuf, Map<Aspect, Integer>> MAP_STREAM_CODEC =
      ByteBufCodecs.map(
          HashMap::new,
          Aspect.STREAM_CODEC,
          ByteBufCodecs.INT
      );

  public static final StreamCodec<ByteBuf, AspectList> STREAM_CODEC =
      StreamCodec.composite(
          MAP_STREAM_CODEC, AspectList::getMap,
          AspectList::new
      );

  public AspectList add(Aspect aspect, int amount) {
    HashMap<Aspect, Integer> newMap = new HashMap<>(map);
    newMap.put(aspect, amount);
    return new AspectList(newMap);
  }

  public AspectList scale(int scale) {
    HashMap<Aspect, Integer> newMap = new HashMap<>();
    map.forEach((k, v) -> newMap.put(k, v * scale));
    return new AspectList(newMap);
  }

  public AspectList merge(AspectList other) {
    HashMap<Aspect, Integer> newMap = new HashMap<>(map);
    other.getMap().forEach((k, v) -> {
      newMap.merge(k, v, Integer::sum);
    });
    return new AspectList(newMap);
  }

  /**
   * AspectList Maps are also immutable.
   * This is read-only access to copy the map into a new one.
   */
  protected Map<Aspect, Integer> getMap() {
    return map;
  }

  public static AspectList of(Aspect aspect, int amount) {
    return new AspectList(Map.of(aspect, amount));
  }

  public static AspectList of(AspectStack... aspectStacks) {
    Map<Aspect, Integer> map = new HashMap<>();
    for (AspectStack stack : aspectStacks) {
      map.put(stack.aspect(), stack.amount());
    }
    return new AspectList(map);
  }

  public static AspectList randomAura() {
    HashMap<Aspect, Integer> map = new HashMap<>();
    for (Aspect a : Aspect.PRIMAL_ASPECTS) {
      map.put(a, (int) (Math.random() * 16 + 1));
    }
    return new AspectList(map);
  }

  public int get(Aspect aspect) {
    return map.getOrDefault(aspect, 0);
  }

  public ImmutableList<AspectStack> displayedAspects() {
    if (this == AspectList.EMPTY) return ImmutableList.of();
    return Aspect.ASPECTS.values().stream().filter(a -> get(a) > 0).map(a -> AspectStack.of(a, get(a))).collect(ImmutableList.toImmutableList());
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return this == AspectList.EMPTY || map.values().stream().noneMatch(i -> i > 0);
  }

  public Tag save(HolderLookup.Provider lookupProvider) {
    return CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this)
        .getOrThrow();
  }

  public static AspectList parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalAspectList = CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalAspectList.orElse(AspectList.EMPTY);
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
        str.append(k.id).append(v)
    );
    return str.toString();
  }
}
