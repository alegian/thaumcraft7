package me.alegian.thaumcraft7.blockentity;

import me.alegian.thaumcraft7.data.capability.CrucibleFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Objects;

public class CrucibleBE extends BlockEntity {
  private final CrucibleFluidHandler fluidHandler;

  public CrucibleBE(BlockPos pos, BlockState blockState) {
    super(TCBlockEntities.CRUCIBLE.get(), pos, blockState);
    this.fluidHandler = new CrucibleFluidHandler(this);
  }

  public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
    if (level instanceof ServerLevel serverLevel
        && serverLevel.getGameTime() % 5 == 0
        && blockEntity instanceof CrucibleBE crucibleBE
        && !crucibleBE.getFluidHandler().isEmpty()
    ) {
      serverLevel.sendParticles(
          ParticleTypes.BUBBLE,
          (double) pos.getX() + serverLevel.random.nextDouble(),
          pos.getY() + crucibleBE.getWaterHeight(),
          (double) pos.getZ() + serverLevel.random.nextDouble(),
          1,
          0.0,
          0.00,
          0.0,
          0.0
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

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
    // empty fluidstacks make empty tags, which are normally not handled
    // however we need to still update when tank empties
    this.loadAdditional(Objects.requireNonNull(pkt.getTag()), lookupProvider);
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
  }

  @Override
  protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.saveAdditional(pTag, pRegistries);
    fluidHandler.writeToNBT(pRegistries, pTag);
  }
}
