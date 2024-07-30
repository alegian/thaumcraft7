package me.alegian.thaumcraft7.api.aspect;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.DataComponentUtil;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
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
    if (this.isEmpty()) {
      throw new IllegalStateException("Cannot encode empty FluidStack");
    } else {
      return DataComponentUtil.wrapEncodingExceptions(this, CODEC, lookupProvider);
    }
  }

  public Tag save(HolderLookup.Provider lookupProvider, Tag prefix) {
    if (this.isEmpty()) {
      throw new IllegalStateException("Cannot encode empty FluidStack");
    } else {
      return DataComponentUtil.wrapEncodingExceptions(this, CODEC, lookupProvider, prefix);
    }
  }

  public static FluidStack parseOptional(HolderLookup.Provider lookupProvider, CompoundTag tag) {
    return tag.isEmpty() ? EMPTY : parse(lookupProvider, tag).orElse(EMPTY);
  }

  public static Optional<FluidStack> parse(HolderLookup.Provider lookupProvider, Tag tag) {
    return CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag)
        .resultOrPartial(error -> LOGGER.error("Tried to load invalid fluid: '{}'", error));
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
