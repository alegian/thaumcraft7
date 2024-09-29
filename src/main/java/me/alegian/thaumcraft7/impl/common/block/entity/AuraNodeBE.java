package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.impl.common.aspect.AspectList;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class AuraNodeBE extends DataComponentBE {
  private List<BlockPos> glassPositions = new ArrayList<>();
  public AuraNodeBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.AURA_NODE.get(), pos, blockState);
    generateGlassPositions();
  }

  @Override
  public void onLoad() {
    if (!this.getLevel().isClientSide()) {
      var aspects = get(T7DataComponents.ASPECTS.get());
      if (aspects == null) {
        set(T7DataComponents.ASPECTS.get(), AspectList.randomPrimals());
      }
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }
  }

  /**
   * Cached possible positions of glass.
   * Used for node-in-a-jar interactions.
   * 3x3x3 except center
   */
  public void generateGlassPositions(){
    for(int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        for(int k = -1; k <= 1; k++) {
          if(i == 0 && j == 0 && k == 0) continue;
          glassPositions.add(getBlockPos().offset(i, j, k));
        }
      }
    }
  }

  /**
   * Returns true if the interaction was successful
   */
  public boolean jarInteraction() {
    // check if glass exists
    for(var pos : glassPositions) {
        if(!level.getBlockState(pos).is(Tags.Blocks.GLASS_BLOCKS)) return false;
    }

    if (!level.isClientSide() && level instanceof ServerLevel) {
      for(var pos : glassPositions) {
        level.removeBlock(pos, false);
      }
    }

    return true;
  }

  @Override
  public DataComponentType<?>[] getComponentTypes() {
    return new DataComponentType[]{T7DataComponents.ASPECTS.get()};
  }
}
