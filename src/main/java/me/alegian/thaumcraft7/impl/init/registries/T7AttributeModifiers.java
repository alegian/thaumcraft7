package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class T7AttributeModifiers {
  public static class StepHeight {
    public static final ResourceLocation LOCATION = Thaumcraft.id("step_height");
    public static final AttributeModifier MODIFIER =
        new AttributeModifier(
            LOCATION,
            0.5,
            AttributeModifier.Operation.ADD_VALUE
        );
  }

  public static class Revealing {
    public static final ResourceLocation LOCATION = Thaumcraft.id("revealing");
    public static final AttributeModifier MODIFIER =
        new AttributeModifier(
            LOCATION,
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
  }
}
