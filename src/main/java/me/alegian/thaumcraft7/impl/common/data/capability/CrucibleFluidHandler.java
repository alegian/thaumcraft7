package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.impl.common.block.entity.CrucibleBE;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class CrucibleFluidHandler extends FluidTank {
  private final CrucibleBE crucibleBE;

  public CrucibleFluidHandler(CrucibleBE crucibleBE) {
    super(FluidType.BUCKET_VOLUME, (fluidStack) -> fluidStack.is(Fluids.WATER));
    this.crucibleBE = crucibleBE;
  }

  @Override
  public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
    return FluidStack.EMPTY;
  }

  @Override
  protected void onContentsChanged() {
    this.crucibleBE.setChanged();
  }

  // returns true if any water was drained
  public boolean catalystDrain() {
    if (this.isEmpty()) return false;

    int maxDrain = FluidType.BUCKET_VOLUME / 4;
    this.fluid.shrink(Math.min(maxDrain, this.fluid.getAmount()));
    this.onContentsChanged();
    return true;
  }

  // returns true if any water was filled
  public boolean fillUp() {
    if (this.getSpace() == 0) return false;

    this.fluid = new FluidStack(Fluids.WATER, FluidType.BUCKET_VOLUME);
    this.onContentsChanged();
    return true;
  }
}
