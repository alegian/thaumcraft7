package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import me.alegian.thaumcraft7.data.capability.CrucibleFluidHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

@OnlyIn(Dist.CLIENT)
public class CrucibleBER implements BlockEntityRenderer<CrucibleBE> {
  @Override
  public void render(CrucibleBE pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
    CrucibleFluidHandler tank = pBlockEntity.getFluidHandler();

    if (tank.isEmpty()) return;

    int amount = tank.getFluidAmount();
    int total = tank.getCapacity();

    pPoseStack.pushPose();

    pPoseStack.translate(0.5d, 0, 0.5d);

    renderWaterQuad(pPoseStack.last(), pBufferSource.getBuffer(RenderType.translucent()), (amount / (float) total), pPackedLight);

    pPoseStack.popPose();
  }

  private static void renderWaterQuad(PoseStack.Pose pose, VertexConsumer buffer, float percent, int packedLight) {
    IClientFluidTypeExtensions waterClientExtensions = IClientFluidTypeExtensions.of(Fluids.WATER);

    var sprite = getFluidSprite(waterClientExtensions);
    int color = waterClientExtensions.getTintColor();

    float width = 12 / 16f;
    float height = 3 / 16f + 12 / 16f * percent;

    float minU = sprite.getU0();
    float maxU = sprite.getU1();
    float minV = sprite.getV0();
    float maxV = sprite.getV1();

    buffer.addVertex(pose, -width / 2, height, -width / 2)
        .setColor(color)
        .setUv(minU, minV)
        .setLight(packedLight)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, -width / 2, height, width / 2)
        .setColor(color)
        .setUv(minU, maxV)
        .setLight(packedLight)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, width / 2, height, width / 2)
        .setColor(color)
        .setUv(maxU, maxV)
        .setLight(packedLight)
        .setNormal(pose, 0, 1, 0);

    buffer.addVertex(pose, width / 2, height, -width / 2)
        .setColor(color)
        .setUv(maxU, minV)
        .setLight(packedLight)
        .setNormal(pose, 0, 1, 0);
  }

  private static TextureAtlasSprite getFluidSprite(IClientFluidTypeExtensions fluidTypeExtensions) {
    return Minecraft.getInstance()
        .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
        .apply(fluidTypeExtensions.getStillTexture());
  }
}
