package me.alegian.thavma.impl.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class WandItemExtensions implements IClientItemExtensions {
  @Override
  public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
    int i = arm == HumanoidArm.RIGHT ? 1 : -1;
    Matrix4f transformMatrix = new Matrix4f();
    boolean using = false;

    if (player.getUseItem() == itemInHand && player.isUsingItem()) {
      using = true;

      transformMatrix = transformMatrix
          .translate(i * 0.56F, -0.52F, -0.72F)
          .rotateX((float) (-1 * Math.PI / 4))
          .rotateZ((float) (i * Math.PI / 16));
    }

    poseStack.mulPose(transformMatrix);
    return using;
  }
}
