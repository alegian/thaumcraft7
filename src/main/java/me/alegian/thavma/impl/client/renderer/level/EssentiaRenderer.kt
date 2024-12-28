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

fun trajectory(start: Vec3, end: Vec3): List<Vec3> {
    val dl = (end - start) / 10.0
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

            vc.addVertex(poseStack, currentPoint).setColorDebug()
            //vc.addVertex(poseStack, nextPoint).setColorDebug()
            vc.addVertex(poseStack, currentPoint + normal2).setColorDebug()
            //vc.addVertex(poseStack, nextPoint + normal2).setColorDebug()
        }
    }
}