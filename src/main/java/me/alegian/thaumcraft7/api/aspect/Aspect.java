package me.alegian.thaumcraft7.api.aspect;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class Aspect {
    public static Map<String,Aspect> aspects = new LinkedHashMap<>();

    String id;
    int color;
    Aspect[] components;
    ResourceLocation image;

    public Aspect(String id, int color, Aspect[] components, ResourceLocation image){
        this.id = id;
        this.color = color;
        this.components = components;
        this.image = image;

        var existing = aspects.putIfAbsent(id, this);
        if(existing != null) throw new IllegalArgumentException("Thaumcraft Aspect Exception: Duplicate ID: "+id);
    }

    /*
     * DON'T use this constructor, as it hardcodes asset locations to the thaumcraft folder.
     * Shortcut used for the default Thaumcraft Aspects.
     */
    public Aspect(String id, int color, Aspect[] components){
       this(id, color, components, new ResourceLocation("thaumcraft7", "aspects/"+id));
    }

    public boolean isPrimal(){
        return getComponents() == null;
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

        return new int[]{r,g,b};
    }

    public Aspect[] getComponents() {
        return components;
    }

    public ResourceLocation getImage() {
        return image;
    }

    // PRIMAL
    public static final Aspect IGNIS = new Aspect("ignis", 0xff5a01, null);
    public static final Aspect AER = new Aspect("aer", 0xffff7e, null);
    public static final Aspect TERRA = new Aspect("terra", 0x56c000, null);
    public static final Aspect AQUA = new Aspect("aqua", 0x3cd4fc, null);
    public static final Aspect ORDO = new Aspect("ordo", 0xd5d4ec, null);
    public static final Aspect PERDITIO = new Aspect("perditio", 0x404040, null);
    public static final Aspect[] PRIMAL_ASPECTS = {IGNIS, AER, TERRA, AQUA, ORDO, PERDITIO};

    // SECONDARY (PRIMAL + PRIMAL)
    public static final Aspect VACUOS = new Aspect("vacuos",0x888888, new Aspect[] {AER, PERDITIO});
    public static final Aspect LUX = new Aspect("lux",0xffffc0, new Aspect[] {AER, IGNIS});
    public static final Aspect MOTUS = new Aspect("motus",0xcdccf4, new Aspect[] {AER, ORDO});
    public static final Aspect GELUM = new Aspect("gelum",0xe1ffff, new Aspect[] {IGNIS, PERDITIO});
    public static final Aspect VITREUS = new Aspect("vitreus",0x80ffff, new Aspect[] {TERRA, AER});
    public static final Aspect METALLUM = new Aspect("metallum",0xb5b5cd, new Aspect[] {TERRA, ORDO});
    public static final Aspect VICTUS = new Aspect("victus",0xde0005, new Aspect[] {TERRA, AQUA});
    public static final Aspect MORTUUS = new Aspect("mortuus",0x6a0005, new Aspect[] {AQUA, PERDITIO});
    public static final Aspect POTENTIA = new Aspect("potentia",0xc0ffff, new Aspect[] {ORDO, IGNIS});
    public static final Aspect PERMUTATIO = new Aspect("permutatio",0x578357, new Aspect[] {PERDITIO, ORDO});

    // TERTIARY
    public static final Aspect PRAECANTATIO = new Aspect("praecantatio",0xcf00ff, new Aspect[] {POTENTIA, AER});
    public static final Aspect AURAM = new Aspect("auram",0xffc0ff, new Aspect[] {PRAECANTATIO, AER});
    public static final Aspect ALKIMIA = new Aspect("alkimia",0x23ac9d, new Aspect[] {PRAECANTATIO, AQUA});
    public static final Aspect VITIUM = new Aspect("vitium",0x800080, new Aspect[] {PERDITIO, PRAECANTATIO});
    public static final Aspect TENEBRAE = new Aspect("tenebrae",0x222222, new Aspect[] {VACUOS, LUX});
    public static final Aspect ALIENIS = new Aspect("alienis",0x805080, new Aspect[] {VACUOS, TENEBRAE});
    public static final Aspect VOLATUS = new Aspect("volatus",0xe7e7d7, new Aspect[] {AER, MOTUS});
    public static final Aspect HERBA = new Aspect("herba",0x01ac00, new Aspect[] {VICTUS, TERRA});
    public static final Aspect INSTRUMENTUM = new Aspect("instrumentum",0x4040ee, new Aspect[] {METALLUM, POTENTIA});
    public static final Aspect FABRICO = new Aspect("fabrico",0x809d80, new Aspect[] {PERMUTATIO, INSTRUMENTUM});
    public static final Aspect MACHINA = new Aspect("machina",0x8080a0, new Aspect[] {MOTUS, INSTRUMENTUM});
    public static final Aspect VINCULUM = new Aspect("vinculum",0x9a8080, new Aspect[] {MOTUS, PERDITIO});
    public static final Aspect SPIRITUS = new Aspect("spiritus",0xebebfb, new Aspect[] {VICTUS, MORTUUS});
    public static final Aspect COGNITIO = new Aspect("cognitio",0xf9967f, new Aspect[] {IGNIS, SPIRITUS});
    public static final Aspect SENSUS = new Aspect("sensus",0xc0ffc0, new Aspect[] {AER, SPIRITUS});
    public static final Aspect AVERSIO = new Aspect("aversio",0xc05050, new Aspect[] {SPIRITUS, PERDITIO});
    public static final Aspect PRAEMUNIO = new Aspect("praemunio",0x00c0c0, new Aspect[] {SPIRITUS, TERRA});
    public static final Aspect DESIDERIUM = new Aspect("desiderium",0xe6be44, new Aspect[] {SPIRITUS, VACUOS});
    public static final Aspect EXANIMIS = new Aspect("exanimis",0x3a4000, new Aspect[] {MOTUS, MORTUUS});
    public static final Aspect BESTIA = new Aspect("bestia",0x9f6409, new Aspect[] {MOTUS, VICTUS});
    public static final Aspect HUMANUS = new Aspect("humanus",0xffd7c0, new Aspect[] {SPIRITUS, VICTUS});
}
