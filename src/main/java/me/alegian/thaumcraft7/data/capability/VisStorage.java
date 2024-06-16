package me.alegian.thaumcraft7.data.capability;

import me.alegian.thaumcraft7.api.capability.IVisStorage;
import me.alegian.thaumcraft7.data.attachment.TCAttachments;
import me.alegian.thaumcraft7.data.component.TCDataComponents;
import me.alegian.thaumcraft7.data.component.VisDataComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentHolder;

public class VisStorage implements IVisStorage{
    private final float maxVis;
    private final AttachmentHolder attachmentHolder;
    private final ItemStack itemStack;

    public VisStorage(float maxVis, AttachmentHolder holder, ItemStack itemStack) {
        this.maxVis = maxVis;
        this.attachmentHolder = holder;
        this.itemStack = itemStack;
    }

    public VisStorage(float maxVis, AttachmentHolder holder){
        this(maxVis, holder, null);
    }

    public VisStorage(float maxVis, ItemStack itemStack) {
        this(maxVis, null, itemStack);
    }

    @Override
    public float extractVis(float maxExtract) {
        float visExtracted = 0;

        if(attachmentHolder != null) {
            var attachment = attachmentHolder.getData(TCAttachments.VIS);
            visExtracted = Math.min(attachment.vis, maxExtract);
            attachment.vis -= visExtracted;
            attachmentHolder.setData(TCAttachments.VIS, attachment);
        } else if (itemStack != null) {
            var component = itemStack.get(TCDataComponents.VIS);
            if(component != null) {
                visExtracted = Math.min(component.vis(), maxExtract);
                itemStack.set(TCDataComponents.VIS, new VisDataComponent.Vis(component.vis() - visExtracted));
            } else {
                itemStack.set(TCDataComponents.VIS, new VisDataComponent.Vis());
            }
        }

        return visExtracted;
    }

    @Override
    public float receiveVis(float maxReceive) {
        float visReceived = 0;

        if(attachmentHolder != null) {
            var attachment = attachmentHolder.getData(TCAttachments.VIS);
            visReceived = Math.min(maxVis - attachment.vis, maxReceive);
            attachment.vis += visReceived;
            attachmentHolder.setData(TCAttachments.VIS, attachment);
        } else if (itemStack != null) {
            var component = itemStack.get(TCDataComponents.VIS);
            if(component != null) {
                visReceived = Math.min(maxVis - component.vis(), maxReceive);
                itemStack.set(TCDataComponents.VIS, new VisDataComponent.Vis(component.vis() + visReceived));
            } else {
                itemStack.set(TCDataComponents.VIS, new VisDataComponent.Vis());
            }
        }

        return visReceived;
    }

    @Override
    public float getVisStored() {
        float visStored = 0;

        if(attachmentHolder != null) {
            var attachment = attachmentHolder.getData(TCAttachments.VIS);
            visStored = attachment.vis;
        } else if (itemStack != null) {
            var component = itemStack.get(TCDataComponents.VIS);
            if(component != null) visStored = component.vis();
        }

        return visStored;
    }

    @Override
    public float getMaxVisStored() {
        return maxVis;
    }
}
