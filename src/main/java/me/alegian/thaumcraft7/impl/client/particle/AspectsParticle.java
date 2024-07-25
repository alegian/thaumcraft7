package me.alegian.thaumcraft7.impl.client.particle;

import me.alegian.thaumcraft7.impl.client.TCParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AspectsParticle extends TextureSheetParticle {
  public static boolean kill = false;
  public static BlockPos blockPos = null;
  public static AspectsParticle instance = null;

  private AspectsParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
    super(pLevel, pX, pY, pZ);
    this.gravity = 0;
    this.lifetime = Integer.MAX_VALUE;
    this.quadSize = .4f;
    this.pickSprite(spriteSet);
  }

  public static AspectsParticle getInstance(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
    if (instance == null) {
      instance = new AspectsParticle(pLevel, pX, pY, pZ, spriteSet);
    } else {
      instance.x = pX;
      instance.y = pY;
      instance.z = pZ;
    }
    return instance;
  }

  @Override
  public void tick() {
    super.tick();
    if (kill) {
      this.remove();
      instance = null;
    }
  }

  @Override
  public ParticleRenderType getRenderType() {
    return TCParticleRenderTypes.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
  }

  @Override
  protected int getLightColor(float pPartialTick) {
    return 0b111100000000000011110000; // completely bright
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet spriteSet;

    public Provider(SpriteSet spriteSet) {
      this.spriteSet = spriteSet;
    }

    @Override
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
      return getInstance(pLevel, pX, pY, pZ, spriteSet);
    }
  }
}
