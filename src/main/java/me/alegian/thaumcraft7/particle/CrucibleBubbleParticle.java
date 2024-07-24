package me.alegian.thaumcraft7.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrucibleBubbleParticle extends TextureSheetParticle {
  protected CrucibleBubbleParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
    super(pLevel, pX, pY, pZ);
    this.lifetime = (int) (Math.random() * 40 + 20);
    this.quadSize = .05f;
    this.gravity = 0;
    this.pickSprite(spriteSet);
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

  @Override
  public void remove() {
    super.remove();
    level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, 0, 0, 0);
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public Provider(SpriteSet pSprites) {
      this.sprite = pSprites;
    }

    public Particle createParticle(
        SimpleParticleType pType,
        ClientLevel pLevel,
        double pX,
        double pY,
        double pZ,
        double pXSpeed,
        double pYSpeed,
        double pZSpeed
    ) {
      return new CrucibleBubbleParticle(pLevel, pX, pY, pZ, sprite);
    }
  }
}
