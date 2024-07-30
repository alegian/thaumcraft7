package me.alegian.thaumcraft7.api.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import net.minecraft.core.BlockPos;
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
}
