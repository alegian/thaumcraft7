package me.alegian.thavma.impl.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderHelper {
  /**
   * Angle between ClientPlayer and a Position, in Radians, relative to North.
   * Like a compass centered at the Position, that tracks the Player.
   */
  public static float calculatePlayerAngle(Vec3 pos) {
    var deltaTracker = Minecraft.getInstance().getTimer();
    var player = Minecraft.getInstance().player;
    if (player == null) return 0;

    Vec3 playerPos = player.getPosition(deltaTracker.getGameTimeDeltaPartialTick(true));
    Vec3 diff = playerPos.vectorTo(pos);
    Vec3 diffXZ = (new Vec3(diff.x, 0, diff.z)).normalize();
    double angle = Math.acos(diffXZ.dot(new Vec3(0, 0, -1)));
    if (diffXZ.x > 0) angle = angle * -1;

    return (float) angle;
  }
}
