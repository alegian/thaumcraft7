package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class EntityHelper {
  public static boolean isEntityWearingBoots(LivingEntity entity) {
    var boots = entity.getItemBySlot(EquipmentSlot.FEET);
    return boots.is(ItemTags.FOOT_ARMOR);
  }

  public static void invertSwingingArm(LivingEntity pLivingEntity) {
    if(pLivingEntity.swingingArm == InteractionHand.MAIN_HAND) pLivingEntity.swingingArm = InteractionHand.OFF_HAND;
    else pLivingEntity.swingingArm = InteractionHand.MAIN_HAND;
  }

  public static boolean isHandKatana(InteractionHand hand) {
    return Minecraft.getInstance().player.getItemInHand(hand).getItem().equals(T7Items.ARCANUM_KATANA.get());
  }
}
