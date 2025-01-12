package me.alegian.thavma.impl.common.item

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ResearchScrollItem : Item(Properties().stacksTo(1)){
    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val mc = Minecraft.getInstance()
        mc.player?.connection?.advancements?.run {
            mc.setScreen(AdvancementsScreen(this))
        }
        return super.use(level, player, usedHand)
    }
}