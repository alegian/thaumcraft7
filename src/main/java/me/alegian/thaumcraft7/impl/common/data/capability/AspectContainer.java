package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class AspectContainer implements IAspectContainer {
  private final AspectList contents;

  public AspectContainer(AspectList contents) {
    this.contents = contents;
  }

  public AspectContainer() {
    this.contents = new AspectList();
  }

  @Override
  public AspectList getAspects() {
    return contents;
  }

  @Override
  public boolean addAspect(Aspect aspect, int amount) {
    contents.add(aspect, amount);
    return true;
  }

  public AspectContainer readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    fluid = FluidStack.parseOptional(lookupProvider, nbt.getCompound("Fluid"));
    return this;
  }

  public CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
    if (!fluid.isEmpty()) {
      nbt.put("Fluid", fluid.save(lookupProvider));
    }

    return nbt;
  }
}
