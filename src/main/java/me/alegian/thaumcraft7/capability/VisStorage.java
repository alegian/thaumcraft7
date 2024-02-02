package me.alegian.thaumcraft7.capability;

import me.alegian.thaumcraft7.api.capabilities.IVisStorage;
import me.alegian.thaumcraft7.attachment.TCAttachments;
import net.neoforged.neoforge.attachment.AttachmentHolder;

public class VisStorage implements IVisStorage{
    private final float maxVis;
    private final AttachmentHolder holder;

    public VisStorage(float maxVis, AttachmentHolder holder){
        this.maxVis = maxVis;
        this.holder = holder;
    }

    @Override
    public float extractVis(float maxExtract) {
        var attachment = holder.getData(TCAttachments.VIS);

        float visExtracted = Math.min(attachment.vis, maxExtract);
        attachment.vis -= visExtracted;

        holder.setData(TCAttachments.VIS, attachment);

        return visExtracted;
    }

    @Override
    public float receiveVis(float maxReceive) {
        var attachment = holder.getData(TCAttachments.VIS);

        float energyReceived = Math.min(maxVis - attachment.vis, maxReceive);
        attachment.vis += energyReceived;

        holder.setData(TCAttachments.VIS, attachment);

        return energyReceived;
    }

    @Override
    public float getVisStored() {
        return holder.getData(TCAttachments.VIS).vis;
    }

    @Override
    public float getMaxVisStored() {
        return maxVis;
    }
}
