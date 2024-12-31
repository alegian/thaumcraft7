package me.alegian.thavma.impl.client.renderer.level

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
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
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

const val MAIN_AXIS_RESOLUTION = 32
const val CR0SS_AXIS_RESOLUTION = 16

fun trajectory(start: Vec3, end: Vec3): List<Vec3> {
    val dl = (end - start) / MAIN_AXIS_RESOLUTION.toDouble()
    return (0..MAIN_AXIS_RESOLUTION).map { start + dl * it.toDouble() }
}

fun renderEssentia(startPos: BlockPos, endPos: BlockPos, poseStack: PoseStack, multiBufferSource: MultiBufferSource, ticks: Float) {
    val vc = multiBufferSource.getBuffer(T7RenderTypes.TRANSLUCENT_TRIANGLES)
    val start = startPos.center
    val end = endPos.center

    trajectory(start, end).run {
        renderVariableRadiusCylinder(this, vc, poseStack, ticks)
    }
}

private fun renderVariableRadiusCylinder(trajectory: List<Vec3>, vc: VertexConsumer, poseStack: PoseStack, ticks: Float) {
    for (i in 0 until trajectory.size - 1) {
        val currentPoint = trajectory[i]
        val nextPoint = trajectory[i + 1]

        val direction = (nextPoint - currentPoint).normalize()
        val randomOtherDirection = (direction - Vec3(0.0, 1.0, 0.0)).normalize()
        val normal1 = direction.cross(randomOtherDirection)
        val normal2 = direction.cross(normal1)

        val radius1 = oscillatingRadius(i, trajectory.size - 1, ticks)
        val radius2 = oscillatingRadius(i + 1, trajectory.size - 1, ticks)

        for (j in 0..CR0SS_AXIS_RESOLUTION) {
            val angle = 2 * PI * j / CR0SS_AXIS_RESOLUTION

            val normalizedOffset = normal1 * cos(angle) + normal2 * sin(angle)

            vc.addVertex(poseStack, currentPoint + normalizedOffset * radius1).setColorDebug()
            vc.addVertex(poseStack, nextPoint + normalizedOffset * radius2).setColorDebug()
        }
    }
}

/**
 * Determines whether a point in the trajectory is considered an endpoint
 * or not (whether it is close to the start or end)
 */
private fun isEndpoint(i: Int, maxI: Int): Boolean {
    val endpointRange = MAIN_AXIS_RESOLUTION / 8
    return i < endpointRange || i > maxI - endpointRange
}

/**
 * Calculates the of the cylinder at the current point in the trajectory.
 * Endpoint radii are multiplied with an extra term to avoid open ends.
 */
private fun oscillatingRadius(i: Int, maxI: Int, ticks: Float): Double {
    val timePhase = 1.5 * 2 * PI * ticks / 20
    val default = 0.18 + 0.04 * sin(i * 4 * 2 * PI / MAIN_AXIS_RESOLUTION + timePhase)
    if (isEndpoint(i, maxI)) return default * abs(sin(2 * PI * i * 2 / maxI))
    return default
}
