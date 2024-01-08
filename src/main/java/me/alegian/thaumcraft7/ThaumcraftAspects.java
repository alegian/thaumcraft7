package me.alegian.thaumcraft7;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ThaumcraftAspects {
    enum AspectTier {
        PRIMAL,ONE,TWO,THREE;
    }

    private static final HashMap<String, Aspect> registered = new HashMap<>();

    public static class Aspect{
        AspectTier tier;
        String id;
        Aspect component1;
        Aspect component2;

        public Aspect(AspectTier tier, String id, @Nullable Aspect component1, @Nullable Aspect component2){
            this.tier = tier;
            this.id = id;
            this.component1 = component1;
            this.component2 = component2;
            var existing = registered.putIfAbsent(id, this);
            if(existing != null) throw new IllegalArgumentException("Thaumcraft Aspect Exception: Duplicate ID");
        }

        final boolean isPrimal(){
            return tier == AspectTier.PRIMAL;
        }
    }

    public static Aspect getRegisteredAspect(String id){
        return registered.get(id);
    }

    public static class Contained{
        public Aspect type;
        public int amount;

        public Contained(Aspect type, int amount){
            this.type = type;
            this.amount = amount;
        }

        public Contained(String id, int amount){
            this.type = getRegisteredAspect(id);
            this.amount = amount;
        }

        @Override
        public String toString() {
            return type.id+amount;
        }
    }

    public static final Aspect IGNIS = new Aspect(AspectTier.PRIMAL, "ignis", null, null);
    public static final Aspect AER = new Aspect(AspectTier.PRIMAL, "aer", null, null);
    public static final Aspect TERRA = new Aspect(AspectTier.PRIMAL, "terra", null, null);
    public static final Aspect AQUA = new Aspect(AspectTier.PRIMAL, "aqua", null, null);
    public static final Aspect ORDO = new Aspect(AspectTier.PRIMAL, "ordo", null, null);
    public static final Aspect PERDITIO = new Aspect(AspectTier.PRIMAL, "perditio", null, null);

    public static final Aspect LUX = new Aspect(AspectTier.PRIMAL, "lux", IGNIS, AER);
    public static final Aspect POTENTIA = new Aspect(AspectTier.PRIMAL, "potentia", IGNIS, ORDO);
}
