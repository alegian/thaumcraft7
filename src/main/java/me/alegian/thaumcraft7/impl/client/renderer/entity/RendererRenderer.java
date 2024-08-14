package me.alegian.thaumcraft7.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.impl.client.T7PoseStack;
import me.alegian.thaumcraft7.impl.client.renderer.VisRenderer;
import me.alegian.thaumcraft7.impl.client.renderer.blockentity.BERHelper;
import me.alegian.thaumcraft7.impl.common.entity.RendererEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RendererRenderer extends EntityRenderer<RendererEntity> {

  public RendererRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(RendererEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
    // general purpose useful stuff
    var minecraft = Minecraft.getInstance();
    if (minecraft.level == null) return;
    var hitResult = minecraft.hitResult;
    if (hitResult == null || hitResult.getType() != HitResult.Type.BLOCK) return;
    var blockPos = ((BlockHitResult) hitResult).getBlockPos();
    var player = minecraft.player;
    if (player == null) return;

    T7PoseStack t7pose = new T7PoseStack(pPoseStack);

    Vec3 playerPos = player.getPosition(pPartialTick);
    t7pose.push();
    t7pose.translateNegative(pEntity.position()); // we are inside an entity renderer
    t7pose.translate(playerPos);

    t7pose.scale(player.getScale());
    var angle = Math.PI/2 - player.yBodyRot/360F * 2 * Math.PI;
    t7pose.rotate(Axis.YP, angle);
    pPoseStack.scale(-1.0F, -1.0F, 1.0F);
    pPoseStack.translate(0.0F, -1.501F, 0.0F);
    PlayerRenderer playerRenderer = (PlayerRenderer) minecraft.getEntityRenderDispatcher().getRenderer(player);
    playerRenderer.getModel().translateToHand(player.getMainArm(), t7pose.poseStack());

    BERHelper.renderAuraNodeLayer(pPoseStack, pBufferSource, 0.5f, 16, 1, 0, 0, 1);
    //VisRenderer.render(blockPos.getCenter(), t7pose, pBufferSource, pEntity.tickCount + pPartialTick);
    t7pose.pop();
  }

  @Override
  public ResourceLocation getTextureLocation(RendererEntity pEntity) {
    return InventoryMenu.BLOCK_ATLAS;
  }

  @Override
  public boolean shouldRender(RendererEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
    return true;
  }
}
