package me.alegian.thaumcraft7.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.enumextension.ThaumometerArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;

public class ThaumometerItemExtensions implements IClientItemExtensions {
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return ThaumometerArmPose.value();
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        // pretty much nullifies the default right click bob
        if (player.getUseItem() == itemInHand && player.isUsingItem()) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            var transformMatrix = new Matrix4f().translate(i * 0.56F, -0.53F, -0.72F);
            poseStack.mulPose(transformMatrix);
            return true;
        }
        return false;
    }
}
