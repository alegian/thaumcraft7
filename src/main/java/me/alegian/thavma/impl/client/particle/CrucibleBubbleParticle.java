package me.alegian.thavma.impl.client.particle;

import me.alegian.thavma.impl.common.block.entity.CrucibleBE;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrucibleBubbleParticle extends TextureSheetParticle {
  private CrucibleBE crucibleBE;
  private boolean toRemove = false;

  protected CrucibleBubbleParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
    super(pLevel, pX, pY, pZ);
    this.lifetime = (int) (Math.random() * 40 + 20);
    this.quadSize = .05f;
    this.gravity = 0;
    this.pickSprite(spriteSet);
  }

  @Override
  public void tick() {
    if (toRemove) remove();
    super.tick();
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

  @Override
  public void remove() {
    super.remove();
    if (crucibleBE != null) crucibleBE.removeParticle(this);
    level.addParticle(ParticleTypes.BUBBLE_POP, this.x, this.y, this.z, 0, 0, 0);
  }

  public void scheduleRemove() {
    this.toRemove = true;
  }

  public void setBlockEntity(CrucibleBE be) {
    this.crucibleBE = be;
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
      var particle = new CrucibleBubbleParticle(pLevel, pX, pY, pZ, sprite);
      var be = pLevel.getBlockEntity(new BlockPos((int) Math.floor(pX), (int) Math.floor(pY), (int) Math.floor(pZ)));
      if (be instanceof CrucibleBE crucibleBE) {
        crucibleBE.addParticle(particle);
        particle.setBlockEntity(crucibleBE);
      }

      return particle;
    }
  }
}
