package me.alegian.thavma.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.alegian.thavma.impl.client.T7PoseStack;
import me.alegian.thavma.impl.client.renderer.VisRenderer;
import me.alegian.thavma.impl.common.entity.VisEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VisER extends EntityRenderer<VisEntity> {

  public VisER(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(VisEntity visEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
    if (visEntity.getPlayer() == null) return;

    T7PoseStack t7pose = new T7PoseStack(pPoseStack);
    t7pose.push();
    t7pose.translateNegative(visEntity.position()); // we are inside an entity renderer

    preparePlayerHandPose(pPartialTick, (LocalPlayer) visEntity.getPlayer(), t7pose);

    VisRenderer.render(visEntity.position(), t7pose, pBufferSource, visEntity.tickCount + pPartialTick);

    t7pose.pop();
  }

  /**
   * Translate the Pose to the Player's hand. This is done approximately, and does not follow
   * the PlayerModel's animations, because using these animation poses introduces rotations and
   * reflections that the VisRenderer cannot handle.
   */
  private static void preparePlayerHandPose(float pPartialTick, LocalPlayer player, T7PoseStack t7pose) {
    Vec3 playerPos = player.getPosition(pPartialTick);
    t7pose.translate(playerPos);

    var arm = player.getMainArm();

    // for first person, if it is the client player, we follow the camera
    if (player == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
      var angle = Math.PI / 2 - player.getViewYRot(pPartialTick) / 360F * 2 * Math.PI;
      var translation = player.getViewVector(pPartialTick).normalize().scale(.1f);
      t7pose.translate(0, player.getEyeHeight() + 0.01, 0);
      t7pose.translate(translation);
      t7pose.rotate(Axis.YP, angle);
      t7pose.translate(0, 0, arm == HumanoidArm.RIGHT ? -.06f : .06f);
      t7pose.rotate(Axis.YP, -angle);
    } else { // for third person, we follow body rotation
      var angle = Math.PI / 2 - player.getPreciseBodyRotation(pPartialTick) / 360F * 2 * Math.PI;
      t7pose.rotate(Axis.YP, angle);
      t7pose.translate(-1f, player.getEyeHeight() - .56f, arm == HumanoidArm.RIGHT ? -.4f : .4f);
      t7pose.rotate(Axis.YP, -angle);
    }
  }

  @Override
  public ResourceLocation getTextureLocation(VisEntity pEntity) {
    return InventoryMenu.BLOCK_ATLAS;
  }

  /**
   * The Vis Entity does not have a strict bounding box,
   * so we never cull it to avoid rendering bugs at the edge
   * of the screen.
   */
  @Override
  public boolean shouldRender(VisEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
    return true;
  }
}
