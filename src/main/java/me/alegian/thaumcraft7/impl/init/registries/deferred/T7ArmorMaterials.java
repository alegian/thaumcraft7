package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class T7ArmorMaterials {
  public static final DeferredRegister<ArmorMaterial> REGISTRAR = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Thaumcraft.MODID);

  public static final DeferredHolder<ArmorMaterial, ArmorMaterial> GOGGLES = REGISTRAR.register("goggles", () -> new ArmorMaterial(
      Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 1);
      }),
      25,
      SoundEvents.ARMOR_EQUIP_CHAIN,
      Ingredient::of,
      List.of(),
      0,
      0
  ));

  public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RESEARCHER = REGISTRAR.register("researcher", () -> new ArmorMaterial(
      Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.CHESTPLATE, 1);
        map.put(ArmorItem.Type.LEGGINGS, 1);
        map.put(ArmorItem.Type.BOOTS, 1);
      }),
      25,
      SoundEvents.ARMOR_EQUIP_LEATHER,
      Ingredient::of,
      List.of(),
      0,
      0
  ));

  public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ARCANUM = REGISTRAR.register("arcanum", () -> new ArmorMaterial(
      Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 3);
      }),
      25,
      SoundEvents.ARMOR_EQUIP_IRON,
      () -> Ingredient.of(T7Items.ARCANUM_INGOT),
      List.of(
          new ArmorMaterial.Layer(Thaumcraft.id("arcanum"))
      ),
      1.0F,
      0
  ));

  public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CUSTOS_ARCANUM = REGISTRAR.register("custos_arcanum", () -> new ArmorMaterial(
      Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
      }),
      25,
      SoundEvents.ARMOR_EQUIP_NETHERITE,
      () -> Ingredient.of(T7Items.ARCANUM_INGOT),
      List.of(
          new ArmorMaterial.Layer(Thaumcraft.id("arcanum"))
      ),
      3.0F,
      0.1F
  ));
}
