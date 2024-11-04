package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainerHelper;
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
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Entity that renders as a spiral beam between the Player and an Aura Node.
 * Transfers vis from the target block to the held wand by ticking.
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
   * Ticks once every 5 ticks. Kills itself if the player is not using the Wand.
   * Interrupts item use when the wand is full.
   */
  @Override
  public void tick() {
    if (this.tickCount % 5 != 0) return;
    var player = this.getPlayer();
    if (player != null && AspectContainerHelper.isFull(player.getUseItem())) player.stopUsingItem();
    if (this.level().isClientSide()) return;
    this.serverTick(player);
  }

  private void serverTick(Player player) {
    if (player == null || !player.isUsingItem() || !(player.getUseItem().getItem() instanceof WandItem)) {
      this.kill();
      return;
    }

    var wandContainer = AspectContainerHelper.getAspectContainerInHand(player);
    if (wandContainer == null) return;
    AspectContainerHelper.getAspectContainer(this.level(), this.blockPosition()).ifPresent(nodeContainer -> {
      var insert = nodeContainer.extractRandom(5);
      var be = this.level().getBlockEntity(this.blockPosition());
      if (be != null) {
        be.setChanged();
        this.level().sendBlockUpdated(this.blockPosition(), be.getBlockState(), be.getBlockState(), Block.UPDATE_CLIENTS);
      }
      if (insert == null) return;
      wandContainer.insert(insert);
    });
  }

  public @Nullable Player getPlayer() {
    if (this.playerUUID == null) return null;
    return this.level().getPlayerByUUID(this.playerUUID);
  }

  public @Nullable UUID getPlayerUUID() {
    return this.playerUUID;
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {
    if (pCompound.hasUUID(VisEntity.PLAYER_TAG)) this.playerUUID = pCompound.getUUID(VisEntity.PLAYER_TAG);
  }

  @Override
  protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    if (this.playerUUID == null) return;
    pCompound.putUUID(VisEntity.PLAYER_TAG, this.playerUUID);
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
    if (entity instanceof Player player) this.playerUUID = player.getUUID();
  }

  @Override
  public void restoreFrom(@NotNull Entity pEntity) {
    super.restoreFrom(pEntity);
    this.playerUUID = ((VisEntity) pEntity).getPlayerUUID();
  }
}
