package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.api.capability.AspectContainerHelper;
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

public class ThaumometerI extends Item {
  public ThaumometerI(Properties props) {
    super(props);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    player.startUsingItem(hand);
    return InteractionResultHolder.success(player.getItemInHand(hand));
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    var level = context.getLevel();
    var block = level.getBlockState(context.getClickedPos()).getBlock();
    if (block instanceof AuraNodeB) {
      var player = context.getPlayer();
      if (player != null) {
        if (level.isClientSide) {
          player.sendSystemMessage(Component.literal(AspectContainerHelper.getAspects(level, context.getClickedPos()).toString()));
        }
      }
    }
    return InteractionResult.PASS;
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
