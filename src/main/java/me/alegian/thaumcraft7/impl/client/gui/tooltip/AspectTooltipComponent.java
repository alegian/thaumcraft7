package me.alegian.thaumcraft7.impl.client.gui.tooltip;

import me.alegian.thaumcraft7.api.aspect.AspectHelper;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AspectTooltipComponent implements TooltipComponent {
  private static AspectList aspectList;

  public AspectTooltipComponent(ItemStack itemStack) {
    aspectList = AspectHelper.getAspects(itemStack);
  }

  public AspectList getAspectList() {
    if (aspectList == null) return AspectList.EMPTY;
    return aspectList;
  }
}
