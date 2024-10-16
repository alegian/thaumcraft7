package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.research.Research;
import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Researches {
  public static final DeferredRegister<Research> REGISTRAR = DeferredRegister.create(T7Registries.RESEARCH, Thaumcraft.MODID);

  public static final Supplier<Research> TRANSFUSION = REGISTRAR.register("transfusion", Research::new);
}
