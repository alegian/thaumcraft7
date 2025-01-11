package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.T7GuiGraphics
import me.alegian.thavma.impl.client.renderer.AspectRenderer
import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.aspect.AspectStack
import me.alegian.thavma.impl.common.menu.WorkbenchMenu
import me.alegian.thavma.impl.init.registries.deferred.Aspects.PRIMAL_ASPECTS
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.RenderType
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ResultSlot
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max

@OnlyIn(Dist.CLIENT)
open class WorkbenchScreen(pMenu: WorkbenchMenu, pPlayerInventory: Inventory, pTitle: Component) : AbstractContainerScreen<WorkbenchMenu>(pMenu, pPlayerInventory, pTitle) {
  init {
    this.inventoryLabelX += 20
    this.inventoryLabelY += 69
    this.titleLabelY -= 15
    this.imageWidth = max(WORKBENCH_BG.width.toDouble(), INVENTORY_BG.width.toDouble()).toInt()
    this.imageHeight = WORKBENCH_BG.height + PADDING + INVENTORY_BG.height
  }

  override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick)
    this.renderAspects(pGuiGraphics)
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY)
  }

  override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
    guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x83FF9B, false)
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false)
  }

  override fun renderBg(pGuiGraphics: GuiGraphics, pPartialTick: Float, pMouseX: Int, pMouseY: Int) {
    val t7graphics = T7GuiGraphics(pGuiGraphics)
    t7graphics.push()
    t7graphics.translateXY(leftPos.toFloat(), topPos.toFloat())

    t7graphics.blit(WORKBENCH_BG)
    t7graphics.translateXY((WORKBENCH_BG.width - INVENTORY_BG.width) / 2f, WORKBENCH_BG.height.toFloat())
    t7graphics.translateXY(0f, PADDING.toFloat())
    t7graphics.blit(INVENTORY_BG)

    t7graphics.pop()

    for (slot in menu.slots) this.renderSlotBg(t7graphics, slot)
  }

  override fun renderSlotContents(guiGraphics: GuiGraphics, itemstack: ItemStack, slot: Slot, countString: String?) {
    super.renderSlotContents(guiGraphics, itemstack, slot, countString)
    if (slot is ResultSlot && !slot.mayPickup(menu.player)) guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), slot.x, slot.y, slot.x + 16, slot.y + 16, 0x50FFFFFF)
  }

  protected fun renderSlotBg(t7graphics: T7GuiGraphics, slot: Slot) {
    var texture = SLOT_TEXTURE
    if (slot is ResultSlot) texture = RESULT_SLOT_TEXTURE

    t7graphics.push()
    // go to the center of the slot and draw the texture centered there
    t7graphics.translateXY(this.leftPos + slot.x + SLOT_SIZE / 2f, this.topPos + slot.y + SLOT_SIZE / 2f)
    t7graphics.translateXY(-texture.width / 2f, -texture.height / 2f)
    t7graphics.blit(texture)
    t7graphics.pop()
  }

  protected fun renderAspects(guiGraphics: GuiGraphics) {
    val BASE_RADIUS = 52
    val ANGLE = 360f / PRIMAL_ASPECTS.size
    val middleSlot = menu.slots[4]
    val t7graphics = T7GuiGraphics(guiGraphics)

    t7graphics.push()
    t7graphics.translateXY(leftPos.toFloat(), topPos.toFloat())
    t7graphics.translateXY(middleSlot.x.toFloat(), middleSlot.y.toFloat())

    // draw aspects at hexagon points (or N-gon if more primals are added by addons)
    for ((i, a) in PRIMAL_ASPECTS.withIndex()) {
      val requiredAmount = menu.requiredAspects[a.get()]
      val requiredStack = AspectStack(a.get(), requiredAmount)
      t7graphics.push()
      t7graphics.rotateZ(ANGLE * i)
      val fac = abs(cos(2 * Math.PI / PRIMAL_ASPECTS.size * i))
      t7graphics.translateXY((BASE_RADIUS * (1 - 0.16 * fac * fac)).toFloat(), 0f)
      t7graphics.rotateZ(-ANGLE * i)
      val texture = ASPECT_SLOT_TEXTURE
      t7graphics.blit(texture.location, (SLOT_TEXTURE.width - ASPECT_SLOT_TEXTURE.width) / 2, (SLOT_TEXTURE.height - ASPECT_SLOT_TEXTURE.height) / 2, 0f, 0f, texture.width, texture.height, texture.width, texture.height)
      if (requiredAmount != 0) AspectRenderer.renderAspect(t7graphics, requiredStack, 0, 0)
      t7graphics.pop()
    }

    t7graphics.pop()
  }

  companion object {
    protected const val PADDING: Int = 2
    protected const val SLOT_SIZE: Int = 16
    private val WORKBENCH_BG = Texture("gui/container/arcane_workbench", 216, 134, 255, 255)
    private val INVENTORY_BG = Texture("gui/container/inventory", 176, 99, 255, 255)
    private val SLOT_TEXTURE = Texture("gui/container/arcane_workbench_slot", 18, 18)
    private val RESULT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_result_slot", 34, 34)
    private val ASPECT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_aspect_slot", 25, 25)
  }
}
