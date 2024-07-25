package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.init.registries.deferred.TCParticleTypes;
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
