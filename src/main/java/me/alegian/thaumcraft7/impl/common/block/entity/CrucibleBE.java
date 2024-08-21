package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thaumcraft7.impl.common.block.T7BlockStateProperties;
import me.alegian.thaumcraft7.impl.common.data.capability.CrucibleFluidHandler;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrucibleBE extends BlockEntity {
  private final CrucibleFluidHandler fluidHandler;
  private final List<CrucibleBubbleParticle> particles = new ArrayList<>();

  public CrucibleBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.CRUCIBLE.get(), pos, blockState);
    this.fluidHandler = new CrucibleFluidHandler(this);
  }

  public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
    if (level instanceof ServerLevel serverLevel
        && state.getValue(T7BlockStateProperties.BOILING)
        && serverLevel.getGameTime() % 7 == 0
        && blockEntity instanceof CrucibleBE crucibleBE
        && !crucibleBE.getFluidHandler().isEmpty()
    ) {
      serverLevel.sendParticles(
          T7ParticleTypes.CRUCIBLE_BUBBLE.get(),
          (double) pos.getX() + serverLevel.random.nextDouble() * 12 / 16f + 2 / 16f,
          pos.getY() + crucibleBE.getWaterHeight(),
          (double) pos.getZ() + serverLevel.random.nextDouble() * 12 / 16f + 2 / 16f,
          1,
          0,
          0,
          0,
          0
      );
    }
  }

  public CrucibleFluidHandler getFluidHandler() {
    return fluidHandler;
  }

  public double getWaterHeight() {
    int amount = fluidHandler.getFluidAmount();
    int total = fluidHandler.getCapacity();
    double percent = (amount / (double) total);

    return 3 / 16f + 12 / 16f * percent;
  }

  public void addParticle(CrucibleBubbleParticle particle) {
    particles.add(particle);
  }

  public void removeParticle(CrucibleBubbleParticle particle) {
    particles.remove(particle);
  }

  public void clearParticles() {
    particles.forEach(CrucibleBubbleParticle::scheduleRemove);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
    // empty fluidstacks make empty tags, which are normally not handled
    // however we need to still update when tank empties
    this.loadAdditional(Objects.requireNonNull(pkt.getTag()), lookupProvider);
    // delete floating particles from previous water level
    clearParticles();
  }

  @Nullable
  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
    CompoundTag tag = new CompoundTag();
    saveAdditional(tag, pRegistries);
    return tag;
  }

  @Override
  protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.loadAdditional(pTag, pRegistries);
    fluidHandler.readFromNBT(pRegistries, pTag);
    aspectContainer.readFromNBT(pRegistries, pTag);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.saveAdditional(pTag, pRegistries);
    fluidHandler.writeToNBT(pRegistries, pTag);
    aspectContainer.writeToNBT(pRegistries, pTag);
  }
}
