package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.client.util.blit
import me.alegian.thavma.impl.client.util.drawString
import me.alegian.thavma.impl.client.util.usePose
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

private val BG = Texture("gui/book/background", 510, 282, 512, 512)

class BookEntryScreen : Screen(Component.literal("Book Entry")) {
    private var left = 0.0
    private var top = 0.0
    private val testText = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
        guiGraphics.usePose {
            translate(left + 30, top + 30, 0.0)
            guiGraphics.drawString(font, "Reaves likes cockroaches")
        }
    }

    override fun init() {
        super.init()
        left = (width - BG.width) / 2.0
        top = (height - BG.height) / 2.0
        addRenderableOnly(testText)

        Root(width, height) {
            Column {
                Box(width = 100, height = 100, 0xFFFF0000.toInt()) {

                    addRenderableOnly(rect())

                }
                Box(width = 30, height = 30, color = 0xFF00FF00.toInt()) {
                    addRenderableOnly(rect())
                }
                Box(width = 50, pY = 30, color = 0xFF0000FF.toInt()) {
                    addRenderableOnly(rect())
                    Box(width = 20, color = 0xFF00FF00.toInt()) {
                        addRenderableOnly(rect())
                    }
                }
            }
        }
    }

    override fun renderBackground(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderTransparentBackground(guiGraphics)
        guiGraphics.usePose {
            translate(left, top, 0.0)
            guiGraphics.blit(BG)
        }
    }
}

private fun Root(width: Int, height: Int, children: ComposeContext.() -> Unit) {
    ComposeContext(Shape.BOX, width, height, 0, 0).children()
}

private fun ComposeContext.Box(width: Int = this.width, height: Int = this.height, color: Int = this.color, pY: Int = 0, children: ComposeContext.() -> Unit) {
    this.height -= 2*pY
    this.top += pY
    ComposeContext(Shape.BOX, width, height, color, this.top).children()
    if (this.shape == Shape.COLUMN) {
        this.height -= height
        this.top += height
    }
}

private fun ComposeContext.Column(children: ComposeContext.() -> Unit) {
    ComposeContext(Shape.COLUMN, this.width, this.height, this.color, this.top).children()
}

class ComposeContext(val shape: Shape, var width: Int, var height: Int, val color: Int, var top: Int) {
    fun rect() = Renderable { guiGraphics, _, _, _ ->
        guiGraphics.fill(0, top, width, top + height, color)
    }
}

enum class Shape {
    BOX, COLUMN, ROW
}