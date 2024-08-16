package me.alegian.thaumcraft7.impl.common.data.capability;

import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.IAspectContainer;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class AspectContainerHelper {
  public static AspectList getAspectListInHand(Player player) {
    var mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
    var offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);

    IAspectContainer aspectContainer = null;

    if (!mainHandItem.isEmpty())
      aspectContainer = mainHandItem.getCapability(T7Capabilities.AspectContainer.ITEM, null);
    else if (!offHandItem.isEmpty())
      aspectContainer = offHandItem.getCapability(T7Capabilities.AspectContainer.ITEM, null);

    if (aspectContainer == null)
      return null;
    else return aspectContainer.getAspects();
  }
}
