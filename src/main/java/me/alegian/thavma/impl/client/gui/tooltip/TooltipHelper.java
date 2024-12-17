package me.alegian.thavma.impl.client.gui.tooltip;

import me.alegian.thavma.impl.common.aspect.AspectMap;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TooltipHelper {
  public static MutableComponent containedPrimals(AspectMap contents) {
    var sequence = Component.empty();
    for (int i = 0; i < Aspects.INSTANCE.getPRIMAL_ASPECTS().size(); i++) {
      var a = Aspects.INSTANCE.getPRIMAL_ASPECTS().get(i).get();
      var newPart = Component.literal(String.valueOf(contents.get(a))).withColor(a.getColor());
      sequence.append(newPart);
      if (i != Aspects.INSTANCE.getPRIMAL_ASPECTS().size() - 1) sequence.append(Component.literal(" | "));
    }
    return sequence;
  }
}
