package me.alegian.thaumcraft7.impl.common.entity;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EntityHelper {
  public static boolean isEntityWearingBoots(LivingEntity entity) {
    var boots = entity.getItemBySlot(EquipmentSlot.FEET);
    return boots.is(ItemTags.FOOT_ARMOR);
  }
}
