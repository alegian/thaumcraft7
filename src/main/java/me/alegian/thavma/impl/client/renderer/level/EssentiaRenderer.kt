package me.alegian.thavma.impl.client.renderer.level

import com.mojang.blaze3d.vertex.PoseStack
import me.alegian.thavma.impl.client.T7RenderTypes
import me.alegian.thavma.impl.client.util.addVertex
import me.alegian.thavma.impl.client.util.setColorDebug
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.Vec3
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.div
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.minus
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.plus
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.times
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

val MAIN_AXIS_RESOLUTION = 10
val CR0SS_AXIS_RESOLUTION = 16

fun trajectory(start: Vec3, end: Vec3): List<Vec3> {
    val dl = (end - start) / MAIN_AXIS_RESOLUTION.toDouble()
    return (0..10).map { start + dl * it.toDouble() }
}

fun renderEssentia(startPos: BlockPos, endPos: BlockPos, poseStack: PoseStack, multiBufferSource: MultiBufferSource) {
    val vc = multiBufferSource.getBuffer(T7RenderTypes.TRANSLUCENT_TRIANGLES)
    val start = startPos.center
    val end = endPos.center

    trajectory(start, end).run {
        for (i in 0 until size - 1) {
            val currentPoint = this[i]
            val nextPoint = this[i + 1]

            val direction = (nextPoint - currentPoint).normalize()
            val randomOtherDirection = (direction - Vec3(0.0, 1.0, 0.0)).normalize()
            val normal1 = direction.cross(randomOtherDirection)
            val normal2 = direction.cross(normal1)

            for (j in 0 .. CR0SS_AXIS_RESOLUTION) {
                val angle = 2 * PI * j / CR0SS_AXIS_RESOLUTION

                val offset = (normal1 * cos(angle) + normal2 * sin(angle)) * 0.5

                vc.addVertex(poseStack, currentPoint + offset).setColorDebug()
                vc.addVertex(poseStack, nextPoint + offset).setColorDebug()
            }
        }
    }
}