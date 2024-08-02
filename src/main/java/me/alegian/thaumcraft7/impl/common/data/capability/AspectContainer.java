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

  public AspectContainer(AspectList contents) {
    this.contents = contents;
  }

  public AspectContainer() {
    this.contents = new AspectList();
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

  public void readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    contents = AspectList.parse(lookupProvider, nbt.getList("aspects", Tag.TAG_COMPOUND));
  }

  public void writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    nbt.put("aspects", contents.save(lookupProvider));
  }
}
