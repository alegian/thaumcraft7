package me.alegian.thavma.impl.common.aspect;

import me.alegian.thavma.impl.init.registries.T7DataMaps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class AspectHelper {
  public static boolean hasAspects(ItemEntity itemEntity) {
    return AspectHelper.hasAspects(itemEntity.getItem());
  }

  public static boolean hasAspects(ItemStack itemStack) {
    return AspectHelper.getAspects(itemStack) != null;
  }

  public static AspectMap getAspects(ItemEntity itemEntity) {
    return AspectHelper.getAspects(itemEntity.getItem());
  }

  /**
   * This method checks for Block aspects before returning Item aspects.
   */
  public static AspectMap getAspects(ItemLike itemLike) {
    var item = itemLike.asItem();
    if (item instanceof BlockItem blockItem)
      return BuiltInRegistries.BLOCK.wrapAsHolder(blockItem.getBlock()).getData(T7DataMaps.AspectContent.INSTANCE.getBLOCK());
    return BuiltInRegistries.ITEM.wrapAsHolder(item).getData(T7DataMaps.AspectContent.INSTANCE.getITEM());
  }

  public static AspectMap getAspects(ItemStack itemStack) {
    var itemAspects = AspectHelper.getAspects(itemStack.getItem());
    if (itemAspects == null) return null;
    return itemAspects.scale(itemStack.getCount());
  }
}
