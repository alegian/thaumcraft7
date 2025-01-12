package me.alegian.thavma.impl.client.gui.tooltip;

import me.alegian.thavma.impl.common.aspect.AspectHelperKt;
import me.alegian.thavma.impl.common.aspect.AspectMap;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AspectTooltipComponent implements TooltipComponent {
  private static AspectMap aspectMap;

  public AspectTooltipComponent(ItemStack itemStack) {
    AspectTooltipComponent.aspectMap = AspectHelperKt.getAspects(itemStack.getItem());
  }

  public AspectMap getAspectMap() {
    if (AspectTooltipComponent.aspectMap == null) return AspectMap.EMPTY;
    return AspectTooltipComponent.aspectMap;
  }
}
