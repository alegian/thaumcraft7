package me.alegian.thavma.impl.client.screen

import me.alegian.thavma.impl.client.texture.Texture
import me.alegian.thavma.impl.common.menu.WorkbenchMenu
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

private val WORKBENCH_BG = Texture("gui/container/arcane_workbench/bg", 214, 132, 256, 256)
private val RESULT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench/result_slot", 34, 34)
private val ASPECT_SLOT_TEXTURE = Texture("gui/container/arcane_workbench/aspect_slot", 25, 25)
private val SLOTS = (0..8).map { Texture("gui/container/arcane_workbench/crafting_slots/$it", 21, 21) }
private const val BORDER = 5
private const val WOOD_SIZE = 122

open class WorkbenchScreenRemake(val menu: WorkbenchMenu, pPlayerInventory: Inventory, pTitle: Component) : T7ContainerScreen<WorkbenchMenu>(menu, pPlayerInventory, pTitle, WORKBENCH_BG) {
  override fun ComposeContext.layout() {
    Padding(BORDER) {
      Row {
        Box(Modifier().width(WOOD_SIZE).center()) {
          Box(Modifier().width(SLOTS[0].width * 3).height(SLOTS[0].height * 3).center().color(0xFF0000FF.toInt())) {
            addRenderableOnly(slotGrid(3, 3, menu.craftingContainer.range.slots) { i, j -> SLOTS[i * 3 + j] })
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