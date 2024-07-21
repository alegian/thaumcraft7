package me.alegian.thaumcraft7.blockentity;

import me.alegian.thaumcraft7.data.capability.CrucibleFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrucibleBE extends BlockEntity {
    private CrucibleFluidHandler fluidHandler;

    public CrucibleBE(BlockPos pos, BlockState blockState) {
        super(TCBlockEntities.CRUCIBLE.get(), pos, blockState);
        this.fluidHandler = new CrucibleFluidHandler();
    }

    public CrucibleFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    // Data Handling Section
    public void clientSync() {
        if (Objects.requireNonNull(this.getLevel()).isClientSide) {
            return;
        }
        ServerLevel world = (ServerLevel) this.getLevel();
        Stream<ServerPlayer> entities = world.getChunkSource().chunkMap.getPlayers(new ChunkPos(this.worldPosition), false).stream();
        ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
        entities.forEach(e -> {
            if (updatePacket != null) {
                e.connection.send(updatePacket);
            }
        });
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
