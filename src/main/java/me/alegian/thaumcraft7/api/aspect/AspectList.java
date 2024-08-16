package me.alegian.thaumcraft7.api.aspect;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AspectList {
  private final Map<Aspect, Integer> map;

  public AspectList(Map<Aspect, Integer> map) {
    this.map = new LinkedHashMap<>(map);
  }

  public AspectList() {
    this.map = new LinkedHashMap<>();
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
          .orElse(DataResult.success(new Pair<>(new AspectList(), t)));
    }

    @Override
    public <T> DataResult<T> encode(AspectList aspectList, DynamicOps<T> dynamicOps, T t) {
      var listOfPairs = aspectList.map.entrySet().stream().map(e -> Pair.of(e.getKey(), e.getValue())).toList();
      return PAIR_LIST_CODEC.encode(listOfPairs, dynamicOps, t);
    }
  };

  public AspectList add(Aspect aspect, int amount) {
    map.compute(aspect, (k, v) -> v == null ? amount : v + amount);
    return this;
  }

  public void merge(AspectList aspects) {
    aspects.map.forEach((k, v) -> {
      this.map.merge(k, v, Integer::sum);
    });
  }

  public static AspectList of(Aspect aspect, int amount) {
    return new AspectList(Map.of(aspect, amount));
  }

  public int get(Aspect aspect) {
    return map.getOrDefault(aspect, 0);
  }

  public Set<Aspect> aspectSet() {
    return map.keySet();
  }

  public int size() {
    return map.size();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public Tag save(HolderLookup.Provider lookupProvider) {
    return CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this)
        .getOrThrow();
  }

  public static AspectList parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalAspectList = CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalAspectList.orElse(new AspectList());
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
