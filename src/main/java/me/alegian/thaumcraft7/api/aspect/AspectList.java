package me.alegian.thaumcraft7.api.aspect;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AspectList {
  private final Map<Aspect, Integer> map;

  public AspectList(Map<Aspect, Integer> map) {
    this.map = map;
  }

  public AspectList() {
    this.map = new LinkedHashMap<>();
  }

  public static final Codec<Map<Aspect, Integer>> CODEC = Codec.unboundedMap(Aspect.CODEC, Codec.INT);

  public AspectList add(Aspect aspect, int amount) {
    map.put(aspect, amount);
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

  public Tag save(HolderLookup.Provider lookupProvider) {
    return CODEC.encode(this.map, lookupProvider.createSerializationContext(NbtOps.INSTANCE), null).getOrThrow();
  }

  public Tag save(HolderLookup.Provider lookupProvider, Tag prefix) {
    return CODEC.encode(this.map, lookupProvider.createSerializationContext(NbtOps.INSTANCE), prefix).getOrThrow();
  }

  public static AspectList parseOptional(HolderLookup.Provider lookupProvider, CompoundTag tag) {
    return tag.isEmpty() ? new AspectList() : parse(lookupProvider, tag).orElse(new AspectList());
  }

  public static Optional<AspectList> parse(HolderLookup.Provider lookupProvider, Tag tag) {
    var optionalMap = CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(System.out::println);

    return optionalMap.map(AspectList::new);
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
