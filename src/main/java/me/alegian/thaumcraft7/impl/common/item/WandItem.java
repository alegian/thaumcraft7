package me.alegian.thaumcraft7.impl.common.item;

import me.alegian.thaumcraft7.impl.client.renderer.geo.WandRenderer;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.entity.FancyThaumonomiconEntity;
import me.alegian.thaumcraft7.impl.common.entity.VisEntity;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WandItem extends Item implements GeoItem {
  private static final RawAnimation CAST_ANIMATION = RawAnimation.begin().thenPlay("casting");
  private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenPlay("idle");
  private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
  private final HandleMaterial handleMaterial;
  private final CoreMaterial coreMaterial;

  public WandItem(Properties props, HandleMaterial handleMaterial, CoreMaterial coreMaterial) {
    super(props);
    SingletonGeoAnimatable.registerSyncedAnimatable(this);
    this.handleMaterial = handleMaterial;
    this.coreMaterial = coreMaterial;
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
        //try receiving only on server

        player.startUsingItem(context.getHand());
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
          level.addFreshEntity(new VisEntity(level, player, blockPos));
          triggerAnim(player, GeoItem.getOrAssignId(context.getItemInHand(), serverLevel), "Casting", "casting");
        }
        return InteractionResult.CONSUME;
      }
    }
    if (block.equals(Blocks.CAULDRON)) {
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
  public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int someDuration) {
    if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
      triggerAnim(entity, GeoItem.getOrAssignId(itemStack, serverLevel), "Casting", "idle");
    }
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

  public enum HandleMaterial {
    IRON("iron"),
    GOLD("gold"),
    ORICHALCUM("orichalcum"),
    ARCANUM("arcanum");

    private final String id;

    HandleMaterial(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }

  public enum CoreMaterial {
    WOOD("wood"),
    GREATWOOD("greatwood"),
    SILVERWOOD("silverwood");

    private final String id;

    CoreMaterial(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }
  }
}
