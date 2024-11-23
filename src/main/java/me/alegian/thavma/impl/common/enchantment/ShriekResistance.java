package me.alegian.thavma.impl.common.enchantment;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ShriekResistance {
  public static final ResourceLocation LOCATION = Thavma.rl("shriek_resistance");

  /**
   * Custom version of EnchantmentHelper.getDamageProtection
   * Divides by 4 to account for 4 pieces of armor.
   * Does not account for other enchants like Protection, these should have no effect.
   */
  public static float getDamageProtection(ServerLevel level, LivingEntity entity, DamageSource damageSource) {
    MutableFloat mutablefloat = new MutableFloat(0.0F);
    EnchantmentHelper.runIterationOnEquipment(
        entity,
        (enchantmentHolder, enchantmentLevel, enchantedItemInUse) -> {
          if (enchantmentHolder.is(ShriekResistance.LOCATION))
            enchantmentHolder.value()
                .modifyDamageProtection(level, enchantmentLevel, enchantedItemInUse.itemStack(), entity, damageSource, mutablefloat);
        });
    return mutablefloat.floatValue() / 4f;
  }
}
