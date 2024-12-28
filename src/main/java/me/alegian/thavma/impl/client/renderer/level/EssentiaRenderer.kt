package me.alegian.thavma.impl.client.renderer.level

import com.mojang.blaze3d.vertex.PoseStack
import me.alegian.thavma.impl.client.T7RenderTypes
import me.alegian.thavma.impl.client.util.addVertex
import me.alegian.thavma.impl.client.util.setColorDebug
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.core.BlockPos

fun renderEssentia(startPos: BlockPos, endPos: BlockPos, poseStack: PoseStack, multiBufferSource: MultiBufferSource) {
    val vc = multiBufferSource.getBuffer(T7RenderTypes.TRANSLUCENT_TRIANGLES)
    val start = startPos.center
    val end = endPos.center

    vc.addVertex(poseStack, start).setColorDebug()
    vc.addVertex(poseStack, end).setColorDebug()
    vc.addVertex(poseStack, end.add(1.0, 0.0, 0.0)).setColorDebug()
    vc.addVertex(poseStack, start.add(1.0, 0.0, 0.0)).setColorDebug()
    vc.addVertex(poseStack, start).setColorDebug()
}