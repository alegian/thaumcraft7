package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.api.capability.VisStorageHelper;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.entity.FancyThaumonomiconEntity;
import me.alegian.thaumcraft7.impl.common.entity.VisEntity;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;

public class WandItem extends Item {
  public WandItem(Properties props) {
    super(props);
  }

  /**
   * Use Wand on a Block. Has 3 main uses:<p>
   * 1. Receiving Vis from an Aura Node, by spawning a VisEntity<br>
   * 2. Turning Cauldrons into Crucibles<br>
   * 3. Creating Thaumonomicons from Bookcases
   */
  @Override
  public InteractionResult useOn(UseOnContext context) {
    var level = context.getLevel();
    var blockPos = context.getClickedPos();
    var block = level.getBlockState(blockPos).getBlock();

    if (block instanceof AuraNodeBlock) {
      var player = context.getPlayer();
      if (player != null) { // and wand is not full and aura node is not empty
        var stack = player.getItemInHand(context.getHand());
        var received = VisStorageHelper.receiveVis(stack, 5); //try receiving only on server

        if (received == 0f) return InteractionResult.PASS;
        player.startUsingItem(context.getHand());
        if (!level.isClientSide()) level.addFreshEntity(new VisEntity(level, player, blockPos));
        return InteractionResult.CONSUME;
      }
    }
    if (block instanceof AbstractCauldronBlock) {
      if (!level.isClientSide()) {
        level.setBlockAndUpdate(blockPos, T7Blocks.CRUCIBLE.get().defaultBlockState());
      }
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
    }
    if (block == Blocks.BOOKSHELF) {
      if (!level.isClientSide() && level.removeBlock(blockPos, false)) {
        level.addFreshEntity(new FancyThaumonomiconEntity(level, blockPos));
      }
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
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
