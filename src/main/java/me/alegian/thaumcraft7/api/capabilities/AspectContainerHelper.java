package me.alegian.thaumcraft7.api.capabilities;

import me.alegian.thaumcraft7.api.aspects.AspectList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class AspectContainerHelper {
    /*
     * always check hasAspects before using
     */
    public static AspectList getAspects(Level level, BlockPos pos){
        return Objects.requireNonNull(level.getCapability(TCCapabilities.AspectContainer.BLOCK, pos, null)).getAspects();
    }

    public static boolean hasAspects(Level level, BlockPos pos){
        return level.getCapability(TCCapabilities.AspectContainer.BLOCK, pos, null) != null;
    }
}
