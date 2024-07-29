package me.alegian.thaumcraft7.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;

public class FancyItemRenderer extends ItemEntityRenderer {
  public FancyItemRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(ItemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    EnderDragonRenderer.renderCrystalBeams((float) pEntity.getX(), (float) pEntity.getY(), (float) pEntity.getZ(), pPartialTicks, pEntity.tickCount, pPoseStack, pBuffer, pPackedLight);
  }
}
