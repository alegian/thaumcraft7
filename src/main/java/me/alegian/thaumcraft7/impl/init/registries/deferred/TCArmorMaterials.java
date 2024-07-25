package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class TCArmorMaterials {
  public static final DeferredRegister<ArmorMaterial> REGISTRAR = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Thaumcraft.MODID);

  public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOGGLES = REGISTRAR.register("goggles", () -> new ArmorMaterial(
      Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 1);
      }),
      25,
      SoundEvents.ARMOR_EQUIP_GENERIC,
      Ingredient::of,
      List.of(
          new ArmorMaterial.Layer(
              ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "goggles")
          )
      ),
      0,
      0
  ));
}
