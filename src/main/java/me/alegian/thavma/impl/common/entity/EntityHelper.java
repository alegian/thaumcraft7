package me.alegian.thavma.impl.common.entity;

import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;

public class EntityHelper {
  public static boolean isEntityWearingBoots(LivingEntity entity) {
    var boots = entity.getItemBySlot(EquipmentSlot.FEET);
    return boots.is(ItemTags.FOOT_ARMOR);
  }

  public static void invertSwingingArm(LivingEntity pLivingEntity) {
    if (pLivingEntity.swingingArm == InteractionHand.MAIN_HAND) pLivingEntity.swingingArm = InteractionHand.OFF_HAND;
    else pLivingEntity.swingingArm = InteractionHand.MAIN_HAND;
  }

  public static boolean isHandKatana(InteractionHand hand) {
    return Minecraft.getInstance().player.getItemInHand(hand).getItem().equals(T7Items.ARCANUM_KATANA.get());
  }

  public static BlockHitResult getServerHitResult(ServerPlayer player) {
    return player.level().clip(
        new ClipContext(
            player.getEyePosition(1),
            player.getEyePosition(1).add(player.getViewVector(1).scale(6)
            ),
            ClipContext.Block.COLLIDER,
            ClipContext.Fluid.NONE,
            player
        )
    );
  }
}
