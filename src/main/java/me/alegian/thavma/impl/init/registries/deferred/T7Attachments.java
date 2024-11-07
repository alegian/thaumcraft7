package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class T7Attachments {
  public static final DeferredRegister<AttachmentType<?>> REGISTRAR = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Thavma.MODID);
//  private static final Codec<Map<Research, Boolean>> CODEC = Codec.
//
//  private static final Supplier<AttachmentType<Map<Research, Boolean>>> RESEARCH = REGISTRAR.register(
//      "research", () -> AttachmentType.builder(Map::of).serialize(CODEC).build()
//  );
}
