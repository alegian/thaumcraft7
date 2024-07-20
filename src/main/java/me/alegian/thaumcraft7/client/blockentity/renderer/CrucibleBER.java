package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CrucibleBER implements BlockEntityRenderer<CrucibleBE> {
    @Override
    public void render(CrucibleBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }

    private void renderTopFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, float percent) {
        float width = 12 / 16f;
        float height = 16 / 16f;

        float minU = sprite.getU(0);
        float maxU = sprite.getU(16);
        float minV = sprite.getV(0);
        float maxV = sprite.getV(16);

        builder.addVertex(matrix4f, -width / 2, -height / 2 + percent * height, -width / 2).setColor(1,1,1,0)
                .setUv(minU, minV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.addVertex(matrix4f, -width / 2, -height / 2 + percent * height, width / 2).setColor(1,1,1,0)
                .setUv(minU, maxV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.addVertex(matrix4f, width / 2, -height / 2 + percent * height, width / 2).setColor(1,1,1,0)
                .setUv(maxU, maxV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();

        builder.addVertex(matrix4f, width / 2, -height / 2 + percent * height, -width / 2).setColor(1,1,1,0)
                .setUv(maxU, minV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setUv2(15728880)
                .normal(normalMatrix, 0, 1, 0)
                .endVertex();
    }
}
