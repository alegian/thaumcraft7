package me.alegian.thaumcraft7.data.capability;

import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class CrucibleFluidHandler extends FluidTank {
    private final CrucibleBE crucibleBE;

    public CrucibleFluidHandler(CrucibleBE crucibleBE) {
        super(FluidType.BUCKET_VOLUME, (fluidStack)-> fluidStack.is(Fluids.WATER));
        this.crucibleBE = crucibleBE;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Override
    protected void onContentsChanged() {
        crucibleBE.setChanged();
        crucibleBE.clientSync();
    }

    // returns true if any water was drained
    public boolean catalystDrain(){
        if(isEmpty()) return false;

        int maxDrain = FluidType.BUCKET_VOLUME/4;
        fluid.shrink(Math.min(maxDrain, fluid.getAmount()));
        onContentsChanged();
        return true;
    }

    public boolean fillUp(){
        if(getSpace() == 0) return false;

        fluid = new FluidStack(Fluids.WATER, FluidType.BUCKET_VOLUME);
        onContentsChanged();
        return true;
    }
}
