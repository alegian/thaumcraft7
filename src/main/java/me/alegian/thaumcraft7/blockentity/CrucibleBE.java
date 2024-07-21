package me.alegian.thaumcraft7.blockentity;

import me.alegian.thaumcraft7.data.capability.CrucibleFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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

    public CrucibleFluidHandler getFluidHandler() {
        return fluidHandler;
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
