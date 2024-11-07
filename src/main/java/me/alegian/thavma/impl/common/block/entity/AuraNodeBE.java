package me.alegian.thavma.impl.common.block.entity;

import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import me.alegian.thavma.impl.init.registries.deferred.T7DataComponents;
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
    this.generateGlassPositions();
    this.generateSlabPositions();
  }

  public static void tick(Level level, BlockPos pos, BlockState state, AuraNodeBE blockEntity) {
    var countdown = blockEntity.getContainingCountdown();
    if (countdown == 0) blockEntity.contain();
    else if (countdown > 0) blockEntity.decrementContainingCountdown();
  }

  @Override
  public void onLoad() {
    if (!this.getLevel().isClientSide()) {
      var aspects = this.get(T7DataComponents.ASPECTS.get());
      if (aspects == null) this.set(T7DataComponents.ASPECTS.get(), AspectMap.randomPrimals());
      this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
      this.setChanged();
    }
  }

  /**
   * Cached possible positions of glass.
   * Used for contained aura node interactions.
   * 3x3x3 except center
   */
  public void generateGlassPositions() {
    for (int i = -1; i <= 1; i++)
      for (int j = -1; j <= 1; j++)
        for (int k = -1; k <= 1; k++) {
          if (i == 0 && j == 0 && k == 0) continue;
          this.glassPositions.add(this.getBlockPos().offset(i, j, k));
        }
  }

  /**
   * Cached possible positions of wooden slabs.
   * Used for contained aura node interactions.
   * 3x1x3, above the center
   */
  public void generateSlabPositions() {
    for (int i = -1; i <= 1; i++)
      for (int j = -1; j <= 1; j++) this.slabPositions.add(this.getBlockPos().offset(i, 2, j));
  }

  /**
   * Returns true if the interaction was successful
   */
  public boolean jarInteraction() {
    // check if glass exists
    for (var pos : this.glassPositions) if (!this.level.getBlockState(pos).is(Tags.Blocks.GLASS_BLOCKS)) return false;
    // check if slabs exist, and are bottom slabs
    for (var pos : this.slabPositions) {
      var blockState = this.level.getBlockState(pos);
      if (!blockState.is(BlockTags.WOODEN_SLABS) || blockState.getValue(BlockStateProperties.SLAB_TYPE) != SlabType.BOTTOM)
        return false;
    }

    if (!this.level.isClientSide() && this.level instanceof ServerLevel) {
      for (var pos : this.glassPositions) this.level.removeBlock(pos, false);
      for (var pos : this.slabPositions) this.level.removeBlock(pos, false);
    }

    this.containingCountdown = AuraNodeBE.MAX_COUNTDOWN;

    return true;
  }

  public void contain() {
    this.containingCountdown = -1;
    this.level.removeBlock(this.getBlockPos(), false);
    this.level.addFreshEntity(new ItemEntity(
        this.level,
        this.getBlockPos().getX() + 0.5,
        this.getBlockPos().getY() + 0.5,
        this.getBlockPos().getZ() + 0.5,
        new ItemStack(T7Blocks.AURA_NODE.get())));
  }

  /**
   * The countdown in ticks after which a node will break
   * itself and drop a contained aura node.
   */
  public int getContainingCountdown() {
    return this.containingCountdown;
  }

  public void decrementContainingCountdown() {
    this.containingCountdown--;
  }

  @Override
  public DataComponentType<?>[] getComponentTypes() {
    return new DataComponentType[]{T7DataComponents.ASPECTS.get()};
  }
}
