package me.alegian.thaumcraft7.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class CrucibleBE extends BlockEntity {
    private FluidTank fluidTank;

    public CrucibleBE(BlockPos pos, BlockState blockState) {
        super(TCBlockEntities.CRUCIBLE.get(), pos, blockState);
        this.fluidTank = new FluidTank(FluidType.BUCKET_VOLUME, (fluidStack)-> fluidStack.is(Fluids.WATER)){
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                return FluidStack.EMPTY;
            }
        };
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        fluidTank.readFromNBT(pRegistries, pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        fluidTank.writeToNBT(pRegistries, pTag);
    }
}
