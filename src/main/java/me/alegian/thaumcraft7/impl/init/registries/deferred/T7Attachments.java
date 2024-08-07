package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.FloatTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class T7Attachments {
  public static final DeferredRegister<AttachmentType<?>> REGISTRAR = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Thaumcraft.MODID);

  public static class Vis implements INBTSerializable<FloatTag> {
    public float vis = 0;

    @Override
    public FloatTag serializeNBT(HolderLookup.Provider provider) {
      return FloatTag.valueOf(vis);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, FloatTag nbt) {
      vis = nbt.getAsFloat();
    }
  }

  public static final Supplier<AttachmentType<Vis>> VIS = REGISTRAR.register(
      "vis", () -> AttachmentType.serializable(Vis::new).build());
}
