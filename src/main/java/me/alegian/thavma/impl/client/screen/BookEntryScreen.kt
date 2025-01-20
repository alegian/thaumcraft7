package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.client.util.blit
import me.alegian.thavma.impl.client.util.drawString
import me.alegian.thavma.impl.client.util.usePose
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

private val BG = Texture("gui/book/background", 510, 282, 512, 512)

class BookEntryScreen : Screen(Component.literal("Book Entry")) {
  private var left = 0.0
  private var top = 0.0

  override fun init() {
    super.init()
    left = (width - BG.width) / 2.0
    top = (height - BG.height) / 2.0

    Root(width, height) {
      Box(
        Modifier().center()
      ) {
        Row(
          Modifier().size(BG)
        ) {
          Box(
            Modifier().maxWidth(0.5f).color(0xFFFF00FF.toInt())
          ) {
            addRenderableOnly(debugRect())
            PaddingY(30) {
              Box(Modifier().color(0xFFFF0000.toInt())) {
                addRenderableOnly(debugRect())
                PaddingX(30) {
                  addRenderableOnly(text("Reaves likes cockroaches"))
                }
              }
            }
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
  ComposeContext(Shape.BOX, Alignment.START, Alignment.START, width, height, 0, 0, 0).children()
}

private fun ComposeContext.Box(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) = Component(Shape.BOX, modifier, children)
private fun ComposeContext.Column(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) = Component(Shape.COLUMN, modifier, children)
private fun ComposeContext.Row(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) = Component(Shape.ROW, modifier, children)

private fun ComposeContext.PaddingX(size: Int, children: ComposeContext.() -> Unit) {
  Row {
    Box(Modifier().width(size)) {}
    Component(Shape.BOX, Modifier().shrinkX(size), children)
    Box(Modifier().width(size)) {}
  }
}

private fun ComposeContext.PaddingY(size: Int, children: ComposeContext.() -> Unit) {
  Column {
    Box(Modifier().height(size)) {}
    Component(Shape.BOX, Modifier().shrinkY(size), children)
    Box(Modifier().height(size)) {}
  }
}

private fun ComposeContext.Padding(size: Int, children: ComposeContext.() -> Unit) {
  PaddingX(size) {
    PaddingY(size, children)
  }
}

fun ComposeContext.Component(shape: Shape, modifier: Modifier, children: ComposeContext.() -> Unit) {
  modifier.shape(shape)
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
  fun size(size: Int) = width(size).height(size)
  fun size(texture: Texture) = width(texture.width).height(texture.height)

  fun shrinkX(x: Int) = apply { mutations.add { this.width -= x } }
  fun shrinkY(y: Int) = apply { mutations.add { this.height -= y } }

  fun maxWidth(scale: Float) = apply { mutations.add { this.width = (this.width * scale).toInt() } }
  fun maxHeight(scale: Float) = apply { mutations.add { this.height = (this.height * scale).toInt() } }

  fun centerX() = apply { mutations.add { this.alignmentX = Alignment.CENTER } }
  fun centerY() = apply { mutations.add { this.alignmentY = Alignment.CENTER } }
  fun center() = centerY().centerX()

  fun endX() = apply { mutations.add { this.alignmentX = Alignment.END } }
  fun endY() = apply { mutations.add { this.alignmentY = Alignment.END } }

  fun apply(parent: ComposeContext) {
    mutations.forEach { it.invoke(parent) }
  }
}

class ComposeContext(var shape: Shape, var alignmentX: Alignment, var alignmentY: Alignment, var width: Int, var height: Int, var color: Int, var top: Int, var left: Int) {
  fun debugRect() = Renderable { guiGraphics, _, _, _ ->
    guiGraphics.fill(left, top, left + width, top + height, color)
  }

  fun text(content: String) = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
    guiGraphics.usePose {
      translate(left.toDouble(), top.toDouble(), 0.0)
      guiGraphics.drawString(Minecraft.getInstance().font, content)
    }
  }

  class Builder(private val parent: ComposeContext) {
    private val context = ComposeContext(
      Shape.BOX,
      Alignment.START,
      Alignment.START,
      parent.width,
      parent.height,
      parent.color,
      parent.top,
      parent.left
    )

    private fun parentSideEffects() {
      if (parent.shape == Shape.COLUMN) {
        parent.height -= context.height
        parent.top += context.height
      }
      if (parent.shape == Shape.ROW) {
        parent.width -= context.width
        parent.left += context.width
      }
      if (parent.alignmentX == Alignment.CENTER) {
        context.left += (parent.width - context.width) / 2
      }
      if (parent.alignmentY == Alignment.CENTER) {
        context.top += (parent.height - context.height) / 2
      }
      if (parent.alignmentX == Alignment.END) {
        context.left += parent.width - context.width
      }
      if (parent.alignmentY == Alignment.END) {
        context.top += parent.height - context.height
      }
    }

    fun apply(modifier: Modifier): Builder {
      modifier.apply(context)
      return this
    }

    fun build(): ComposeContext {
      parentSideEffects()
      return context
    }
  }
}

enum class Shape {
  BOX, COLUMN, ROW
}

enum class Alignment {
  START, CENTER, END
}