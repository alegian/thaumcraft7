package me.alegian.thavma.impl.init.registries.deferred;

import com.mojang.serialization.MapCodec;
import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.loot.WardenLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class T7GlobalLootModifierSerializers {
  public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTRAR =
      DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Thavma.MODID);

  public static final Supplier<MapCodec<WardenLootModifier>> WARDEN = T7GlobalLootModifierSerializers.REGISTRAR.register("warden", () -> WardenLootModifier.CODEC);
}
