package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.init.registries.T7Capabilities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class AspectContainerHelper {
  public static Optional<AspectMap> getAspects(Level level, BlockPos pos) {
    return AspectContainerHelper.getAspectContainer(level, pos).map(IAspectContainer::getAspects);
  }

  public static Optional<IAspectContainer> getAspectContainer(Level level, BlockPos pos) {
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

  public static Optional<IAspectContainer> getAspectContainer(ItemStack itemStack) {
    return Optional.ofNullable(itemStack.getCapability(T7Capabilities.AspectContainer.ITEM));
  }

  public static boolean isAspectContainer(Level level, BlockPos blockPos) {
    return level.getCapability(T7Capabilities.AspectContainer.BLOCK, blockPos) != null;
  }

  public static boolean isFull(ItemStack itemStack) {
    var cap = itemStack.getCapability(T7Capabilities.AspectContainer.ITEM);
    if (cap == null) return true;

    return cap.getAspects().contains(AspectMap.ofPrimals(cap.getMaxAmount()));
  }

  public static boolean isEmpty(Level level, BlockPos pos) {
    return AspectContainerHelper.getAspects(level, pos).map(AspectMap::isEmpty).orElse(true);
  }

  public static Optional<Pair> blockSourceItemSink(Level level, BlockPos blockPos, ItemStack itemStack) {
    return getAspectContainer(itemStack).flatMap(sink ->
        getAspectContainer(level, blockPos).map(source -> new Pair(source, sink))
    );
  }

  public static class Pair {
    private final IAspectContainer source;
    private final IAspectContainer sink;

    public Pair(IAspectContainer source, IAspectContainer sink) {
      this.source = source;
      this.sink = sink;
    }

    protected int simulateTransfer(Aspect a, int idealAmount) {
      int maxInsert = this.sink.getMaxAmount() - this.sink.getAspects().get(a);
      if (maxInsert <= 0) return 0;
      int maxExtract = this.source.getAspects().get(a);
      if (maxExtract <= 0) return 0;

      return Math.min(Math.min(idealAmount, maxInsert), maxExtract);
    }

    public boolean canTransferPrimals() {
      return Aspects.PRIMAL_ASPECTS.stream()
          .map(a -> this.simulateTransfer(a.get(), 1))
          .anyMatch(e -> e > 0);
    }

    public void transferPrimal(int indexOffset, int idealAmount) {
      var primals = Aspects.PRIMAL_ASPECTS.size();
      for (int i = 0; i < primals; i++) {
        var a = Aspects.PRIMAL_ASPECTS.get((i + indexOffset) % primals).get();
        int amount = this.simulateTransfer(a, idealAmount);
        if (amount == 0) continue;
        this.sink.insert(a, amount);
        this.source.extract(a, amount);
        break;
      }
    }
  }
}
