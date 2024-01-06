package me.alegian.thaumcraft7.capability;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class VisStorage implements IVisStorage{
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
        float extract = vis - maxExtract < 0 ? vis : maxExtract;
        this.vis -= extract;
        return extract;
    }

    @Override
    public float receiveVis(float maxReceive) {
        float receive = vis + maxReceive > maxVis ? maxVis-vis : maxReceive;
        this.vis += receive;
        return receive;
    }

    @Override
    public float getVisStored() {
        return vis;
    }

    @Override
    public float getMaxVisStored() {
        return maxVis;
    }
}
