package me.alegian.thaumcraft7.enumextension;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class WandArmPose {
    public static Object getParameters(int idx, Class<?> type) {
        return type.cast(switch (idx) {
            case 0 -> TWO_HANDED;
            case 1 -> ARM_POSE_TRANSFORMER;
            default -> throw new IllegalArgumentException("Unexpected parameter index: " + idx);
        });
    }

    private static final boolean TWO_HANDED = false;

    private static final IArmPoseTransformer ARM_POSE_TRANSFORMER = (model, entity, arm) -> {
        model.rightArm.xRot = (float) (-0.8 * Math.PI /2);
        model.leftArm.xRot = (float) (-0.8 * Math.PI /2);
        if (arm == HumanoidArm.RIGHT) {
            model.leftArm.yRot = (float) (Math.PI /4);
        } else {
            model.rightArm.yRot = (float) (Math.PI /4);
        }
    };

    public static HumanoidModel.ArmPose value(){
        return HumanoidModel.ArmPose.valueOf(Thaumcraft.MODID + "_WAND");
    }
}
