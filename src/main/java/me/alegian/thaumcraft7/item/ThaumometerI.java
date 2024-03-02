package me.alegian.thaumcraft7.item;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.api.capability.AspectContainerHelper;
import me.alegian.thaumcraft7.block.AuraNodeB;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;

import java.util.function.Consumer;

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
        if(block instanceof AuraNodeB){
            var player = context.getPlayer();
            if(player != null){
                if(level.isClientSide){
                    player.sendSystemMessage(Component.literal(AspectContainerHelper.getAspects(level, context.getClickedPos()).toString()));
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int someDuration) {
        if(level.isClientSide() && entity instanceof Player) entity.sendSystemMessage(Component.literal("release using"));
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private static final HumanoidModel.ArmPose THAUMOMETER_POSE = HumanoidModel.ArmPose.create("THAUMOMETER", true, (model, entity, arm) -> {
                model.rightArm.xRot = (float) (-0.8 * Math.PI /2);
                model.leftArm.xRot = (float) (-0.8 * Math.PI /2);
                model.leftArm.yRot = (float) (Math.PI /8);
                model.rightArm.yRot = (float) (-1*Math.PI /8);
            });

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return THAUMOMETER_POSE;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                // pretty much nullifies the default right click bob
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                    var transformMatrix = new Matrix4f().translate(i * 0.56F, -0.53F, -0.72F);
                    poseStack.mulPoseMatrix(transformMatrix);
                    return true;
                }
                return false;
            }
        });
    }
}
