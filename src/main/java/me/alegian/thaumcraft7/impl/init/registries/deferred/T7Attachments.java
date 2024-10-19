package me.alegian.thaumcraft7.impl.init.registries.deferred;

import com.mojang.serialization.Codec;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.research.Research;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.FloatTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Map;
import java.util.function.Supplier;

public class T7Attachments {
  public static final DeferredRegister<AttachmentType<?>> REGISTRAR = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Thaumcraft.MODID);

//  private static final Codec<Map<Research, Boolean>> CODEC = Codec.
//
//  private static final Supplier<AttachmentType<Map<Research, Boolean>>> RESEARCH = REGISTRAR.register(
//      "research", () -> AttachmentType.builder(Map::of).serialize(CODEC).build()
//  );
}
