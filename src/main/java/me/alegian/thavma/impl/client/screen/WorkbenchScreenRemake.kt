package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.menu.WorkbenchMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

private val WORKBENCH_BG = Texture("gui/container/arcane_workbench", 214, 132, 256, 256)
private val INVENTORY_BG = Texture("gui/container/inventory", 174, 97, 256, 256)
private val SLOT_TEXTURE = Texture("gui/container/arcane_workbench_slot", 18, 18)
private val RESULT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_result_slot", 34, 34)
private val ASPECT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_aspect_slot", 25, 25)
private val GAP = 4
private val BORDER = 5
private val WOOD_SIZE = 122
private val INVENTORY_PADDING = 6
private val HOTBAR_GAP = 4

open class WorkbenchScreenRemake(pMenu: WorkbenchMenu, pPlayerInventory: Inventory, pTitle: Component) : AbstractContainerScreen<WorkbenchMenu>(pMenu, pPlayerInventory, pTitle) {
  override fun init() {
    super.init()
    val lineHeight = this.font.lineHeight

    Root(width, height) {
      Box(
        Modifier().center()
      ) {
        Column(
          Modifier()
            .size(WORKBENCH_BG)
            .extendBelow(INVENTORY_BG)
            .extendBelow(GAP)
            .extendBelow(lineHeight)
            .centerX()
        ) {
          Box(Modifier().height(lineHeight)) {
            addRenderableOnly(text(this@WorkbenchScreenRemake.title, 0x83FF9B))
          }
          Box(Modifier().size(WORKBENCH_BG)) {
            addRenderableOnly(texture(WORKBENCH_BG))
            Padding(BORDER) {
              Row {
                Box(Modifier().width(WOOD_SIZE).color(0xFFFF00FF.toInt())) {
                  addRenderableOnly(debugRect())
                }
                Box(Modifier().width(BORDER))
                Box(Modifier().color(0xFF0000FF.toInt())) {
                  addRenderableOnly(debugRect())
                }
              }
            }
          }
          Box(Modifier().height(GAP))
          Box(Modifier().size(INVENTORY_BG)) {
            addRenderableOnly(texture(INVENTORY_BG))
            PaddingX(INVENTORY_PADDING) {
              Column {
                Box(
                  Modifier()
                    .height(INVENTORY_PADDING)
                    .extendBelow(lineHeight)
                    .centerY()
                ) {
                  Box(Modifier().height(lineHeight)) {
                    addRenderableOnly(text(this@WorkbenchScreenRemake.playerInventoryTitle))
                  }
                }
                Box(Modifier().color(0xFF00FFFF.toInt())) {
                  addRenderableOnly(debugRect())
                }
              }
            }
          }
        }
      }
    }
  }

  override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {}

  override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {}
}