package me.alegian.thavma.impl.common.item;

import me.alegian.thavma.impl.client.gui.thaumonomicon.ThaumonomiconScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThaumonomiconItem extends Item {

  public ThaumonomiconItem(Properties props) {
    super(new Item.Properties().stacksTo(1));
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    if (level.isClientSide())
      Minecraft.getInstance().setScreen(new ThaumonomiconScreen());

    return InteractionResultHolder.consume(player.getItemInHand(hand));
  }
}
