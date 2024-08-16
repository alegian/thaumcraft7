package me.alegian.thaumcraft7.api.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class AspectContainerHelper {
  public static boolean addRandomAspect(Level level, BlockPos pos) {
    Optional<IAspectContainer> aspectContainer = getAspectContainer(level, pos);
    return aspectContainer.map(
        container -> container.addAspect(Aspect.getRandomAspect(), 1)
    ).orElse(false);
  }

  public static Optional<AspectList> getAspects(Level level, BlockPos pos) {
    return getAspectContainer(level, pos).map(IAspectContainer::getAspects);
  }

  public static Optional<IAspectContainer> getAspectContainer(Level level, BlockPos pos) {
    return Optional.ofNullable(level.getCapability(T7Capabilities.AspectContainer.BLOCK, pos, null));
  }

  public static IAspectContainer getAspectContainerInHand(Player player) {
    var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
    var offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);

    IAspectContainer aspectContainer = null;

    if (!mainHandItem.isEmpty())
      aspectContainer = mainHandItem.getCapability(T7Capabilities.AspectContainer.ITEM, null);
    else if (!offHandItem.isEmpty())
      aspectContainer = offHandItem.getCapability(T7Capabilities.AspectContainer.ITEM, null);

    return aspectContainer;
  }

  public static boolean isAspectContainer(Level level, BlockPos blockPos) {
    return level.getCapability(T7Capabilities.AspectContainer.BLOCK, blockPos) != null;
  }
}
