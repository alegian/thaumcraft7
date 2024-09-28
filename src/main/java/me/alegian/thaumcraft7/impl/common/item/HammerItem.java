package me.alegian.thaumcraft7.impl.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.LevelAccessor;

import java.util.List;

/**
 * Mining hammer for 3x3 mining.
 * Has double durability of the corresponding pickaxe (via event),
 * but takes 9x more damage from mining (every block counts)
 */
public class HammerItem extends DiggerItem {
  public HammerItem(Tier tier, Item.Properties props) {
    super(tier, BlockTags.MINEABLE_WITH_PICKAXE, props);
  }

  /**
   * Used in event
   */
  public void tryBreak3x3exceptOrigin(ServerPlayer serverPlayer, BlockPos blockPos, LevelAccessor level, ItemStack itemStack) {
    // find the 2 axes perpendicular to the player's direction
    var playerAxis = serverPlayer.getNearestViewDirection().getAxis();
    var allAxes = List.of(Direction.Axis.X, Direction.Axis.Y, Direction.Axis.Z);
    var perpendicularAxes = allAxes.stream().filter(a -> a != playerAxis).toList();

    // 3x3 area, except original block, only for correct mining tool
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        var currPos = blockPos
            .relative(perpendicularAxes.get(0), i)
            .relative(perpendicularAxes.get(1), j);
        var currBlockState = level.getBlockState(currPos);

        if ((i != 0 || j != 0) && this.isCorrectToolForDrops(itemStack, currBlockState))
          serverPlayer.gameMode.destroyBlock(currPos);
      }
    }
  }
}
