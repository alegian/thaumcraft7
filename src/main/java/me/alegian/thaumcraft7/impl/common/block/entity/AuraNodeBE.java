package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7DataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

public class AuraNodeBE extends DataComponentBE {
  public static final int MAX_COUNTDOWN = 60;
  private List<BlockPos> glassPositions = new ArrayList<>();
  private List<BlockPos> slabPositions = new ArrayList<>();
  private int containingCountdown = -1;

  public AuraNodeBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.AURA_NODE.get(), pos, blockState);
    generateGlassPositions();
    generateSlabPositions();
  }

  public static void tick(Level level, BlockPos pos, BlockState state, AuraNodeBE blockEntity) {
    var countdown = blockEntity.getContainingCountdown();
    if (countdown == 0) blockEntity.contain();
    else if (countdown > 0) blockEntity.decrementContainingCountdown();
  }

  @Override
  public void onLoad() {
    if (!this.getLevel().isClientSide()) {
      var aspects = get(T7DataComponents.ASPECTS.get());
      if (aspects == null) {
        set(T7DataComponents.ASPECTS.get(), AspectMap.randomPrimals());
      }
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }
  }

  /**
   * Cached possible positions of glass.
   * Used for contained aura node interactions.
   * 3x3x3 except center
   */
  public void generateGlassPositions() {
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        for (int k = -1; k <= 1; k++) {
          if (i == 0 && j == 0 && k == 0) continue;
          glassPositions.add(getBlockPos().offset(i, j, k));
        }
      }
    }
  }

  /**
   * Cached possible positions of wooden slabs.
   * Used for contained aura node interactions.
   * 3x1x3, above the center
   */
  public void generateSlabPositions() {
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        slabPositions.add(getBlockPos().offset(i, 2, j));
      }
    }
  }

  /**
   * Returns true if the interaction was successful
   */
  public boolean jarInteraction() {
    // check if glass exists
    for (var pos : glassPositions) {
      if (!level.getBlockState(pos).is(Tags.Blocks.GLASS_BLOCKS)) return false;
    }
    // check if slabs exist, and are bottom slabs
    for (var pos : slabPositions) {
      var blockState = level.getBlockState(pos);
      if (!blockState.is(BlockTags.WOODEN_SLABS) || blockState.getValue(BlockStateProperties.SLAB_TYPE) != SlabType.BOTTOM)
        return false;
    }

    if (!level.isClientSide() && level instanceof ServerLevel) {
      for (var pos : glassPositions) {
        level.removeBlock(pos, false);
      }
      for (var pos : slabPositions) {
        level.removeBlock(pos, false);
      }
    }

    containingCountdown = MAX_COUNTDOWN;

    return true;
  }

  public void contain() {
    containingCountdown = -1;
    level.removeBlock(this.getBlockPos(), false);
    level.addFreshEntity(new ItemEntity(
        level,
        this.getBlockPos().getX() + 0.5,
        this.getBlockPos().getY() + 0.5,
        this.getBlockPos().getZ() + 0.5,
        new ItemStack(T7Blocks.AURA_NODE_ITEM.get())));
  }

  /**
   * The countdown in ticks after which a node will break
   * itself and drop a contained aura node.
   */
  public int getContainingCountdown() {
    return containingCountdown;
  }

  public void decrementContainingCountdown() {
    containingCountdown--;
  }

  @Override
  public DataComponentType<?>[] getComponentTypes() {
    return new DataComponentType[]{T7DataComponents.ASPECTS.get()};
  }
}
