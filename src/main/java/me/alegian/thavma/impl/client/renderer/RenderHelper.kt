package me.alegian.thavma.impl.client.renderer

import net.minecraft.client.Minecraft
import net.minecraft.world.phys.Vec3
import kotlin.math.acos


/**
 * Angle between ClientPlayer and a Position, in Radians, relative to North.
 * Like a compass centered at the Position, that tracks the Player.
 */
fun calculatePlayerAngle(pos: Vec3): Float {
    val deltaTracker = Minecraft.getInstance().timer
    val player = Minecraft.getInstance().player ?: return 0f

    val playerPos = player.getPosition(deltaTracker.getGameTimeDeltaPartialTick(true))
    val diff = playerPos.vectorTo(pos)
    val horizontalDirection = (Vec3(diff.x, 0.0, diff.z)).normalize()
    var angle = acos(horizontalDirection.dot(Vec3(0.0, 0.0, -1.0)))
    if (horizontalDirection.x > 0) angle *= -1

    return angle.toFloat()
}
