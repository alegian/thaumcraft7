package me.alegian.thavma.impl.common.event;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.enchantment.ShriekResistance;
import me.alegian.thavma.impl.common.entity.EntityHelper;
import me.alegian.thavma.impl.common.item.HammerItem;
import me.alegian.thavma.impl.init.registries.T7AttributeModifiers;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = Thavma.MODID, bus = EventBusSubscriber.Bus.GAME)
public class T7CommonGameEvents {
  private static boolean allowHammerBreakEvents = true;

  @SubscribeEvent
  public static void entityTickPre(EntityTickEvent.Pre event) {
    if (event.getEntity() instanceof LivingEntity livingEntity) {
      AttributeInstance attribute = livingEntity.getAttribute(Attributes.STEP_HEIGHT);
      if (attribute == null) return;

      boolean hasStepHeightFromOtherModifier = attribute.getValue() >= 1.0 && !attribute.hasModifier(T7AttributeModifiers.StepHeight.LOCATION);

      if (!EntityHelper.isEntityWearingBoots(livingEntity) || hasStepHeightFromOtherModifier || livingEntity.isCrouching())
        attribute.removeModifier(T7AttributeModifiers.StepHeight.MODIFIER);
      else attribute.addOrUpdateTransientModifier(T7AttributeModifiers.StepHeight.MODIFIER);
    }
  }

  @SubscribeEvent
  public static void livingDamagePost(LivingDamageEvent.Post event) {
    var itemStack = event.getSource().getWeaponItem();
    if (itemStack == null) return;
    if (!itemStack.getItem().equals(T7Items.ARCANUM_KATANA.get())) return;

    var entity = event.getEntity();
    if (entity.getHealth() <= 10) {
      entity.kill();
      if (event.getSource().getEntity() instanceof ServerPlayer player)
        entity.level().playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
  }

  @SubscribeEvent
  public static void breakBlock(BlockEvent.BreakEvent event) {
    var player = event.getPlayer();
    var itemStack = player.getMainHandItem();
    var item = itemStack.getItem();
    var level = event.getLevel();

    if (player instanceof ServerPlayer serverPlayer && item instanceof HammerItem hammer) {
      // disallow nested hammer break events, to avoid infinite recursion
      if (!T7CommonGameEvents.allowHammerBreakEvents) return;
      T7CommonGameEvents.allowHammerBreakEvents = false;

      hammer.tryBreak3x3exceptOrigin(serverPlayer, level, itemStack);

      T7CommonGameEvents.allowHammerBreakEvents = true;
    }
  }

  @SubscribeEvent
  public static void mobEffectApplicable(MobEffectEvent.Applicable event) {
    if (event.getEffectInstance() == null) return;
    if (event.getEffectInstance().getEffect() != MobEffects.DARKNESS) return;
    if (!EntityHelper.isEntityWearingAccessory(event.getEntity(), T7Items.DAWN_CHARM.get())) return;
    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
  }

  @SubscribeEvent
  public static void preLivingDamage(LivingDamageEvent.Pre event) {
    if (event.getEntity().level() instanceof ServerLevel serverLevel) {
      var sonicType = serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).get(DamageTypes.SONIC_BOOM);
      if (!event.getSource().type().equals(sonicType)) return;

      float damageBlocked = ShriekResistance.getDamageProtection(serverLevel, event.getEntity(), event.getSource());
      if (damageBlocked <= 0.0F) return;

      // ideally would want to apply a reduction here,
      // but warden bypasses all reductions...
      event.getContainer().setNewDamage(Math.max(0, event.getNewDamage() - damageBlocked));
    }
  }
}
