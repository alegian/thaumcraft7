package me.alegian.thavma.impl.init.registries;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class T7AttributeModifiers {
  public static class StepHeight {
    public static final ResourceLocation LOCATION = Thavma.Companion.rl("step_height");
    public static final AttributeModifier MODIFIER =
        new AttributeModifier(
            StepHeight.LOCATION,
            0.5,
            AttributeModifier.Operation.ADD_VALUE
        );
  }

  /**
   * Different sources of revealing should have different attribute modifiers,
   * otherwise race conditions may occur
   */
  public static class Revealing {
    public static final ResourceLocation LOCATION = Thavma.Companion.rl("revealing");

    public static final AttributeModifier GOGGLES =
        new AttributeModifier(
            Revealing.LOCATION.withSuffix("goggles"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
    public static final AttributeModifier GOGGLES_ACCESSORY =
        new AttributeModifier(
            Revealing.LOCATION.withSuffix("goggles_accessory"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
    public static final AttributeModifier OCULUS =
        new AttributeModifier(
            Revealing.LOCATION.withSuffix("oculus"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        );
  }
}
