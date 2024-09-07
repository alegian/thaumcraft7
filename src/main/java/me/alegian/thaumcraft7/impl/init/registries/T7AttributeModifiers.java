package me.alegian.thaumcraft7.impl.init.registries;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class T7AttributeModifiers {
  public static final AttributeModifier STEP_HEIGHT =
      new AttributeModifier(
          ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "step_height"),
          0.5,
          AttributeModifier.Operation.ADD_VALUE
      );
}
