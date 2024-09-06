package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.data.worldgen.tree.trunk.GreatwoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7TrunkPlacerTypes {
  public static final DeferredRegister<TrunkPlacerType<?>> REGISTRAR = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Thaumcraft.MODID);

  public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GreatwoodTrunkPlacer>> GREATWOOD_TRUNK_PLACER_TYPE = REGISTRAR.register("greatwood", () -> new TrunkPlacerType<>(GreatwoodTrunkPlacer.CODEC));

}
