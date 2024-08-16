package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AuraNodeBE extends BlockEntity {
  private final IAspectContainer aspectContainer = new AspectContainer(AspectList.of(Aspect.IGNIS, 12));

  public AuraNodeBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.AURA_NODE.get(), pos, blockState);
  }

  public IAspectContainer getAspectContainer() {
    return aspectContainer;
  }
}
