package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.block.WorkbenchBlock
import me.alegian.thavma.impl.common.menu.WorkbenchMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.MenuAccess
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

private val WORKBENCH_BG = Texture("gui/container/arcane_workbench", 216, 134, 256, 256)
private val INVENTORY_BG = Texture("gui/container/inventory", 176, 99, 256, 256)
private val SLOT_TEXTURE = Texture("gui/container/arcane_workbench_slot", 18, 18)
private val RESULT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_result_slot", 34, 34)
private val ASPECT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_aspect_slot", 25, 25)
private val GAP = 4

open class WorkbenchScreenRemake(val pMenu: WorkbenchMenu, pPlayerInventory: Inventory, pTitle: Component) : Screen(WorkbenchBlock.CONTAINER_TITLE), MenuAccess<WorkbenchMenu> {
  override fun init() {
    super.init()

    Root(width, height) {
      Box(
        Modifier().center()
      ) {
        Column(
          Modifier()
            .size(WORKBENCH_BG)
            .extendBelow(INVENTORY_BG)
            .extendBelow(GAP)
            .centerX()
        ) {
          Box(Modifier().size(WORKBENCH_BG).color(0xFF0000FF.toInt())) {
            addRenderableOnly(texture(WORKBENCH_BG))
          }
          Box(Modifier().height(GAP))
          Box(Modifier().size(INVENTORY_BG).color(0xFFFF00FF.toInt())) {
            addRenderableOnly(texture(INVENTORY_BG))
          }
        }
      }
    }
  }

  override fun renderBackground(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
    renderTransparentBackground(guiGraphics)
  }

  override fun getMenu(): WorkbenchMenu {
    return pMenu
  }
}