package me.alegian.thavma.impl.client.renderer.entity

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import me.alegian.thavma.impl.client.T7PoseStack
import me.alegian.thavma.impl.client.renderer.level.renderEssentia
import me.alegian.thavma.impl.common.entity.VisEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.HumanoidArm
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.phys.Vec3
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.plus

@OnlyIn(Dist.CLIENT)
class VisER(pContext: EntityRendererProvider.Context) : EntityRenderer<VisEntity>(pContext) {
    override fun render(visEntity: VisEntity, pEntityYaw: Float, pPartialTick: Float, pPoseStack: PoseStack, pBufferSource: MultiBufferSource, pPackedLight: Int) {
        if (visEntity.player == null) return

        val t7pose = T7PoseStack(pPoseStack)
        t7pose.push()
        t7pose.translateNegative(visEntity.position()) // we are inside an entity renderer
        val playerPos = preparePlayerHandPose(pPartialTick, (visEntity.player as LocalPlayer?)!!, t7pose)

        //VisRenderer.render(visEntity.position(), t7pose, pBufferSource, visEntity.tickCount + pPartialTick);

        renderEssentia(playerPos, visEntity.position(), pPoseStack, pBufferSource, visEntity.tickCount + pPartialTick)
        t7pose.pop()
    }

    override fun getTextureLocation(pEntity: VisEntity): ResourceLocation {
        return InventoryMenu.BLOCK_ATLAS
    }

    /**
     * The Vis Entity does not have a strict bounding box,
     * so we never cull it to avoid rendering bugs at the edge
     * of the screen.
     */
    override fun shouldRender(pLivingEntity: VisEntity, pCamera: Frustum, pCamX: Double, pCamY: Double, pCamZ: Double): Boolean {
        return true
    }

    companion object {
        /**
         * Translate the Pose to the Player's hand. This is done approximately, and does not follow
         * the PlayerModel's animations, because using these animation poses introduces rotations and
         * reflections that the VisRenderer cannot handle.
         */
        private fun preparePlayerHandPose(pPartialTick: Float, player: LocalPlayer, t7pose: T7PoseStack): Vec3 {
            var position = player.getPosition(pPartialTick)

            val arm = player.mainArm

            // for first person, if it is the client player, we follow the camera
            if (player === Minecraft.getInstance().player && Minecraft.getInstance().options.cameraType.isFirstPerson) {
                val angle = Math.PI / 2 - player.getViewYRot(pPartialTick) / 360f * 2 * Math.PI
                val translation = player.getViewVector(pPartialTick).normalize().scale(.1)
                position += Vec3(0.0, player.eyeHeight + 0.01, 0.0)
                position += translation
                val horizontalOffset = Vec3(0.0, 0.0, (if (arm == HumanoidArm.RIGHT) -.06f else .06f).toDouble())
                position += horizontalOffset.yRot(angle.toFloat())
            } else { // for third person, we follow body rotation. TODO: fix
                val angle = Math.PI / 2 - player.getPreciseBodyRotation(pPartialTick) / 360f * 2 * Math.PI
                t7pose.rotate(Axis.YP, angle)
                t7pose.translate(-1.0, (player.eyeHeight - .56f).toDouble(), (if (arm == HumanoidArm.RIGHT) -.4f else .4f).toDouble())
                t7pose.rotate(Axis.YP, -angle)
            }
            return position
        }
    }
}
