package me.alegian.thaumcraft7.mixin;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HumanoidModel.class, remap = false)
public class HumanoidModelMixin {
  @Inject(
      method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
      at = @At(value = "INVOKE",
          target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V"
      )
  )
  private void thaumcraft7_setupKatanaAttackAnimationOffhand(LivingEntity pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo info) {
    if (!(pLivingEntity instanceof AbstractClientPlayer)) return;
    if (pLivingEntity.swingingArm != InteractionHand.MAIN_HAND) return;
    if (!Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(T7Items.ARCANUM_KATANA.get()))
      return;

    pLivingEntity.swingingArm = InteractionHand.OFF_HAND;
    setupAttackAnimation(pLivingEntity, pAgeInTicks);
    pLivingEntity.swingingArm = InteractionHand.MAIN_HAND;
  }

  @Shadow
  private void setupAttackAnimation(LivingEntity pLivingEntity, float pAgeInTicks) {
  }
}
