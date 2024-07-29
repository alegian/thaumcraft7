package me.alegian.thaumcraft7.api.capability;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class VisStorageHelper {
  /*
   * always check hasVis or getVisItem before using
   */
  public static float getVisStored(ItemStack stack) {
    return Objects.requireNonNull(stack.getCapability(T7Capabilities.VisStorage.ITEM)).getVisStored();
  }

  /*
   * always check hasVis or getVisItem before using
   */
  public static float getMaxVisStored(ItemStack stack) {
    return Objects.requireNonNull(stack.getCapability(T7Capabilities.VisStorage.ITEM)).getMaxVisStored();
  }

  /*
   * always check hasVis or getVisItem before using
   */
  public static float extractVis(ItemStack stack, float maxExtract) {
    return Objects.requireNonNull(stack.getCapability(T7Capabilities.VisStorage.ITEM)).extractVis(maxExtract);
  }

  /*
   * always check hasVis or getVisItem before using
   */
  public static float receiveVis(ItemStack stack, float maxReceive) {
    return Objects.requireNonNull(stack.getCapability(T7Capabilities.VisStorage.ITEM)).receiveVis(maxReceive);
  }

  public static boolean hasVis(ItemStack stack) {
    return stack.getCapability(T7Capabilities.VisStorage.ITEM) != null;
  }

  /*
   * may return null if no vis item in hand
   */
  public static ItemStack getVisItemInHand(Player player) {
    var main = player.getItemInHand(InteractionHand.MAIN_HAND);
    var off = player.getItemInHand(InteractionHand.OFF_HAND);

    if (hasVis(main)) return main;
    else if (hasVis(off)) return off;
    return null;
  }
}
