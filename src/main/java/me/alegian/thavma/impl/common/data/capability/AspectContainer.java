package me.alegian.thavma.impl.common.data.capability;

import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.init.registries.T7Capabilities;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import me.alegian.thavma.impl.init.registries.deferred.T7DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.MutableDataComponentHolder;

import javax.annotation.Nullable;
import java.util.Optional;

public class AspectContainer implements IAspectContainer {
  private final MutableDataComponentHolder holder;
  private int capacity; // per aspect
  private boolean visSource;
  private boolean essentiaSource;

  public AspectContainer(MutableDataComponentHolder holder, int capacity, boolean visSource, boolean essentiaSource) {
    this.holder = holder;
    this.capacity = capacity;
    this.visSource = visSource;
    this.essentiaSource = essentiaSource;
  }

  public AspectContainer(MutableDataComponentHolder holder, int capacity) {
    this(holder, capacity, false, false);
  }

  public AspectContainer(MutableDataComponentHolder holder) {
    this(holder, Integer.MAX_VALUE);
  }

  public static Optional<IAspectContainer> at(Level level, BlockPos pos) {
    return Optional.ofNullable(level.getCapability(T7Capabilities.AspectContainer.BLOCK, pos, null));
  }

  public static IAspectContainer getAspectContainerInHand(Player player) {
    var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
    var offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);

    IAspectContainer aspectContainer = null;

    if (!mainHandItem.isEmpty())
      aspectContainer = mainHandItem.getCapability(T7Capabilities.AspectContainer.ITEM);
    else if (!offHandItem.isEmpty())
      aspectContainer = offHandItem.getCapability(T7Capabilities.AspectContainer.ITEM);

    return aspectContainer;
  }

  public static Optional<IAspectContainer> from(ItemStack itemStack) {
    return Optional.ofNullable(itemStack.getCapability(T7Capabilities.AspectContainer.ITEM));
  }

  public static boolean isAspectContainer(Level level, BlockPos blockPos) {
    return level.getCapability(T7Capabilities.AspectContainer.BLOCK, blockPos) != null;
  }

  public static Optional<Pair> blockSourceItemSink(Level level, BlockPos blockPos, ItemStack itemStack) {
    return AspectContainer.from(itemStack).flatMap(sink ->
        AspectContainer.at(level, blockPos).map(source -> new Pair(source, sink))
    );
  }

  @Override
  public AspectMap getAspects() {
    AspectMap aspectMap = this.holder.get(T7DataComponents.ASPECTS);
    if (aspectMap == null) return AspectMap.EMPTY;
    return aspectMap;
  }

  @Override
  public int insert(Aspect aspect, int amount, boolean simulate) {
    if (amount == 0) return 0;

    AspectMap current = this.getAspects();
    var maxInsert = this.getCapacity() - current.get(aspect);
    var cappedInsert = Math.min(amount, maxInsert);

    if (!simulate) this.holder.set(T7DataComponents.ASPECTS, current.add(aspect, cappedInsert));

    return cappedInsert;
  }

  @Override
  public boolean insert(@Nullable AspectMap aspects) {
    if (aspects == null) return false;

    AspectMap currentAspects = this.getAspects();
    AspectMap newAspects = currentAspects.merge(aspects).cap(this.getCapacity());
    this.holder.set(T7DataComponents.ASPECTS, newAspects);

    return currentAspects.l1norm() != newAspects.l1norm();
  }

  @Override
  public void extract(AspectMap aspects) {
    this.holder.set(T7DataComponents.ASPECTS, this.getAspects().subtract(aspects));
  }

  @Override
  public int extract(Aspect aspect, int amount, boolean simulate) {
    if (amount == 0) return 0;
    var maxSubtract = this.getAspects().get(aspect);
    var cappedSubtract = Math.min(amount, maxSubtract);

    if (!simulate) this.holder.set(T7DataComponents.ASPECTS, this.getAspects().subtract(aspect, amount));

    return cappedSubtract;
  }

  @Override
  public int getCapacity() {
    return this.capacity;
  }

  @Override
  public boolean isVisSource() {
    return this.visSource;
  }

  @Override
  public boolean isEssentiaSource() {
    return this.essentiaSource;
  }

  public static class Pair {
    private final IAspectContainer source;
    private final IAspectContainer sink;

    public Pair(IAspectContainer source, IAspectContainer sink) {
      this.source = source;
      this.sink = sink;
    }

    protected int simulateTransfer(Aspect a, int idealAmount) {
      int maxInsert = this.sink.insert(a, idealAmount, true);
      return this.source.extract(a, maxInsert, true);
    }

    public boolean canTransferPrimals() {
      return Aspects.PRIMAL_ASPECTS.stream()
          .map(a -> this.simulateTransfer(a.get(), 1))
          .anyMatch(e -> e > 0);
    }

    public int transferPrimal(int indexOffset, int idealAmount) {
      var primals = Aspects.PRIMAL_ASPECTS.size();
      for (int i = 0; i < primals; i++) {
        var a = Aspects.PRIMAL_ASPECTS.get((i + indexOffset) % primals).get();
        int amount = this.simulateTransfer(a, idealAmount);
        if (amount == 0) continue;
        this.sink.insert(a, amount, false);
        this.source.extract(a, amount, false);
        return amount;
      }
      return 0;
    }
  }
}
