package me.alegian.thavma.impl.common.aspect;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.*;
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
public class AspectMap implements Iterable<AspectStack> {
  public static final AspectMap EMPTY = new AspectMap(new LinkedHashMap<>());
  public static final Codec<Pair<Aspect, Integer>> PAIR_CODEC = Codec.pair(
      Aspect.CODEC.fieldOf("aspect").codec(),
      Codec.INT.fieldOf("amount").codec()
  );
  public static final Codec<List<Pair<Aspect, Integer>>> PAIR_LIST_CODEC = AspectMap.PAIR_CODEC.listOf();
  public static final Codec<AspectMap> CODEC = new Codec<>() {
    @Override
    public <T> DataResult<Pair<AspectMap, T>> decode(DynamicOps<T> dynamicOps, T t) {
      var optionalListOfPairs = AspectMap.PAIR_LIST_CODEC.parse(dynamicOps, t)
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
      return AspectMap.PAIR_LIST_CODEC.encode(listOfPairs, dynamicOps, t);
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
          AspectMap.MAP_STREAM_CODEC, AspectMap::getMap,
          AspectMap::new
      );
  private final LinkedHashMap<Aspect, Integer> map;

  /**
   * This argument should ideally be a LinkedHashMap, but is a Map instead,
   * to make codecs easier
   */
  private AspectMap(Map<Aspect, Integer> map) {
    this.map = new LinkedHashMap<>(map);
  }

  private static Integer nullIfZero(Integer amount) {
    return amount == 0 ? null : amount;
  }

  public static AspectMap of(AspectStack... aspectStacks) {
    LinkedHashMap<Aspect, Integer> map = new LinkedHashMap<>();
    for (AspectStack stack : aspectStacks) map.put(stack.aspect(), stack.amount());
    return new AspectMap(map);
  }

  public static AspectMap ofPrimals(int amount) {
    LinkedHashMap<Aspect, Integer> map = new LinkedHashMap<>();
    for (var a : Aspects.PRIMAL_ASPECTS) map.put(a.get(), amount);
    return new AspectMap(map);
  }

  public static AspectMap randomPrimals() {
    Random random = new Random();
    LinkedHashMap<Aspect, Integer> map = new LinkedHashMap<>();
    var primals = new ArrayList<>(Aspects.PRIMAL_ASPECTS);
    Collections.shuffle(primals);
    var randomPrimals = primals.subList(0, random.nextInt(primals.size()) + 1);
    for (var a : randomPrimals)
      map.put(a.get(), random.nextInt(16) + 1);
    return new AspectMap(map);
  }

  public static AspectMap parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalAspectMap = AspectMap.CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalAspectMap.orElse(AspectMap.EMPTY);
  }

  public AspectMap add(Aspect aspect, int amount) {
    int oldAmount = this.get(aspect);
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(this.map);
    newMap.put(aspect, oldAmount + amount);
    return new AspectMap(newMap);
  }

  public AspectMap scale(int scale) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>();
    this.map.forEach((k, v) -> newMap.put(k, v * scale));
    return new AspectMap(newMap);
  }

  public AspectMap merge(AspectMap other) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(this.map);
    other.getMap().forEach((k, v) -> newMap.merge(k, v, Integer::sum));
    return new AspectMap(newMap);
  }

  public AspectMap cap(int amount) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(this.map);
    newMap.forEach((k, v) -> newMap.put(k, Math.min(v, amount)));
    return new AspectMap(newMap);
  }

  public int l1norm() {
    return this.map.values().stream().reduce(0, Integer::sum);
  }

  public AspectMap subtract(AspectMap other) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(this.map);
    other.getMap().forEach((k, v) -> newMap.merge(k, v, (a, b) -> AspectMap.nullIfZero(a - b)));
    return new AspectMap(newMap);
  }

  public AspectMap subtract(Aspect aspect, int amount) {
    LinkedHashMap<Aspect, Integer> newMap = new LinkedHashMap<>(this.map);
    newMap.computeIfPresent(aspect, (k, v) -> AspectMap.nullIfZero(v - amount));
    return new AspectMap(newMap);
  }

  /**
   * Whether this AspectMap contains all aspects (in greater quantity) than another.
   * Useful for recipe checks
   */
  public boolean contains(AspectMap other) {
    if (other == AspectMap.EMPTY) return true;
    for (Aspect a : other.getMap().keySet()) if (this.get(a) < other.get(a)) return false;
    return true;
  }

  /**
   * AspectMap.map is also immutable.
   * This is read-only access to copy the map into a new one.
   */
  protected LinkedHashMap<Aspect, Integer> getMap() {
    return this.map;
  }

  public List<AspectStack> toSortedList() {
    return this.map.entrySet().stream()
        .map(e -> AspectStack.of(e.getKey(), e.getValue()))
        .filter(a -> a.amount() > 0)
        .sorted((a, b) -> b.amount() - a.amount())
        .toList();
  }

  public int get(Aspect aspect) {
    return this.map.getOrDefault(aspect, 0);
  }

  public ImmutableList<AspectStack> displayedAspects() {
    if (this == AspectMap.EMPTY) return ImmutableList.of();
    return Aspects.REGISTRAR.getEntries().stream().map(Supplier::get).filter(a -> this.get(a) > 0).map(a -> AspectStack.of(a, this.get(a))).collect(ImmutableList.toImmutableList());
  }

  public int size() {
    return this.map.size();
  }

  public boolean isEmpty() {
    return this == AspectMap.EMPTY || this.map.values().stream().noneMatch(i -> i > 0);
  }

  public AspectMap copy() {
    return new AspectMap(this.map);
  }

  public Tag save(HolderLookup.Provider lookupProvider) {
    return AspectMap.CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this)
        .getOrThrow();
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
    this.map.forEach((k, v) ->
        str.append(k).append(v)
    );
    return str.toString();
  }

  @Override
  public @NotNull Iterator<AspectStack> iterator() {
    return this.map.entrySet().stream().filter(e -> e.getValue() > 0).map(e -> AspectStack.of(e.getKey(), e.getValue())).iterator();
  }
}
