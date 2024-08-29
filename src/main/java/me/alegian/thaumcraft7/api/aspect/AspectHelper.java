package me.alegian.thaumcraft7.api.aspect;

import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class AspectHelper {
  public static boolean hasAspects(ItemEntity itemEntity) {
    return hasAspects(itemEntity.getItem());
  }

  public static boolean hasAspects(ItemStack itemStack) {
    return getAspects(itemStack) != null;
  }

  public static AspectList getAspects(ItemEntity itemEntity) {
    return getAspects(itemEntity.getItem());
  }

  /**
   * This method checks for Block aspects before returning Item aspects.
   */
  public static AspectList getAspects(ItemStack itemStack) {
    if (itemStack.getItem() instanceof BlockItem blockItem)
      return blockItem.getBlock().builtInRegistryHolder().getData(T7DataMaps.AspectContent.BLOCK);
    return itemStack.getItemHolder().getData(T7DataMaps.AspectContent.ITEM);
  }
}
