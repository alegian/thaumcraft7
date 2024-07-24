package me.alegian.thaumcraft7.data.gen;

import me.alegian.thaumcraft7.particle.TCParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class TCParticleDescriptionProvider extends ParticleDescriptionProvider {
  public TCParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper);
  }

  @Override
  protected void addDescriptions() {
    spriteSet(TCParticleTypes.CRUCIBLE_BUBBLE.get(), ResourceLocation.withDefaultNamespace("bubble"));
  }
}
