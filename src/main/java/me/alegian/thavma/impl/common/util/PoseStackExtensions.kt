package me.alegian.thavma.impl.common.util

import com.mojang.blaze3d.vertex.PoseStack

fun PoseStack.use(toRun: PoseStack.() -> Unit) {
  pushPose()
  toRun()
  popPose()
}