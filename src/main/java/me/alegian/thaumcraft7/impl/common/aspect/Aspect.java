package me.alegian.thaumcraft7.impl.common.aspect;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Aspect {
  public static final Map<String, Aspect> ASPECTS = new LinkedHashMap<>();

  String id;
  int color;
  Aspect[] components;

  public Aspect(String id, int color, Aspect[] components) {
    this.id = id;
    this.color = color;
    this.components = components;

    var existing = ASPECTS.putIfAbsent(id, this);
    if (existing != null) throw new IllegalArgumentException("Thaumcraft Aspect Exception: Duplicate ID: " + id);
  }

  public boolean isPrimal() {
    return PRIMAL_ASPECTS.contains(this);
  }

  public String getId() {
    return id;
  }

  public int getColor() {
    return color;
  }

  public int[] getColorRGB() {
    int r = (color & 0xFF0000) >> 16;
    int g = (color & 0xFF00) >> 8;
    int b = (color & 0xFF);

    return new int[]{r, g, b};
  }

  public Aspect[] getComponents() {
    return components;
  }

  public static Aspect getRandomAspect() {
    var aspectArray = Aspect.ASPECTS.values().toArray(new Aspect[]{});
    return aspectArray[(int) (Math.random() * aspectArray.length)];
  }

  public static Aspect valueOf(String s) {
    Aspect aspect = ASPECTS.get(s);
    if (aspect == null) throw new IllegalArgumentException("Thaumcraft Aspect Exception: Unknown Aspect: " + s);
    return aspect;
  }

  // PRIMAL
  public static final Aspect IGNIS = new Aspect("ignis", 0xffff5a01, null);
  public static final Aspect AER = new Aspect("aer", 0xffffff7e, null);
  public static final Aspect TERRA = new Aspect("terra", 0xff56c000, null);
  public static final Aspect AQUA = new Aspect("aqua", 0xff3cd4fc, null);
  public static final Aspect ORDO = new Aspect("ordo", 0xffd5d4ec, null);
  public static final Aspect PERDITIO = new Aspect("perditio", 0xff404040, null);
  public static final List<Aspect> PRIMAL_ASPECTS = List.of(IGNIS, AER, TERRA, AQUA, ORDO, PERDITIO);

  // SECONDARY (PRIMAL + PRIMAL)
  public static final Aspect VACUOS = new Aspect("vacuos", 0xff888888, new Aspect[]{AER, PERDITIO});
  public static final Aspect LUX = new Aspect("lux", 0xffffffc0, new Aspect[]{AER, IGNIS});
  public static final Aspect MOTUS = new Aspect("motus", 0xffcdccf4, new Aspect[]{AER, ORDO});
  public static final Aspect GELUM = new Aspect("gelum", 0xffe1ffff, new Aspect[]{IGNIS, PERDITIO});
  public static final Aspect VITREUS = new Aspect("vitreus", 0xff80ffff, new Aspect[]{TERRA, AER});
  public static final Aspect METALLUM = new Aspect("metallum", 0xffb5b5cd, new Aspect[]{TERRA, ORDO});
  public static final Aspect VICTUS = new Aspect("victus", 0xffde0005, new Aspect[]{TERRA, AQUA});
  public static final Aspect MORTUUS = new Aspect("mortuus", 0xff6a0005, new Aspect[]{AQUA, PERDITIO});
  public static final Aspect POTENTIA = new Aspect("potentia", 0xffc0ffff, new Aspect[]{ORDO, IGNIS});
  public static final Aspect PERMUTATIO = new Aspect("permutatio", 0xff578357, new Aspect[]{PERDITIO, ORDO});

  // TERTIARY
  public static final Aspect PRAECANTATIO = new Aspect("praecantatio", 0xffcf00ff, new Aspect[]{POTENTIA, AER});
  public static final Aspect AURAM = new Aspect("auram", 0xffffc0ff, new Aspect[]{PRAECANTATIO, AER});
  public static final Aspect ALKIMIA = new Aspect("alkimia", 0xff23ac9d, new Aspect[]{PRAECANTATIO, AQUA});
  public static final Aspect VITIUM = new Aspect("vitium", 0xff800080, new Aspect[]{PERDITIO, PRAECANTATIO});
  public static final Aspect TENEBRAE = new Aspect("tenebrae", 0xff222222, new Aspect[]{VACUOS, LUX});
  public static final Aspect ALIENIS = new Aspect("alienis", 0xff805080, new Aspect[]{VACUOS, TENEBRAE});
  public static final Aspect VOLATUS = new Aspect("volatus", 0xffe7e7d7, new Aspect[]{AER, MOTUS});
  public static final Aspect HERBA = new Aspect("herba", 0xff01ac00, new Aspect[]{VICTUS, TERRA});
  public static final Aspect INSTRUMENTUM = new Aspect("instrumentum", 0xff4040ee, new Aspect[]{METALLUM, POTENTIA});
  public static final Aspect FABRICO = new Aspect("fabrico", 0xff809d80, new Aspect[]{PERMUTATIO, INSTRUMENTUM});
  public static final Aspect MACHINA = new Aspect("machina", 0xff8080a0, new Aspect[]{MOTUS, INSTRUMENTUM});
  public static final Aspect VINCULUM = new Aspect("vinculum", 0xff9a8080, new Aspect[]{MOTUS, PERDITIO});
  public static final Aspect SPIRITUS = new Aspect("spiritus", 0xffebebfb, new Aspect[]{VICTUS, MORTUUS});
  public static final Aspect COGNITIO = new Aspect("cognitio", 0xfff9967f, new Aspect[]{IGNIS, SPIRITUS});
  public static final Aspect SENSUS = new Aspect("sensus", 0xffc0ffc0, new Aspect[]{AER, SPIRITUS});
  public static final Aspect AVERSIO = new Aspect("aversio", 0xffc05050, new Aspect[]{SPIRITUS, PERDITIO});
  public static final Aspect PRAEMUNIO = new Aspect("praemunio", 0xff00c0c0, new Aspect[]{SPIRITUS, TERRA});
  public static final Aspect DESIDERIUM = new Aspect("desiderium", 0xffe6be44, new Aspect[]{SPIRITUS, VACUOS});
  public static final Aspect EXANIMIS = new Aspect("exanimis", 0xff3a4000, new Aspect[]{MOTUS, MORTUUS});
  public static final Aspect BESTIA = new Aspect("bestia", 0xff9f6409, new Aspect[]{MOTUS, VICTUS});
  public static final Aspect HUMANUS = new Aspect("humanus", 0xffffd7c0, new Aspect[]{SPIRITUS, VICTUS});
}
