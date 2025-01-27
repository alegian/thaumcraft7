package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.menu.WorkbenchMenu
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

private val WORKBENCH_BG = Texture("gui/container/arcane_workbench", 214, 132, 256, 256)
private val RESULT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_result_slot", 34, 34)
private val ASPECT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench_aspect_slot", 25, 25)
private val SLOT_BG = Texture("gui/container/arcane_workbench_slot", 18, 18)
private const val BORDER = 5
private const val WOOD_SIZE = 122

open class WorkbenchScreenRemake(val menu: WorkbenchMenu, pPlayerInventory: Inventory, pTitle: Component) : T7ContainerScreen<WorkbenchMenu>(menu, pPlayerInventory, pTitle, WORKBENCH_BG) {
  override fun ComposeContext.layout() {
    Padding(BORDER) {
      Row {
        Box(Modifier().width(WOOD_SIZE).center().color(0xFFFF00FF.toInt())) {
          Box(Modifier().width(SLOT_BG.width * 3).height(SLOT_BG.height * 3).center().color(0xFF0000FF.toInt())) {
            addRenderableOnly(slotGrid(3, 3, menu.craftingContainer.range.slots) { _, _ -> SLOT_BG })
          }
        }
        Box(Modifier().width(BORDER))
        Box(Modifier().color(0xFF0000FF.toInt())) {
          addRenderableOnly(debugRect())
        }
      }
    }
  }
}