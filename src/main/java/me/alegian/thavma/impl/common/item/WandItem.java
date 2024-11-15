package me.alegian.thavma.impl.common.item;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.renderer.geo.WandRenderer;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.entity.FancyThaumonomiconEntity;
import me.alegian.thavma.impl.common.entity.VisEntity;
import me.alegian.thavma.impl.common.util.LevelHelper;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import me.alegian.thavma.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.network.packet.SingletonAnimTriggerPacket;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WandItem extends Item implements GeoItem {
  private final RawAnimation CAST_ANIMATION = RawAnimation.begin().thenPlay("casting");
  private final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenPlay("idle");
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final WandHandleMaterial handleMaterial;
  private final WandCoreMaterial coreMaterial;

  public WandItem(Properties props, WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    super(props.stacksTo(1).rarity(Rarity.UNCOMMON));
    this.handleMaterial = handleMaterial;
    this.coreMaterial = coreMaterial;
    GeckoLibUtil.SYNCED_ANIMATABLES.put(this.syncableId(), this);
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
    var blockState = level.getBlockState(blockPos);

    if (blockState.is(T7Blocks.AURA_NODE.get())) {
      var player = context.getPlayer();

      var optionalPair = AspectContainer.blockSourceItemSink(level, blockPos, context.getItemInHand());
      boolean canTransfer = optionalPair.map(AspectContainer.Pair::canTransferPrimals).orElse(false);
      if (player != null && canTransfer) {
        player.startUsingItem(context.getHand());
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
          level.addFreshEntity(new VisEntity(level, player, blockPos));
          this.animateCircle(true, player, context.getItemInHand(), serverLevel);
        }
        return InteractionResult.CONSUME;
      }
    }
    if (blockState.is(Blocks.CAULDRON)) {
      if (!level.isClientSide()) level.setBlockAndUpdate(blockPos, T7Blocks.CRUCIBLE.get().defaultBlockState());
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
    }
    if (blockState.is(Blocks.BOOKSHELF)) {
      if (!level.isClientSide() && level.removeBlock(blockPos, false))
        level.addFreshEntity(new FancyThaumonomiconEntity(level, blockPos));
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
    }
    if (blockState.is(Tags.Blocks.GLASS_BLOCKS)) {
      var direction = context.getClickedFace().getOpposite();
      var behindPos = blockPos.relative(direction, 1);
      return LevelHelper.getSafeBE(level, behindPos, T7BlockEntities.AURA_NODE.get())
          .map(be -> {
            if (be.jarInteraction()) return InteractionResult.SUCCESS;
            return InteractionResult.FAIL;
          }).orElse(InteractionResult.PASS);
    }
    return InteractionResult.PASS;
  }

  @Override
  public void onStopUsing(ItemStack itemStack, LivingEntity entity, int count) {
    this.animateCircle(false, entity, itemStack, entity.level());
    super.onStopUsing(itemStack, entity, count);
  }

  /**
   * Only does things on the server
   */
  protected void animateCircle(boolean isCasting, Entity entity, ItemStack itemStack, Level level) {
    if (!level.isClientSide() && level instanceof ServerLevel serverLevel)
      this.animateCircle(isCasting, entity, itemStack, serverLevel);
  }

  /**
   * Faster variant, if we already have the serverLevel.
   * Sends a gecko internal packet with custom syncableId, to avoid non-singleton bugs
   */
  protected void animateCircle(boolean isCasting, Entity entity, ItemStack itemStack, ServerLevel level) {
    var animationName = isCasting ? "casting" : "idle";
    GeckoLibServices.NETWORK.sendToAllPlayersTrackingEntity(new SingletonAnimTriggerPacket(this.syncableId(), GeoItem.getOrAssignId(itemStack, level), "Casting", animationName), entity);
  }

  @Override
  public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
    return 72000;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack itemStack) {
    return UseAnim.CUSTOM;
  }

  /**
   * The normal implementation causes flickering in the wand animation
   * when aspects are synced from server. Therefore, we have to use a
   * less strict variant.
   */
  @Override
  public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    return !oldStack.getItem().equals(newStack.getItem());
  }

  @Override
  public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    controllers.add(new AnimationController<GeoAnimatable>(this, "Casting", 0, state -> {
          var controller = state.getController();
          if (controller.getCurrentAnimation() == null) controller.setAnimation(this.IDLE_ANIMATION);
          return PlayState.CONTINUE;
        })
            .triggerableAnim("casting", this.CAST_ANIMATION)
            .triggerableAnim("idle", this.IDLE_ANIMATION)
    );
  }

  @Override
  public AnimatableInstanceCache getAnimatableInstanceCache() {
    return this.cache;
  }

  @Override
  public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
    consumer.accept(new GeoRenderProvider() {
      private WandRenderer renderer;

      @Override
      public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
        if (this.renderer == null)
          this.renderer = new WandRenderer(WandItem.this.handleMaterial, WandItem.this.coreMaterial);

        return this.renderer;
      }
    });
  }

  public int capacity() {
    return this.coreMaterial.capacity;
  }

  public String getName() {
    return WandItem.name(this.handleMaterial, this.coreMaterial);
  }

  public static String name(WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    return handleMaterial.getRegisteredName() + "_" + coreMaterial.getRegisteredName() + "_wand";
  }

  /**
   * Custom Animatable Syncable ID, for Gecko. By default, Item classes
   * in gecko are considered singletons, and therefore use classnames
   * as identifiers, but this doesn't work for Wands, as the same class
   * has multiple instances.
   */
  public String syncableId() {
    var location = Thavma.id(this.getName());
    return location.toString();
  }
}
