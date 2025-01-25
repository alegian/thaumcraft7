package me.alegian.thavma.impl.client.util

import com.mojang.blaze3d.vertex.PoseStack
import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.util.use
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

fun GuiGraphics.usePose(block: PoseStack.() -> Unit) {
  pose().use(block)
}

fun GuiGraphics.drawString(font: Font, text: String, color: Int = 0) {
  drawString(font, text, 0, 0, color, false)
}

fun GuiGraphics.drawString(font: Font, text: Component, color: Int = 0) {
  drawString(font, text, 0, 0, color, false)
}

fun GuiGraphics.blit(texture: Texture) {
  blit(texture.location, 0, 0, 0f, 0f, texture.width, texture.height, texture.canvasWidth, texture.canvasHeight)
}