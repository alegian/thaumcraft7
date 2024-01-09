package me.alegian.thaumcraft7.api.aspects;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

public class Aspect {
    public static LinkedHashMap<String,Aspect> aspects = new LinkedHashMap<>();

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

    public Aspect(String id, int color, Aspect[] components){
       this(id, color, components, new ResourceLocation("thaumcraft7", "aspects/"+id));
    }

    public boolean isPrimal(){
        return getComponents() == null;
    }

    public Aspect(String id, int color){
        this(id, color, null);
    }

    public String getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public Aspect[] getComponents() {
        return components;
    }

    public ResourceLocation getImage() {
        return image;
    }

    public static final Aspect IGNIS = new Aspect("ignis", 0xff5a01, null);
    public static final Aspect AER = new Aspect("aer", 0xffff7e, null);
    public static final Aspect TERRA = new Aspect("terra", 0x56c000, null);
    public static final Aspect AQUA = new Aspect("aqua", 0x3cd4fc, null);
    public static final Aspect ORDO = new Aspect("ordo", 0xd5d4ec, null);
    public static final Aspect PERDITIO = new Aspect("perditio", 0x404040, null);

    public static final Aspect LUX = new Aspect("lux", 0xffffc0, new Aspect[]{IGNIS, AER});
    public static final Aspect POTENTIA = new Aspect("potentia", 0xc0ffff, new Aspect[]{IGNIS, ORDO});
}
