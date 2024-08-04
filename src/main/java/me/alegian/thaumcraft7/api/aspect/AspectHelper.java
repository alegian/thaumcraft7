package me.alegian.thaumcraft7.api.aspect;

import me.alegian.thaumcraft7.api.data.map.T7DataMaps;
import net.minecraft.world.entity.item.ItemEntity;
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

  public static AspectList getAspects(ItemStack itemStack) {
    return itemStack.getItemHolder().getData(T7DataMaps.ASPECT_CONTENT);
  }
}
