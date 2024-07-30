package me.alegian.thaumcraft7.api.aspect;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

import java.util.*;
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

  public static final Codec<List<Pair<Aspect, Integer>>> CODEC = PAIR_CODEC.listOf();

  public AspectList add(Aspect aspect, int amount) {
    map.compute(aspect, (k, v) -> v == null ? amount : v + amount);
    return this;
  }

  public int get(Aspect aspect) {
    return map.get(aspect);
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

  public Tag saveOptional(HolderLookup.Provider lookupProvider) {
    return this.isEmpty() ? new CompoundTag() : this.save(lookupProvider, new CompoundTag());
  }

  public Tag save(HolderLookup.Provider lookupProvider, Tag prefix) {
    var listOfPairs = this.map.entrySet().stream().map(e->Pair.of(e.getKey(), e.getValue())).toList();
    return CODEC.encode(listOfPairs, lookupProvider.createSerializationContext(NbtOps.INSTANCE), prefix).getOrThrow();
  }

  public static AspectList parseOptional(HolderLookup.Provider lookupProvider, CompoundTag tag) {
    return tag.isEmpty() ? new AspectList() : parse(lookupProvider, tag).orElse(new AspectList());
  }

  public static Optional<AspectList> parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalListOfPairs = CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalListOfPairs.map(o->new AspectList(o.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))));
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
