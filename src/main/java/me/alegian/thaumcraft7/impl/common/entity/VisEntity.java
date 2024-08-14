package me.alegian.thaumcraft7.impl.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VisEntity extends RendererEntity {
  @Nullable
  private Player player;
  private final BlockPos blockPos;

  public VisEntity(Level pLevel, Player player) {
    this(pLevel, player, new BlockPos(0, 0, 0));
  }

  public VisEntity(Level pLevel, @Nullable Player player, @NotNull BlockPos blockPos) {
    super(pLevel, blockPos.getCenter());
    this.player = player;
    this.blockPos = blockPos;
  }

  public @Nullable Player getPlayer() {
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
    if (pCompound.hasUUID("player")) {
      var playerUUID = pCompound.getUUID("player");

      player = this.level().getPlayerByUUID(playerUUID);
    }
  }

  @Override
  protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    if (player != null) {
      pCompound.putUUID("player", player.getUUID());
    }
  }

  @Override
  public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity pEntity) {
    Entity player = this.getPlayer();
    return new ClientboundAddEntityPacket(this, pEntity, player == null ? 0 : player.getId());
  }

  @Override
  public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
    Entity entity = this.level().getEntity(pPacket.getData());
    if (entity instanceof Player player) {
      this.player = player;
    }
  }
}
