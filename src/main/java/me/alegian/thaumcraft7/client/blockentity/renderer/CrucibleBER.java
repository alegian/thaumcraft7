package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

@OnlyIn(Dist.CLIENT)
public class CrucibleBER implements BlockEntityRenderer<CrucibleBE> {
    @Override
    public void render(CrucibleBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        FluidTank tank = pBlockEntity.getFluidTank();
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack.isEmpty()) return;

        int amount = fluidStack.getAmount();
        int total = tank.getCapacity();

        pPoseStack.pushPose();

        pPoseStack.translate(0.5d, 0, 0.5d);

        renderWaterQuad(pPoseStack.last(), pBufferSource.getBuffer(RenderType.translucent()), (amount / (float) total), pPackedLight);

        pPoseStack.popPose();
    }

    private static void renderWaterQuad(PoseStack.Pose pose, VertexConsumer buffer, float percent, int packedLight) {
        var sprite = getWaterSprite();

        float width = 16 / 16f;
        float height = percent * 10 / 16f;

        float minU = sprite.getU(0);
        float maxU = sprite.getU(16);
        float minV = sprite.getV(0);
        float maxV = sprite.getV(16);

        buffer.addVertex(pose, -width / 2, height, -width / 2).setColor(1,1,1,1)
                .setUv(minU, minV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 1, 0);

        buffer.addVertex(pose, -width / 2, height, width / 2).setColor(1,1,1,1)
                .setUv(minU, maxV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 1, 0);

        buffer.addVertex(pose, width / 2, height, width / 2).setColor(1,1,1,1)
                .setUv(maxU, maxV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 1, 0);

        buffer.addVertex(pose, width / 2, height, -width / 2).setColor(1,1,1,1)
                .setUv(maxU, minV)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 1, 0);
    }

    private static TextureAtlasSprite getWaterSprite() {
        IClientFluidTypeExtensions waterClientExtensions = IClientFluidTypeExtensions.of(Fluids.WATER);

        return Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(waterClientExtensions.getStillTexture());
    }
}
