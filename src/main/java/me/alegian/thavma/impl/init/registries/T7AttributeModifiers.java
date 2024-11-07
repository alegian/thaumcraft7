package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class T7AttributeModifiers {
  public static class StepHeight {
    public static final ResourceLocation LOCATION = Thavma.id("step_height");
    public static final AttributeModifier MODIFIER =
        new AttributeModifier(
            LOCATION,
            0.5,
            AttributeModifier.Operation.ADD_VALUE
        );
  }

  /**
   * Different sources of revealing should have different attribute modifiers,
   * otherwise race conditions may occur
   */
  public static class Revealing {
    public static final ResourceLocation LOCATION = Thavma.id("revealing");

    public static final AttributeModifier GOGGLES =
        new AttributeModifier(
            LOCATION.withSuffix("goggles"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
    public static final AttributeModifier OCULUS =
        new AttributeModifier(
            LOCATION.withSuffix("oculus"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
  }
}
