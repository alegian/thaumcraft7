package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.research.Research;
import me.alegian.thavma.impl.init.registries.T7Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Researches {
  public static final DeferredRegister<Research> REGISTRAR = DeferredRegister.create(T7Registries.RESEARCH, Thavma.MODID);

  public static final Supplier<Research> TRANSFUSION = REGISTRAR.register("transfusion", Research::new);
}
