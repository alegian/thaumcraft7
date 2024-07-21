package me.alegian.thaumcraft7.data.capability;

import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class CrucibleFluidHandler extends FluidTank {
    public CrucibleFluidHandler() {
        super(FluidType.BUCKET_VOLUME, (fluidStack)-> fluidStack.is(Fluids.WATER));
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    // returns true if any water was drained
    public boolean catalystDrain(){
        if(isEmpty()) return false;

        int maxDrain = FluidType.BUCKET_VOLUME/4;
        fluid.shrink(Math.min(maxDrain, fluid.getAmount()));
        onContentsChanged();
        return true;
    }
}
