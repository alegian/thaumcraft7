package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Entity that only Renders as a spiral beam between the Player and an Aura Node.
 */
public class VisEntity extends RendererEntity {
  public static final String PLAYER_TAG = "player";
  @Nullable
  private UUID playerUUID; // save player UUID and not entire Player, because when deserializing, level.players is not yet populated

  public VisEntity(Level pLevel, Player player) {
    this(pLevel, player, new BlockPos(0, 0, 0));
  }

  public VisEntity(Level pLevel, @Nullable Player player, @NotNull BlockPos blockPos) {
    super(pLevel, blockPos.getCenter());
    if (player != null) this.playerUUID = player.getUUID();
  }

  /**
   * Only ticks Serverside, every 5 ticks. Kills itself if the player is not using the Wand.
   */
  @Override
  public void tick() {
    if (this.level().isClientSide()) return;
    if (tickCount % 5 != 0) return;
    var player = getPlayer();

    if (player == null || !player.isUsingItem() || !(player.getUseItem().getItem() instanceof WandItem)) {
      this.kill();
    }else{
      var aspectContainer = AspectContainerHelper.getAspectContainerInHand(player);
      aspectContainer.addAspect(Aspect.IGNIS, 5);
    }
  }

  public @Nullable Player getPlayer() {
    if (playerUUID == null) return null;
    return this.level().getPlayerByUUID(playerUUID);
  }

  public @Nullable UUID getPlayerUUID() {
    return playerUUID;
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {
    if (pCompound.hasUUID(PLAYER_TAG)) {
      this.playerUUID = pCompound.getUUID(PLAYER_TAG);
    }
  }

  @Override
  protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    if (playerUUID == null) return;
    pCompound.putUUID(PLAYER_TAG, playerUUID);
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
