package me.alegian.thavma.impl.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.common.block.entity.AuraNodeBE;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.data.capability.IAspectContainer;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class AuraNodeBER implements BlockEntityRenderer<AuraNodeBE> {
  public static final float MIN_SCALE = 1f / 3; // containment animation minimum allowed scale
  private float scale = 1f;

  @Override
  public void render(@NotNull AuraNodeBE be, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
    var containingCountdown = be.getContainingCountdown();

    poseStack.pushPose();
    this.setupPose(poseStack, containingCountdown, partialTick);

    AuraNodeBER.renderContainer(poseStack, bufferSource, combinedLight, combinedOverlay, containingCountdown);
    this.renderNode(be, poseStack, bufferSource);

    poseStack.popPose();
  }

  /**
   * Renders a 3x3x3 container jar around the node.
   */
  private static void renderContainer(PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, int containingCountdown) {
    if (containingCountdown < 0) return;

    poseStack.pushPose();
    poseStack.scale(3f, 3f, 3f);
    poseStack.translate(-0.5d, -0.5d, -0.5d);
    Minecraft.getInstance().getBlockRenderer().renderSingleBlock(T7Blocks.ESSENTIA_CONTAINER.get().defaultBlockState(), poseStack, bufferSource, combinedLight, combinedOverlay, ModelData.EMPTY, RenderType.translucent());
    poseStack.popPose();
  }

  /**
   * Translates to the center of the block.
   * Smoothly scales down according to containment progress
   */
  private void setupPose(PoseStack poseStack, int containingCountdown, float partialTick) {
    poseStack.translate(0.5d, 0.5d, 0.5d);
    this.scale = 1f;

    if (containingCountdown < 0) return;

    this.scale = Math.max(Mth.lerp(
        partialTick,
        (float) containingCountdown / AuraNodeBE.MAX_COUNTDOWN,
        (float) (containingCountdown - 1) / AuraNodeBE.MAX_COUNTDOWN
    ), AuraNodeBER.MIN_SCALE);

    poseStack.scale(this.scale, this.scale, this.scale);
  }

  /**
   * TODO: dont forget to handle scale when using custom shader
   */
  private void renderNode(AuraNodeBE be, PoseStack poseStack, @NotNull MultiBufferSource bufferSource) {
    poseStack.pushPose();

    // follows the camera like a particle
    Quaternionf rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
    poseStack.mulPose(rotation);

    var index = 0;
    AspectContainer.from(be)
        .map(IAspectContainer::getAspects)
        .map(AspectMap::toSortedList)
        .ifPresentOrElse(aspectList -> {
          int i;
          for (i = 0; i < aspectList.size(); i++) {
            var stack = aspectList.get(i);
            BERHelper.renderAuraNodeLayer(poseStack, bufferSource, stack.amount() / 32f, stack.aspect().getColor(), 0.6f, i * 0.0001f);
          }
        }, () -> {
          // empty nodes look like small black circles
          BERHelper.renderAuraNodeLayer(poseStack, bufferSource, 0.5f / 32f, 0, 1, 0);
        });

    poseStack.popPose();
  }
}
