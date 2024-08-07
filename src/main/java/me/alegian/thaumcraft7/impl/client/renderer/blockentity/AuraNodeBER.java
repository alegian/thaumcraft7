package me.alegian.thaumcraft7.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.impl.common.block.entity.AuraNodeBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class AuraNodeBER implements BlockEntityRenderer<AuraNodeBE> {

  @Override
  public void render(@NotNull AuraNodeBE be, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
    poseStack.pushPose();

    // at the center of the block
    poseStack.translate(0.5d, 0.5d, 0.5d);

    // follows the camera like a particle
    Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
    poseStack.mulPose(rotation);

    BERHelper.renderAuraNodeLayer(poseStack, bufferSource, 0.45f, 16, 0, 0, 1, 0.2f);
    BERHelper.renderAuraNodeLayer(poseStack, bufferSource, 0.2f, 16, 0, 1, 1, 0.2f);

    poseStack.popPose();
  }
}
