package me.alegian.thaumcraft7.impl.client.gui.tooltip;

import me.alegian.thaumcraft7.api.aspect.AspectHelper;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.aspect.AspectStack;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AspectTooltipComponent implements TooltipComponent {
  private static AspectList aspectList;

  public AspectTooltipComponent(ItemStack itemStack) {
    aspectList = AspectHelper.getAspects(itemStack);
  }

  public List<AspectStack> getDisplayedAspectList(){
    if(aspectList == null) return List.of();
    return aspectList.aspectSet().stream().map(a -> new AspectStack(a, aspectList.get(a))).toList();
  }
}
