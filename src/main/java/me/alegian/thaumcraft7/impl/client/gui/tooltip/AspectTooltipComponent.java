package me.alegian.thaumcraft7.impl.client.gui.tooltip;

import me.alegian.thaumcraft7.impl.common.aspect.AspectHelper;
import me.alegian.thaumcraft7.impl.common.aspect.AspectMap;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AspectTooltipComponent implements TooltipComponent {
  private static AspectMap aspectMap;

  public AspectTooltipComponent(ItemStack itemStack) {
    aspectMap = AspectHelper.getAspects(itemStack.getItem());
  }

  public AspectMap getAspectMap() {
    if (aspectMap == null) return AspectMap.EMPTY;
    return aspectMap;
  }
}
