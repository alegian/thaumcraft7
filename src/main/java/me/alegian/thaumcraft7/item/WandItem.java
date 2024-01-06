package me.alegian.thaumcraft7.item;

import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.capability.VisStorage;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class WandItem extends Item {
    public WandItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var cap = player.getUseItem().getCapability(VisStorage.ITEM);
        if(cap!=null){
            player.sendSystemMessage(Component.literal("VIS: " + cap.getMaxVisStored()));
        }
        if(level.isClientSide()) {
            player.sendSystemMessage(Component.literal("use"));
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.success(player.getItemInHand(hand));
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
            private static final HumanoidModel.ArmPose WAND_POSE =HumanoidModel.ArmPose.create("WAND", false, (model, entity, arm) -> {
                model.rightArm.xRot = (float) (-0.8 * Math.PI /2);
                model.leftArm.xRot = (float) (-0.8 * Math.PI /2);
                if (arm == HumanoidArm.RIGHT) {
                    model.leftArm.yRot = (float) (Math.PI /4);
                } else {
                    model.rightArm.yRot = (float) (Math.PI /4);
                }
            });

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return WAND_POSE;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                Matrix4f transformMatrix = new Matrix4f();
                boolean using = false;

                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    using = true;
                    float secondsUsing = (float) player.getTicksUsingItem() /20;

                    transformMatrix = transformMatrix
                        .translate(i * 0.56F, -0.52F, -0.72F)
                        .rotateX((float) (-1*Math.PI/4))
                        .rotateY((float) (secondsUsing*Math.PI))
                        .translate(0, -0.5F, 0)
                        .rotateX((float) (-1*Math.PI/16))
                        .translate(0, 0.5F, 0);
                }

                poseStack.mulPoseMatrix(transformMatrix);
                return using;
            }
        });
    }
}
