package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.api.capability.VisStorageHelper;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeB;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WandI extends Item {
  public WandI(Properties props) {
    super(props);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AuraNodeB) {
      var player = context.getPlayer();
      if (player != null) {
        var stack = player.getItemInHand(context.getHand());
        var received = VisStorageHelper.receiveVis(stack, 5);

        if (received == 0f) return InteractionResult.PASS;
        else player.startUsingItem(context.getHand());
      }
    }
    return InteractionResult.PASS;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    if (level.isClientSide) {
      player.sendSystemMessage(Component.literal("VIS: " + VisStorageHelper.getVisStored(player.getItemInHand(hand))));
    }
    return InteractionResultHolder.consume(player.getItemInHand(hand));
  }

  @Override
  public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int someDuration) {
    if (level.isClientSide() && entity instanceof Player) entity.sendSystemMessage(Component.literal("release using"));
  }

  @Override
  public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
    return 72000;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack itemStack) {
    return UseAnim.CUSTOM;
  }
}
