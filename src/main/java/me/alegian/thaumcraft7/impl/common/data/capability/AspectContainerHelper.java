package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.common.block.entity.BEHelper;
import me.alegian.thaumcraft7.impl.init.registries.T7Capabilities;
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

  public static void fromBlockToItem(Level level, BlockPos sourceBlockPos, ItemStack dest, int amount) {
    AspectContainerHelper.getAspectContainer(dest)
        .flatMap(wandContainer ->
            AspectContainerHelper.getAspectContainer(level, sourceBlockPos)
                .map(nodeContainer -> {
                  var aspectStack = nodeContainer.extractRandom(amount);
                  wandContainer.insert(aspectStack);
                  return !aspectStack.isEmpty();
                }).filter(e -> e))
        .ifPresent($ -> BEHelper.updateBlockEntity(level, sourceBlockPos));
  }
}
