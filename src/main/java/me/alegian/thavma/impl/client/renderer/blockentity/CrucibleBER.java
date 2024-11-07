package me.alegian.thavma.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thavma.impl.common.block.entity.CrucibleBE;
import me.alegian.thavma.impl.common.data.capability.CrucibleFluidHandler;
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
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

@OnlyIn(Dist.CLIENT)
public class CrucibleBER implements BlockEntityRenderer<CrucibleBE> {
  @Override
  public void render(CrucibleBE crucibleBE, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
    CrucibleFluidHandler tank = crucibleBE.getFluidHandler();

    if (tank.isEmpty()) return;

    pPoseStack.pushPose();

    pPoseStack.translate(0.5d, crucibleBE.getWaterHeight(), 0.5d);

    var renderType = RenderTypeHelper.getEntityRenderType(RenderType.translucent(), false);
    renderWaterQuad(pPoseStack.last(), pBufferSource.getBuffer(renderType), pPackedLight);

    pPoseStack.popPose();
  }

  private static void renderWaterQuad(PoseStack.Pose pose, VertexConsumer buffer, int packedLight) {
    IClientFluidTypeExtensions waterClientExtensions = IClientFluidTypeExtensions.of(Fluids.WATER);

    var sprite = getFluidSprite(waterClientExtensions);
    int color = waterClientExtensions.getTintColor();

    float width = 12 / 16f;

    float minU = sprite.getU0();
    float maxU = sprite.getU1();
    float minV = sprite.getV0();
    float maxV = sprite.getV1();

    buffer.addVertex(pose, -width / 2, 0, -width / 2)
        .setColor(color)
        .setUv(minU, minV)
        .setLight(packedLight)
        .setOverlay(OverlayTexture.NO_OVERLAY)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, -width / 2, 0, width / 2)
        .setColor(color)
        .setUv(minU, maxV)
        .setLight(packedLight)
        .setOverlay(OverlayTexture.NO_OVERLAY)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, width / 2, 0, width / 2)
        .setColor(color)
        .setUv(maxU, maxV)
        .setLight(packedLight)
        .setOverlay(OverlayTexture.NO_OVERLAY)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, width / 2, 0, -width / 2)
        .setColor(color)
        .setUv(maxU, minV)
        .setLight(packedLight)
        .setOverlay(OverlayTexture.NO_OVERLAY)
        .setNormal(pose, 0, 1, 0);
  }

  private static TextureAtlasSprite getFluidSprite(IClientFluidTypeExtensions fluidTypeExtensions) {
    return Minecraft.getInstance()
        .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
        .apply(fluidTypeExtensions.getStillTexture());
  }
}
