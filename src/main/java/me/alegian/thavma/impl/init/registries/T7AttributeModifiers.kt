package me.alegian.thavma.impl.init.registries

import me.alegian.thavma.impl.Thavma.rl
import net.minecraft.world.entity.ai.attributes.AttributeModifier

object T7AttributeModifiers {
    object StepHeight {
        val LOCATION = rl("step_height")
        val MODIFIER = AttributeModifier(
            LOCATION,
            0.5,
            AttributeModifier.Operation.ADD_VALUE
        )
    }

    /**
     * Different sources of revealing should have different attribute modifiers,
     * otherwise race conditions may occur
     */
    object Revealing {
        val LOCATION = rl("revealing")

        val GOGGLES = AttributeModifier(
            LOCATION.withSuffix("goggles"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        )

        val GOGGLES_ACCESSORY = AttributeModifier(
            LOCATION.withSuffix("goggles_accessory"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        )

        val OCULUS = AttributeModifier(
            LOCATION.withSuffix("oculus"),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        )
    }
}