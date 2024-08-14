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

import java.util.UUID;

public class VisEntity extends RendererEntity {
  @Nullable
  private UUID playerUUID;

  public VisEntity(Level pLevel, Player player) {
    this(pLevel, player, new BlockPos(0, 0, 0));
  }

  public VisEntity(Level pLevel, @Nullable Player player, @NotNull BlockPos blockPos) {
    super(pLevel, blockPos.getCenter());
    if (player != null) this.playerUUID = player.getUUID();
  }

  public @Nullable Player getPlayer() {
    if (playerUUID == null) return null;
    return this.level().getPlayerByUUID(playerUUID);
  }

  public @Nullable UUID getPlayerUUID() {
    return playerUUID;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {
    if (pCompound.hasUUID("player")) {
      this.playerUUID = pCompound.getUUID("player");
    }
  }

  @Override
  protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    if (playerUUID == null) return;
    pCompound.putUUID("player", playerUUID);
  }

  @Override
  public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity pEntity) {
    Entity player = this.getPlayer();
    return new ClientboundAddEntityPacket(this, pEntity, player == null ? 0 : player.getId());
  }

  @Override
  public void recreateFromPacket(@NotNull ClientboundAddEntityPacket pPacket) {
    super.recreateFromPacket(pPacket);
    Entity entity = this.level().getEntity(pPacket.getData());
    if (entity instanceof Player player) {
      this.playerUUID = player.getUUID();
    }
  }

  @Override
  public void restoreFrom(@NotNull Entity pEntity) {
    super.restoreFrom(pEntity);
    this.playerUUID = ((VisEntity) pEntity).getPlayerUUID();
  }
}
