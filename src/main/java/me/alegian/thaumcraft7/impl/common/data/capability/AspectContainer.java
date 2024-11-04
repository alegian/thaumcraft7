package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.aspect.AspectStack;
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
    AspectMap aspectMap = this.holder.get(T7DataComponents.ASPECTS);
    if (aspectMap == null) return AspectMap.EMPTY;
    return aspectMap;
  }

  @Override
  public boolean addAspect(AspectStack aspectStack) {
    AspectMap current = this.getAspects();
    var maxInsert = this.maxAmount - current.get(aspectStack.aspect());
    var cappedInsert = Math.min(aspectStack.amount(), maxInsert);

    this.holder.set(T7DataComponents.ASPECTS, current.add(aspectStack.aspect(), cappedInsert));

    return cappedInsert != 0;
  }

  @Override
  public boolean addAspects(@Nullable AspectMap aspects) {
    if (aspects == null) return false;

    AspectMap currentAspects = this.getAspects();
    AspectMap newAspects = currentAspects.merge(aspects).cap(this.maxAmount);
    this.holder.set(T7DataComponents.ASPECTS, newAspects);

    return currentAspects.l1norm() != newAspects.l1norm();
  }

  @Override
  public void subtract(AspectMap aspects) {
    this.holder.set(T7DataComponents.ASPECTS, this.getAspects().subtract(aspects));
  }

  @Override
  public AspectStack subtract(AspectStack aspect) {
    this.holder.set(T7DataComponents.ASPECTS, this.getAspects().subtract(aspect));
    return aspect;
  }

  @Override
  public int getMaxAmount() {
    return this.maxAmount;
  }

  @Override
  public boolean isVisSource() {
    return this.visSource;
  }

  @Override
  public boolean isEssentiaSource() {
    return this.essentiaSource;
  }
}
