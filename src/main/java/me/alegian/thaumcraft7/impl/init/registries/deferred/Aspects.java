package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredAspect;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.T7DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class Aspects {
  public static final T7DeferredRegister.Aspects REGISTRAR = T7DeferredRegister.createAspects(Thaumcraft.MODID);

  // PRIMAL
  public static final DeferredAspect<Aspect> IGNIS = register("ignis", 0xffff5a01, null);
  public static final DeferredAspect<Aspect> AER = register("aer", 0xffffff7e, null);
  public static final DeferredAspect<Aspect> TERRA = register("terra", 0xff56c000, null);
  public static final DeferredAspect<Aspect> AQUA = register("aqua", 0xff3cd4fc, null);
  public static final DeferredAspect<Aspect> ORDO = register("ordo", 0xffd5d4ec, null);
  public static final DeferredAspect<Aspect> PERDITIO = register("perditio", 0xff404040, null);
  public static final List<DeferredAspect<Aspect>> PRIMAL_ASPECTS = List.of(IGNIS, AER, TERRA, AQUA, ORDO, PERDITIO);

  // SECONDARY (PRIMAL + PRIMAL)
  public static final DeferredAspect<Aspect> VACUOS = register("vacuos", 0xff888888, List.of(AER, PERDITIO));
  public static final DeferredAspect<Aspect> LUX = register("lux", 0xffffffc0, List.of(AER, IGNIS));
  public static final DeferredAspect<Aspect> MOTUS = register("motus", 0xffcdccf4, List.of(AER, ORDO));
  public static final DeferredAspect<Aspect> GELUM = register("gelum", 0xffe1ffff, List.of(IGNIS, PERDITIO));
  public static final DeferredAspect<Aspect> VITREUS = register("vitreus", 0xff80ffff, List.of(TERRA, AER));
  public static final DeferredAspect<Aspect> METALLUM = register("metallum", 0xffb5b5cd, List.of(TERRA, ORDO));
  public static final DeferredAspect<Aspect> VICTUS = register("victus", 0xffde0005, List.of(TERRA, AQUA));
  public static final DeferredAspect<Aspect> MORTUUS = register("mortuus", 0xff6a0005, List.of(AQUA, PERDITIO));
  public static final DeferredAspect<Aspect> POTENTIA = register("potentia", 0xffc0ffff, List.of(ORDO, IGNIS));
  public static final DeferredAspect<Aspect> PERMUTATIO = register("permutatio", 0xff578357, List.of(PERDITIO, ORDO));

  // TERTIARY
  public static final DeferredAspect<Aspect> PRAECANTATIO = register("praecantatio", 0xffcf00ff, List.of(POTENTIA, AER));
  public static final DeferredAspect<Aspect> AURAM = register("auram", 0xffffc0ff, List.of(PRAECANTATIO, AER));
  public static final DeferredAspect<Aspect> ALKIMIA = register("alkimia", 0xff23ac9d, List.of(PRAECANTATIO, AQUA));
  public static final DeferredAspect<Aspect> VITIUM = register("vitium", 0xff800080, List.of(PERDITIO, PRAECANTATIO));
  public static final DeferredAspect<Aspect> TENEBRAE = register("tenebrae", 0xff222222, List.of(VACUOS, LUX));
  public static final DeferredAspect<Aspect> ALIENIS = register("alienis", 0xff805080, List.of(VACUOS, TENEBRAE));
  public static final DeferredAspect<Aspect> VOLATUS = register("volatus", 0xffe7e7d7, List.of(AER, MOTUS));
  public static final DeferredAspect<Aspect> HERBA = register("herba", 0xff01ac00, List.of(VICTUS, TERRA));
  public static final DeferredAspect<Aspect> INSTRUMENTUM = register("instrumentum", 0xff4040ee, List.of(METALLUM, POTENTIA));
  public static final DeferredAspect<Aspect> FABRICO = register("fabrico", 0xff809d80, List.of(PERMUTATIO, INSTRUMENTUM));
  public static final DeferredAspect<Aspect> MACHINA = register("machina", 0xff8080a0, List.of(MOTUS, INSTRUMENTUM));
  public static final DeferredAspect<Aspect> VINCULUM = register("vinculum", 0xff9a8080, List.of(MOTUS, PERDITIO));
  public static final DeferredAspect<Aspect> SPIRITUS = register("spiritus", 0xffebebfb, List.of(VICTUS, MORTUUS));
  public static final DeferredAspect<Aspect> COGNITIO = register("cognitio", 0xfff9967f, List.of(IGNIS, SPIRITUS));
  public static final DeferredAspect<Aspect> SENSUS = register("sensus", 0xffc0ffc0, List.of(AER, SPIRITUS));
  public static final DeferredAspect<Aspect> AVERSIO = register("aversio", 0xffc05050, List.of(SPIRITUS, PERDITIO));
  public static final DeferredAspect<Aspect> PRAEMUNIO = register("praemunio", 0xff00c0c0, List.of(SPIRITUS, TERRA));
  public static final DeferredAspect<Aspect> DESIDERIUM = register("desiderium", 0xffe6be44, List.of(SPIRITUS, VACUOS));
  public static final DeferredAspect<Aspect> EXANIMIS = register("exanimis", 0xff3a4000, List.of(MOTUS, MORTUUS));
  public static final DeferredAspect<Aspect> BESTIA = register("bestia", 0xff9f6409, List.of(MOTUS, VICTUS));
  public static final DeferredAspect<Aspect> HUMANUS = register("humanus", 0xffffd7c0, List.of(SPIRITUS, VICTUS));


  private static DeferredAspect<Aspect> register(String id, int color, List<Supplier<Aspect>> components) {
    return REGISTRAR.registerAspect(id, () -> new Aspect(id, color, components));
  }
}
