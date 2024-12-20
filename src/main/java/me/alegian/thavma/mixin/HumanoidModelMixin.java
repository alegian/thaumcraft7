package me.alegian.thavma.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.alegian.thavma.impl.common.entity.EntityHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = HumanoidModel.class, remap = false)
public abstract class HumanoidModelMixin {
  @WrapMethod(method = "setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V")
  private void thavma_setupAttackAnimationWrapper(LivingEntity pLivingEntity, float pAgeInTicks, Operation<Void> original) {
    if (!(pLivingEntity instanceof AbstractClientPlayer) || !EntityHelper.INSTANCE.isHandKatana(InteractionHand.MAIN_HAND) || !EntityHelper.INSTANCE.isHandKatana(InteractionHand.OFF_HAND)) {
      original.call(pLivingEntity, pAgeInTicks);
      return;
    }

    HumanoidArm mainArm = this.getAttackArm(pLivingEntity);
    HumanoidArm offArm = mainArm.getOpposite();
    // store the original poses
    PartPose originalMainPose = this.getArm(mainArm).storePose();
    PartPose originalOffPose = this.getArm(offArm).storePose();

    // simulate main hand animation & save result
    original.call(pLivingEntity, pAgeInTicks);
    PartPose correctMainPose = this.getArm(mainArm).storePose();

    // reset poses
    this.getArm(mainArm).loadPose(originalMainPose);
    this.getArm(offArm).loadPose(originalOffPose);

    // actually run offhand animation
    EntityHelper.INSTANCE.invertSwingingArm(pLivingEntity);
    original.call(pLivingEntity, pAgeInTicks);

    // load the simulated main hand pose
    this.getArm(mainArm).loadPose(correctMainPose);

    // restore original swinging arm
    EntityHelper.INSTANCE.invertSwingingArm(pLivingEntity);
  }

  @Shadow
  protected abstract HumanoidArm getAttackArm(LivingEntity pEntity);

  @Shadow
  protected abstract ModelPart getArm(HumanoidArm pSide);
}
