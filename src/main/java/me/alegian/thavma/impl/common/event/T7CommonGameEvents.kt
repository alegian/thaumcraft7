package me.alegian.thavma.impl.common.event

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.enchantment.ShriekResistance
import me.alegian.thavma.impl.common.entity.EntityHelper
import me.alegian.thavma.impl.common.item.HammerItem
import me.alegian.thavma.impl.init.registries.T7AttributeModifiers
import me.alegian.thavma.impl.init.registries.deferred.T7Items
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.damagesource.DamageTypes
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeInstance
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent
import kotlin.math.max

@EventBusSubscriber(modid = Thavma.MODID, bus = EventBusSubscriber.Bus.GAME)
object T7CommonGameEvents {
    private var allowHammerBreakEvents = true

    @SubscribeEvent
    fun entityTickPre(event: EntityTickEvent.Pre) {
        val livingEntity = event.entity
        if (livingEntity !is LivingEntity) return

        val attribute: AttributeInstance =
            livingEntity.getAttribute(Attributes.STEP_HEIGHT)
                ?: return

        val hasStepHeightFromOtherModifier =
            attribute.value >= 1.0 && !attribute.hasModifier(T7AttributeModifiers.StepHeight.LOCATION)

        if (!EntityHelper.isEntityWearingBoots(livingEntity) || hasStepHeightFromOtherModifier || livingEntity.isCrouching()) attribute.removeModifier(
            T7AttributeModifiers.StepHeight.MODIFIER
        )
        else attribute.addOrUpdateTransientModifier(T7AttributeModifiers.StepHeight.MODIFIER)

    }

    @SubscribeEvent
    fun livingDamagePost(event: LivingDamageEvent.Post) {
        val itemStack = event.source.weaponItem ?: return
        if (itemStack.item != T7Items.ARCANUM_KATANA.get()) return

        val entity = event.entity
        if (entity.health <= 10) {
            entity.kill()
            val serverPlayer = event.source.entity
            if (serverPlayer !is ServerPlayer) return

            entity.level().playSound(
                null,
                serverPlayer.blockPosition(),
                SoundEvents.ENDER_DRAGON_GROWL,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
            )
        }
    }

    @SubscribeEvent
    fun breakBlock(event: BreakEvent) {
        val player = event.player
        val itemStack = player.mainHandItem
        val item = itemStack.item
        val level = event.level

        if (player is ServerPlayer && item is HammerItem) {
            // disallow nested hammer break events, to avoid infinite recursion
            if (!allowHammerBreakEvents) return
            allowHammerBreakEvents = false

            item.tryBreak3x3exceptOrigin(player, level, itemStack)

            allowHammerBreakEvents = true
        }
    }

    @SubscribeEvent
    fun mobEffectApplicable(event: Applicable) {
        if (event.effectInstance == null) return
        if (event.effectInstance!!.effect !== MobEffects.DARKNESS) return
        if (!EntityHelper.isEntityWearingAccessory(event.entity, T7Items.DAWN_CHARM.get())) return
        event.result = Applicable.Result.DO_NOT_APPLY
    }

    @SubscribeEvent
    fun preLivingDamage(event: LivingDamageEvent.Pre) {
        val serverLevel = event.entity.level()
        if (serverLevel !is ServerLevel) return

        val sonicType =
            serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).get(
                DamageTypes.SONIC_BOOM
            )
        if (event.source.type() != sonicType) return

        val damageBlocked = ShriekResistance.getDamageProtection(serverLevel, event.entity, event.source)
        if (damageBlocked <= 0.0f) return

        // ideally would want to apply a reduction here,
        // but warden bypasses all reductions...
        event.container.newDamage = max(0.0, (event.newDamage - damageBlocked).toDouble()).toFloat()

    }
}
