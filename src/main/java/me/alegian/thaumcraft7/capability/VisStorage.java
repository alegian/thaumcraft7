package me.alegian.thaumcraft7.capability;

import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class VisStorage implements IVisStorage, INBTSerializable<Tag> {
    private float vis;
    private final float maxVis;

    public static final ItemCapability<IVisStorage, Void> ITEM = ItemCapability.createVoid(
        new ResourceLocation("thaumcraft7", "vis_storage"),
        IVisStorage.class
    );

    public VisStorage(float maxVis){
        this.vis=0;
        this.maxVis = maxVis;
    }

    @Override
    public float extractVis(float maxExtract) {
        float visExtracted = Math.min(vis, maxExtract);
        vis -= visExtracted;
        return visExtracted;
    }

    @Override
    public float receiveVis(float maxReceive) {
        float energyReceived = Math.min(maxVis - vis, maxReceive);
        vis += energyReceived;
        return energyReceived;
    }

    @Override
    public float getVisStored() {
        return vis;
    }

    @Override
    public float getMaxVisStored() {
        return maxVis;
    }

    @Override
    public Tag serializeNBT() {
        return FloatTag.valueOf(getVisStored());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof FloatTag floatNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        vis = floatNbt.getAsFloat();
    }
}
