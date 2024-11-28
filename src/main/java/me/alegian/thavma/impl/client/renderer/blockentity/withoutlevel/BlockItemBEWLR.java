package me.alegian.thavma.impl.client.renderer.blockentity.withoutlevel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockItemBEWLR extends BlockEntityWithoutLevelRenderer {
  private final BlockEntity blockEntity;

  public BlockItemBEWLR(BlockEntity blockEntity) {
    super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    this.blockEntity = blockEntity;
  }

  @Override
  public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    var ber = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(this.blockEntity);
    if (ber == null) return;
    ber.render(this.blockEntity, 0, poseStack, buffer, packedLight, packedOverlay);
  }
}
