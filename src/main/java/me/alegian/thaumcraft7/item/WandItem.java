package me.alegian.thaumcraft7.item;

//import me.alegian.thaumcraft7.interfaces.PoseStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.minecraft.client.Timer;
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
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.function.Consumer;

public class WandItem extends Item {
    private Quaternionf quat;
    private float rotValue = 0;

    public WandItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) player.sendSystemMessage(Component.literal("use"));
        rotValue = 0;
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
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    Matrix4f mat4 = new Matrix4f()
                            .translate(new Vector3f(0.56f, -0.76f, -0.72F))
                            .rotateZ(Math.toRadians(60))
                            .rotateX(Math.toRadians(-45))
                            .rotateY(rotValue)
                            .translate(new Vector3f(0.15f, 0.15f, 0));

                    player.sendSystemMessage(Component.literal("mat: " + mat4.toString()));
                    poseStack.pushTransformation(new Transformation(mat4));

                    rotValue += 0.05f;
                    return true;
                }
                return false;
            }
        });
    }
}


