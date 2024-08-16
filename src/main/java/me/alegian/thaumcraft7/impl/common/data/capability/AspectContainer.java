package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7DataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.MutableDataComponentHolder;

import javax.annotation.Nullable;

public class AspectContainer implements IAspectContainer {
  private final MutableDataComponentHolder holder;
  private int maxAmount; // per aspect
  private boolean visSource;
  private boolean essentiaSource;

  public AspectContainer(MutableDataComponentHolder holder, int maxAmount, boolean visSource, boolean essentiaSource) {
    this.holder = holder;
    this.maxAmount = maxAmount;
    this.visSource = visSource;
    this.essentiaSource = essentiaSource;
  }

  public AspectContainer(MutableDataComponentHolder holder, int maxAmount) {
    this(holder, maxAmount, false, false);
  }


  @Override
  public AspectList getAspects() {
    return holder.get(T7DataComponents.ASPECTS);
  }

  @Override
  public boolean addAspect(Aspect aspect, int amount) {
    contents.add(aspect, amount);
    return true;
  }

  @Override
  public boolean addAspects(@Nullable AspectList aspects) {
    if (aspects == null) return false;

    contents.merge(aspects);
    return true;
  }

  @Override
  public int getMaxAmount() {
    return maxAmount;
  }

  @Override
  public boolean isVisSource() {
    return visSource;
  }

  @Override
  public boolean isEssentiaSource() {
    return essentiaSource;
  }

  public void readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    contents = AspectList.parse(lookupProvider, nbt.getList("aspects", Tag.TAG_COMPOUND));
  }

  public void writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    nbt.put("aspects", contents.save(lookupProvider));
  }
}
