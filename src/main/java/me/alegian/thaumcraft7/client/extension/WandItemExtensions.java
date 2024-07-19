package me.alegian.thaumcraft7.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.enumextension.WandArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;

public class WandItemExtensions implements IClientItemExtensions {
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return WandArmPose.value();
            }
        }
        return HumanoidModel.ArmPose.EMPTY;
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        int i = arm == HumanoidArm.RIGHT ? 1 : -1;
        Matrix4f transformMatrix = new Matrix4f();
        boolean using = false;

        if (player.getUseItem() == itemInHand && player.isUsingItem()) {
            using = true;
            float secondsUsing = (float) player.getTicksUsingItem() /20;

            transformMatrix = transformMatrix
                    .translate(i * 0.56F, -0.52F, -0.72F)
                    .rotateX((float) (-1*Math.PI/4))
                    .rotateY((float) (secondsUsing*Math.PI))
                    .translate(0, -0.5F, 0)
                    .rotateX((float) (-1*Math.PI/16))
                    .translate(0, 0.5F, 0);
        }

        poseStack.mulPose(transformMatrix);
        return using;
    }
}
