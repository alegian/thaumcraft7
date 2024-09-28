package me.alegian.thaumcraft7.impl.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * Mining hammer for 3x3 mining.
 * Has double durability of the corresponding pickaxe (via event),
 * but takes 9x more damage from mining (every block counts)
 */
public class HammerItem extends DiggerItem {
  public HammerItem(Tier tier, Item.Properties props) {
    super(tier, BlockTags.MINEABLE_WITH_PICKAXE, props);
  }
}
