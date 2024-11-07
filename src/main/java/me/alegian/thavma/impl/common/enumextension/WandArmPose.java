package me.alegian.thavma.impl.common.enumextension;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class WandArmPose {
  private static final boolean TWO_HANDED = false;

  private static final IArmPoseTransformer ARM_POSE_TRANSFORMER = (model, entity, arm) -> {
    model.rightArm.xRot = (float) (-0.8 * Math.PI / 2);
    model.leftArm.xRot = (float) (-0.8 * Math.PI / 2);
    if (arm == HumanoidArm.RIGHT) {
      model.leftArm.yRot = (float) (Math.PI / 4);
    } else {
      model.rightArm.yRot = (float) (Math.PI / 4);
    }
  };

  public static final EnumProxy<HumanoidModel.ArmPose> ENUM_PARAMS = new EnumProxy<>(
      HumanoidModel.ArmPose.class, TWO_HANDED, ARM_POSE_TRANSFORMER
  );

  public static HumanoidModel.ArmPose value() {
    return HumanoidModel.ArmPose.valueOf(Thavma.MODID + "_WAND");
  }
}
