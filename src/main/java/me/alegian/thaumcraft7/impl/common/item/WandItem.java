package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.renderer.geo.WandRenderer;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.entity.FancyThaumonomiconEntity;
import me.alegian.thaumcraft7.impl.common.entity.VisEntity;
import me.alegian.thaumcraft7.impl.common.util.LevelHelper;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandHandleMaterial;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.GeckoLibServices;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WandItem extends Item implements GeoItem {
  private final RawAnimation CAST_ANIMATION = RawAnimation.begin().thenPlay("casting");
  private final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenPlay("idle");
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final DeferredWandHandleMaterial<WandHandleMaterial> handleMaterial;
  private final DeferredWandCoreMaterial<WandCoreMaterial> coreMaterial;

  public WandItem(Properties props, DeferredWandHandleMaterial<WandHandleMaterial> handleMaterial, DeferredWandCoreMaterial<WandCoreMaterial> coreMaterial) {
    super(props.stacksTo(1));
    this.handleMaterial = handleMaterial;
    this.coreMaterial = coreMaterial;
    GeckoLibUtil.SYNCED_ANIMATABLES.put(syncableId(), this);
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
      if (player != null) { // and wand is not full and aura node is not empty
        //try receiving only on server

        player.startUsingItem(context.getHand());
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
          level.addFreshEntity(new VisEntity(level, player, blockPos));
          animateCircle(true, player, context.getItemInHand(), serverLevel);
        }
        return InteractionResult.CONSUME;
      }
    }
    if (blockState.is(Blocks.CAULDRON)) {
      if (!level.isClientSide()) {
        level.setBlockAndUpdate(blockPos, T7Blocks.CRUCIBLE.get().defaultBlockState());
      }
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
    }
    if (blockState.is(Blocks.BOOKSHELF)) {
      if (!level.isClientSide() && level.removeBlock(blockPos, false)) {
        level.addFreshEntity(new FancyThaumonomiconEntity(level, blockPos));
      }
      level.playSound(context.getPlayer(), blockPos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1.0F, 1.0F);
      return InteractionResult.SUCCESS;
    }
    if (blockState.is(Blocks.GLASS)) {
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
  public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int someDuration) {
    animateCircle(false, entity, itemStack, level);
  }

  /**
   * Only does things on the server
   */
  protected void animateCircle(boolean isCasting, Entity entity, ItemStack itemStack, Level level) {
    if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
      animateCircle(isCasting, entity, itemStack, serverLevel);
    }
  }

  /**
   * Faster variant, if we already have the serverLevel
   */
  protected void animateCircle(boolean isCasting, Entity entity, ItemStack itemStack, ServerLevel level) {
    var animationId = isCasting ? "casting" : "idle";
    GeckoLibServices.NETWORK.triggerSingletonAnim(syncableId(), entity, GeoItem.getOrAssignId(itemStack, level), "Casting", animationId);
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
          if (controller.getCurrentAnimation() == null) controller.setAnimation(IDLE_ANIMATION);
          return PlayState.CONTINUE;
        })
            .triggerableAnim("casting", CAST_ANIMATION)
            .triggerableAnim("idle", IDLE_ANIMATION)
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
          this.renderer = new WandRenderer(handleMaterial, coreMaterial);

        return this.renderer;
      }
    });
  }

  public int capacity() {
    return coreMaterial.get().capacity();
  }

  public String getName() {
    return name(handleMaterial, coreMaterial);
  }

  public WandCoreMaterial coreMaterial() {
    return coreMaterial.get();
  }

  public WandHandleMaterial handleMaterial() {
    return handleMaterial.get();
  }

  public static String name(DeferredWandHandleMaterial<WandHandleMaterial> handleMaterial, DeferredWandCoreMaterial<WandCoreMaterial> coreMaterial) {
    String handleName = handleMaterial.getId().getPath();
    String coreName = coreMaterial.getId().getPath();
    return handleName + "_" + coreName + "_wand";
  }

  /**
   * Custom Animatable Syncable ID, for Gecko. By default, Item classes
   * in gecko are considered singletons, and therefore use classnames
   * as identifiers, but this doesn't work for Wands, as the same class
   * has multiple instances.
   */
  public String syncableId() {
    var location = Thaumcraft.id(getName());
    return location.toString();
  }
}
