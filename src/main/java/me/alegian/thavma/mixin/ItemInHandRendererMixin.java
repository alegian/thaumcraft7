package me.alegian.thavma.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thavma.impl.common.entity.EntityHelper;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemInHandRenderer.class, remap = false)
public abstract class ItemInHandRendererMixin {
  @WrapMethod(method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
  private void thavma_renderArmWithItemWrapper(
      AbstractClientPlayer player,
      float pPartialTicks,
      float pPitch,
      InteractionHand pHand,
      float pSwingProgress,
      ItemStack pStack,
      float pEquippedProgress,
      PoseStack pPoseStack,
      MultiBufferSource pBuffer,
      int pCombinedLight,
      Operation<Void> original
  ) {
    if (EntityHelper.INSTANCE.isHandKatana(InteractionHand.MAIN_HAND) && EntityHelper.INSTANCE.isHandKatana(InteractionHand.OFF_HAND))
      pSwingProgress = player.getAttackAnim(pPartialTicks);
    original.call(player, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
  }
}
