package me.alegian.thaumcraft7.impl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class VisEntity extends RendererEntity {
  private final Player player;
  private final BlockPos blockPos;

  public VisEntity(Level pLevel, Player player, BlockPos blockPos) {
    super(pLevel, blockPos.getCenter());
    this.player = player;
    this.blockPos = blockPos;
  }

  public Player getPlayer() {
    return player;
  }

  public BlockPos getBlockPos() {
    return blockPos;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {

  }

  @Override
  protected void addAdditionalSaveData(CompoundTag pCompound) {

  }
}
