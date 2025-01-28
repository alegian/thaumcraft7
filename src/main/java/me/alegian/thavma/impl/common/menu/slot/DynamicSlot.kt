package me.alegian.thavma.impl.common.menu.slot

import me.alegian.thavma.impl.common.menu.Menu

/**
 * Used for adding size to slots, allowing them to be dynamically positioned
 */
interface DynamicSlot<T : Menu> {
  var actualX: Int

  var actualY: Int

  var size: Int

  val menu: T
}
