package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.client.util.blit
import me.alegian.thavma.impl.client.util.drawString
import me.alegian.thavma.impl.client.util.usePose
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Renderable
import net.minecraft.network.chat.Component
import kotlin.math.max


fun Root(width: Int, height: Int, children: ComposeContext.() -> Unit) {
  ComposeContext(Shape.BOX, Alignment.START, Alignment.START, width, height, 0, 0, 0).children()
}

fun ComposeContext.Box(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit = {}) = Component(Shape.BOX, modifier, children)
fun ComposeContext.Column(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) = Component(Shape.COLUMN, modifier, children)
fun ComposeContext.Row(modifier: Modifier = Modifier(), children: ComposeContext.() -> Unit) = Component(Shape.ROW, modifier, children)

fun ComposeContext.PaddingX(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(size, size, 0, 0, children)
fun ComposeContext.PaddingY(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(0, 0, size, size, children)
fun ComposeContext.paddingLeft(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(size, 0, 0, 0, children)
fun ComposeContext.paddingRight(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(0, size, 0, 0, children)
fun ComposeContext.paddingTop(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(0, 0, size, 0, children)
fun ComposeContext.paddingBottom(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(0, 0, 0, size, children)
fun ComposeContext.Padding(size: Int, children: ComposeContext.() -> Unit) = PaddingInternal(size, size, size, size, children)

/**
 * Padding is emulated via Rows and Columns with empty boxes
 */
private fun ComposeContext.PaddingInternal(left: Int, right: Int, top: Int, bottom: Int, children: ComposeContext.() -> Unit) {
  Row {
    Box(Modifier().width(left))
    Column(Modifier().shrinkX(right)) {
      Box(Modifier().height(top))
      Component(Shape.BOX, Modifier().shrinkY(bottom), children)
      Box(Modifier().height(bottom))
    }
    Box(Modifier().width(right))
  }
}

fun ComposeContext.Component(shape: Shape, modifier: Modifier, children: ComposeContext.() -> Unit) {
  modifier.shape(shape)
  ComposeContext.Builder(this).apply(modifier).build().children()
}

class Modifier {
  private val mutations: MutableList<ComposeContext.() -> Unit> = mutableListOf()

  fun shape(shape: Shape) = apply { mutations.add { this.shape = shape } }
  fun width(widthIn: Int) = apply { mutations.add { this.width = widthIn } }
  fun height(heightIn: Int) = apply { mutations.add { this.height = heightIn } }
  fun color(color: Int) = apply { mutations.add { this.color = color } }
  fun top(top: Int) = apply { mutations.add { this.top = top } }
  fun left(left: Int) = apply { mutations.add { this.left = left } }
  fun size(size: Int) = width(size).height(size)
  fun size(texture: Texture) = width(texture.width).height(texture.height)

  fun extendBelow(size: Int) = apply {
    mutations.add { this.height += size }
  }

  fun extendBelow(texture: Texture) = apply {
    mutations.add { this.height += texture.height }
    mutations.add { this.width = max(this.width, texture.width) }
  }

  fun extendRight(size: Int) = apply {
    mutations.add { this.width += size }
  }

  fun extendRight(texture: Texture) = apply {
    mutations.add { this.width += texture.width }
    mutations.add { this.height = max(this.height, texture.height) }
  }

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

  fun text(content: String, color: Int = 0) = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
    guiGraphics.usePose {
      translate(left.toDouble(), top.toDouble(), 0.0)
      guiGraphics.drawString(Minecraft.getInstance().font, content, color)
    }
  }

  fun text(content: Component, color: Int = 0) = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
    guiGraphics.usePose {
      translate(left.toDouble(), top.toDouble(), 0.0)
      guiGraphics.drawString(Minecraft.getInstance().font, content, color)
    }
  }

  fun texture(texture: Texture) = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
    guiGraphics.usePose {
      translate(left.toDouble(), top.toDouble(), 0.0)
      guiGraphics.blit(texture)
    }
  }

  fun textureGrid(rows: Int, columns: Int, getTexture: (Int, Int) -> Texture) = Renderable { guiGraphics: GuiGraphics, _: Int, _: Int, _: Float ->
    guiGraphics.usePose {
      translate(left.toDouble(), top.toDouble(), 0.0)
      for (i in 0 until rows) {
        pushPose()
        for (j in 0 until columns) {
          guiGraphics.blit(getTexture(i, j))
          translate(getTexture(0, 0).width.toDouble(), 0.0, 0.0)
        }
        popPose()
        translate(0.0, getTexture(0, 0).height.toDouble(), 0.0)
      }
    }
  }

  class Builder(private val parent: ComposeContext) {
    private val child = ComposeContext(
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
        parent.height -= child.height
        parent.top += child.height
      }
      if (parent.shape == Shape.ROW) {
        parent.width -= child.width
        parent.left += child.width
      }
      if (parent.alignmentX == Alignment.CENTER) {
        child.left += (parent.width - child.width) / 2
      }
      if (parent.alignmentY == Alignment.CENTER) {
        child.top += (parent.height - child.height) / 2
      }
      if (parent.alignmentX == Alignment.END) {
        child.left += parent.width - child.width
      }
      if (parent.alignmentY == Alignment.END) {
        child.top += parent.height - child.height
      }
    }

    fun apply(modifier: Modifier): Builder {
      modifier.apply(child)
      return this
    }

    fun build(): ComposeContext {
      parentSideEffects()
      return child
    }
  }
}

enum class Shape {
  BOX, COLUMN, ROW
}

enum class Alignment {
  START, CENTER, END
}