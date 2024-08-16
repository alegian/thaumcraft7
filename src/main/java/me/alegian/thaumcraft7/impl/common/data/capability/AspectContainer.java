package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;

public class AspectContainer implements IAspectContainer {
  private AspectList contents;
  private int maxAmount; // per aspect
  private boolean visSource;
  private boolean essentiaSource;

  public AspectContainer(AspectList contents, int maxAmount, boolean visSource, boolean essentiaSource) {
    this.contents = contents;
    this.maxAmount = maxAmount;
    this.visSource = visSource;
    this.essentiaSource = essentiaSource;
  }

  public AspectContainer(AspectList contents) {
    this(contents, Integer.MAX_VALUE, false, false);
  }

  public AspectContainer() {
    this(new AspectList());
  }

  public AspectContainer(int maxAmount) {
    this();
    this.maxAmount = maxAmount;
  }

  @Override
  public AspectList getAspects() {
    return contents;
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

  public int getMaxAmount() {
    return maxAmount;
  }

  public boolean isVisSource() {
    return visSource;
  }

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
