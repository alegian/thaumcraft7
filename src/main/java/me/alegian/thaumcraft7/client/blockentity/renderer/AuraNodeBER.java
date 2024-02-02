package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.alegian.thaumcraft7.blockentity.AuraNodeBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class AuraNodeBER implements BlockEntityRenderer<AuraNodeBE> {

    @Override
    public void render(AuraNodeBE be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();

        // at the center of the block
        poseStack.translate(0.5f, 0.5f, 0.5f);

        // follows the camera like a particle
        Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        poseStack.mulPose(rotation);

        BERHelper.renderAuraNodeLayer(poseStack, 0.45f, 16, 0, 0, 1);
        BERHelper.renderAuraNodeLayer(poseStack, 0.2f, 16, 0, 1, 1);

        poseStack.popPose();
    }
}
