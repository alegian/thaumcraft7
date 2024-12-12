package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.aspect.Aspect;
import me.alegian.thavma.impl.init.registries.deferred.util.DeferredAspect;
import me.alegian.thavma.impl.init.registries.deferred.util.T7DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class Aspects {
  public static final T7DeferredRegister.Aspects REGISTRAR = T7DeferredRegister.createAspects(Thavma.MODID);

  // PRIMAL
  public static final DeferredAspect<Aspect> IGNIS = Aspects.register("ignis", 0xffff5a01, null);
  public static final DeferredAspect<Aspect> AER = Aspects.register("aer", 0xffffff7e, null);
  public static final DeferredAspect<Aspect> LUX = Aspects.register("lux", 0xffffffc0, List.of(Aspects.AER, Aspects.IGNIS));
  public static final DeferredAspect<Aspect> TERRA = Aspects.register("terra", 0xff56c000, null);
  public static final DeferredAspect<Aspect> VITREUS = Aspects.register("vitreus", 0xff80ffff, List.of(Aspects.TERRA, Aspects.AER));
  public static final DeferredAspect<Aspect> AQUA = Aspects.register("aqua", 0xff3cd4fc, null);
  public static final DeferredAspect<Aspect> VICTUS = Aspects.register("victus", 0xffde0005, List.of(Aspects.TERRA, Aspects.AQUA));
  public static final DeferredAspect<Aspect> HERBA = Aspects.register("herba", 0xff01ac00, List.of(Aspects.VICTUS, Aspects.TERRA));
  public static final DeferredAspect<Aspect> ORDO = Aspects.register("ordo", 0xffd5d4ec, null);
  public static final DeferredAspect<Aspect> MOTUS = Aspects.register("motus", 0xffcdccf4, List.of(Aspects.AER, Aspects.ORDO));
  public static final DeferredAspect<Aspect> VOLATUS = Aspects.register("volatus", 0xffe7e7d7, List.of(Aspects.AER, Aspects.MOTUS));
  public static final DeferredAspect<Aspect> BESTIA = Aspects.register("bestia", 0xff9f6409, List.of(Aspects.MOTUS, Aspects.VICTUS));
  public static final DeferredAspect<Aspect> METALLUM = Aspects.register("metallum", 0xffb5b5cd, List.of(Aspects.TERRA, Aspects.ORDO));
  public static final DeferredAspect<Aspect> POTENTIA = Aspects.register("potentia", 0xffc0ffff, List.of(Aspects.ORDO, Aspects.IGNIS));
  // TERTIARY
  public static final DeferredAspect<Aspect> PRAECANTATIO = Aspects.register("praecantatio", 0xffcf00ff, List.of(Aspects.POTENTIA, Aspects.AER));
  public static final DeferredAspect<Aspect> AURAM = Aspects.register("auram", 0xffffc0ff, List.of(Aspects.PRAECANTATIO, Aspects.AER));
  public static final DeferredAspect<Aspect> ALKIMIA = Aspects.register("alkimia", 0xff23ac9d, List.of(Aspects.PRAECANTATIO, Aspects.AQUA));
  public static final DeferredAspect<Aspect> INSTRUMENTUM = Aspects.register("instrumentum", 0xff4040ee, List.of(Aspects.METALLUM, Aspects.POTENTIA));
  public static final DeferredAspect<Aspect> MACHINA = Aspects.register("machina", 0xff8080a0, List.of(Aspects.MOTUS, Aspects.INSTRUMENTUM));
  public static final DeferredAspect<Aspect> PERDITIO = Aspects.register("perditio", 0xff404040, null);
  // DO NOT USE THIS TO CHECK IF AN ASPECT IS PRIMAL, this array is used to auto-generate ores and other content. Instead, use Aspect::isPrimal
  public static final List<DeferredAspect<Aspect>> PRIMAL_ASPECTS = List.of(Aspects.IGNIS, Aspects.AER, Aspects.TERRA, Aspects.AQUA, Aspects.ORDO, Aspects.PERDITIO);
  // SECONDARY (PRIMAL + PRIMAL)
  public static final DeferredAspect<Aspect> VACUOS = Aspects.register("vacuos", 0xff888888, List.of(Aspects.AER, Aspects.PERDITIO));
  public static final DeferredAspect<Aspect> TENEBRAE = Aspects.register("tenebrae", 0xff222222, List.of(Aspects.VACUOS, Aspects.LUX));
  public static final DeferredAspect<Aspect> ALIENIS = Aspects.register("alienis", 0xff805080, List.of(Aspects.VACUOS, Aspects.TENEBRAE));
  public static final DeferredAspect<Aspect> GELUM = Aspects.register("gelum", 0xffe1ffff, List.of(Aspects.IGNIS, Aspects.PERDITIO));
  public static final DeferredAspect<Aspect> MORTUUS = Aspects.register("mortuus", 0xff6a0005, List.of(Aspects.AQUA, Aspects.PERDITIO));
  public static final DeferredAspect<Aspect> SPIRITUS = Aspects.register("spiritus", 0xffebebfb, List.of(Aspects.VICTUS, Aspects.MORTUUS));
  public static final DeferredAspect<Aspect> COGNITIO = Aspects.register("cognitio", 0xfff9967f, List.of(Aspects.IGNIS, Aspects.SPIRITUS));
  public static final DeferredAspect<Aspect> SENSUS = Aspects.register("sensus", 0xffc0ffc0, List.of(Aspects.AER, Aspects.SPIRITUS));
  public static final DeferredAspect<Aspect> AVERSIO = Aspects.register("aversio", 0xffc05050, List.of(Aspects.SPIRITUS, Aspects.PERDITIO));
  public static final DeferredAspect<Aspect> PRAEMUNIO = Aspects.register("praemunio", 0xff00c0c0, List.of(Aspects.SPIRITUS, Aspects.TERRA));
  public static final DeferredAspect<Aspect> DESIDERIUM = Aspects.register("desiderium", 0xffe6be44, List.of(Aspects.SPIRITUS, Aspects.VACUOS));
  public static final DeferredAspect<Aspect> HUMANUS = Aspects.register("humanus", 0xffffd7c0, List.of(Aspects.SPIRITUS, Aspects.VICTUS));
  public static final DeferredAspect<Aspect> EXANIMIS = Aspects.register("exanimis", 0xff3a4000, List.of(Aspects.MOTUS, Aspects.MORTUUS));
  public static final DeferredAspect<Aspect> PERMUTATIO = Aspects.register("permutatio", 0xff578357, List.of(Aspects.PERDITIO, Aspects.ORDO));
  public static final DeferredAspect<Aspect> FABRICO = Aspects.register("fabrico", 0xff809d80, List.of(Aspects.PERMUTATIO, Aspects.INSTRUMENTUM));
  public static final DeferredAspect<Aspect> VITIUM = Aspects.register("vitium", 0xff800080, List.of(Aspects.PERDITIO, Aspects.PRAECANTATIO));
  public static final DeferredAspect<Aspect> VINCULUM = Aspects.register("vinculum", 0xff9a8080, List.of(Aspects.MOTUS, Aspects.PERDITIO));

  private static DeferredAspect<Aspect> register(String id, int color, List<Supplier<Aspect>> components) {
    return Aspects.REGISTRAR.registerAspect(id, () -> new Aspect(id, color, components));
  }
}
