package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AuraNodeBE extends BlockEntity {

  public AuraNodeBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.AURA_NODE.get(), pos, blockState);
  }
}
