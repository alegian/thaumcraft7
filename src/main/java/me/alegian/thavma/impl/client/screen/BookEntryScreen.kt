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
                Box(Modifier().width(100).height(100).color(0xFFFF0000.toInt())) {
                    addRenderableOnly(rect())
                }
                Box(Modifier().width(30).height(30).color(0xFF00FF00.toInt())) {
                    addRenderableOnly(rect())
                }
                Box(Modifier().width(50).color(0xFF0000FF.toInt())) {
                    addRenderableOnly(rect())
                    Box(Modifier().width(20).color(0xFF00FF00.toInt())) {
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
    ComposeContext(Shape.BOX, width, height, 0, 0, 0).children()
}

private fun ComposeContext.Box(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) {
    modifier.shape(Shape.BOX)
    ComposeContext.Builder(this).apply(modifier).build().children()
}

private fun ComposeContext.Column(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) {
    modifier.shape(Shape.COLUMN)
    ComposeContext.Builder(this).apply(modifier).build().children()
}

class Modifier {
    private val mutations: MutableList<ComposeContext.() -> Unit> = mutableListOf()

    fun shape(shape: Shape) = apply { mutations.add { this.shape = shape } }
    fun width(width: Int) = apply { mutations.add { this.width = width } }
    fun height(height: Int) = apply { mutations.add { this.height = height } }
    fun color(color: Int) = apply { mutations.add { this.color = color } }
    fun top(top: Int) = apply { mutations.add { this.top = top } }
    fun left(left: Int) = apply { mutations.add { this.left = left } }

    fun apply(parent : ComposeContext) {
        mutations.forEach { it.invoke(parent) }
    }
}

class ComposeContext(var shape: Shape, var width: Int, var height: Int, var color: Int, var top: Int, var left: Int) {
    fun rect() = Renderable { guiGraphics, _, _, _ ->
        guiGraphics.fill(left, top, left + width, top + height, color)
    }

    class Builder(private val parent: ComposeContext) {
        private var shape = parent.shape
        private var width = parent.width
        private var height = parent.height
        private var color = parent.color
        private var top = parent.top
        private var left = parent.left
        private val context = ComposeContext(shape, width, height, color, top, left)

        private fun parentSideEffects(){
            if (parent.shape == Shape.COLUMN) {
                parent.height -= context.height
                parent.top += context.height
            }
        }

        fun apply(modifier: Modifier): Builder {
            modifier.apply(context)
            return this
        }

        fun build(): ComposeContext{
            parentSideEffects()
            return context
        }
    }
}

enum class Shape {
    BOX, COLUMN, ROW
}