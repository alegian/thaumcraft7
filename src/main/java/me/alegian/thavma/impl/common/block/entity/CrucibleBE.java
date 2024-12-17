package me.alegian.thavma.impl.common.block.entity;

import me.alegian.thavma.impl.client.particle.CrucibleBubbleParticle;
import me.alegian.thavma.impl.common.block.T7BlockStateProperties;
import me.alegian.thavma.impl.common.data.capability.CrucibleFluidHandler;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7DataComponents;
import me.alegian.thavma.impl.init.registries.deferred.T7ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrucibleBE extends DataComponentBE {
  private final CrucibleFluidHandler fluidHandler;
  private final List<CrucibleBubbleParticle> particles = new ArrayList<>();

  public CrucibleBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.INSTANCE.getCRUCIBLE().get(), pos, blockState);
    this.fluidHandler = new CrucibleFluidHandler(this);
  }

  public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
    if (level instanceof ServerLevel serverLevel
        && state.getValue(T7BlockStateProperties.BOILING)
        && serverLevel.getGameTime() % 7 == 0
        && blockEntity instanceof CrucibleBE crucibleBE
        && !crucibleBE.getFluidHandler().isEmpty()
    ) serverLevel.sendParticles(
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

  public CrucibleFluidHandler getFluidHandler() {
    return this.fluidHandler;
  }

  public double getWaterHeight() {
    int amount = this.fluidHandler.getFluidAmount();
    int total = this.fluidHandler.getCapacity();
    double percent = (amount / (double) total);

    return 3 / 16f + 12 / 16f * percent;
  }

  public void addParticle(CrucibleBubbleParticle particle) {
    this.particles.add(particle);
  }

  public void removeParticle(CrucibleBubbleParticle particle) {
    this.particles.remove(particle);
  }

  public void clearParticles() {
    this.particles.forEach(CrucibleBubbleParticle::scheduleRemove);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
    // empty fluidstacks make empty tags, which are normally not handled
    // however we need to still update when tank empties
    this.loadAdditional(Objects.requireNonNull(pkt.getTag()), lookupProvider);
    // delete floating particles from previous water level
    this.clearParticles();
  }

  @Override
  public DataComponentType<?>[] getComponentTypes() {
    return new DataComponentType[]{T7DataComponents.INSTANCE.getASPECTS().get()};
  }

  @Override
  protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.loadAdditional(pTag, pRegistries);
    this.fluidHandler.readFromNBT(pRegistries, pTag);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.saveAdditional(pTag, pRegistries);
    this.fluidHandler.writeToNBT(pRegistries, pTag);
  }
}
