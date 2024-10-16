package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7DataComponents;
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

  public AspectContainer(MutableDataComponentHolder holder) {
    this(holder, Integer.MAX_VALUE);
  }

  @Override
  public AspectMap getAspects() {
    AspectMap aspectMap = holder.get(T7DataComponents.ASPECTS);
    if (aspectMap == null) return AspectMap.EMPTY;
    return aspectMap;
  }

  @Override
  public boolean addAspect(Aspect aspect, int amount) {
    AspectMap current = getAspects();
    holder.set(T7DataComponents.ASPECTS, current.add(aspect, amount));
    return true;
  }

  @Override
  public boolean addAspects(@Nullable AspectMap aspects) {
    if (aspects == null) return false;

    AspectMap current = this.getAspects();
    holder.set(T7DataComponents.ASPECTS, current.merge(aspects));
    return true;
  }

  @Override
  public void subtract(AspectMap aspects) {
    holder.set(T7DataComponents.ASPECTS, this.getAspects().subtract(aspects));
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
}
