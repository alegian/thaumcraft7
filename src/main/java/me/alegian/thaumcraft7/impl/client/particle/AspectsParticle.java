package me.alegian.thaumcraft7.impl.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.impl.client.TCParticleRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class AspectsParticle extends TextureSheetParticle {
  public static boolean kill = false;
  public static BlockPos blockPos = null;
  public static AspectsParticle instance = null;

  private AspectsParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet) {
    super(pLevel, pX, pY, pZ);
    this.gravity = 0;
    this.lifetime = Integer.MAX_VALUE;
    this.quadSize = .6f;
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
  public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
    Quaternionf quaternionf = new Quaternionf();
    this.getFacingCameraMode().setRotation(quaternionf, pRenderInfo, pPartialTicks);
    if (this.roll != 0.0F) {
      quaternionf.rotateZ(Mth.lerp(pPartialTicks, this.oRoll, this.roll));
    }

    this.renderOffsetRotatedQuad(pBuffer, pRenderInfo, quaternionf, 0, 0);
    this.renderOffsetRotatedQuad(pBuffer, pRenderInfo, quaternionf, 1, 0);
  }

  public void renderOffsetRotatedQuad(VertexConsumer pBuffer, Camera pCamera, Quaternionf pQuaternion, float xOffset, float yOffset) {
    Vec3 vec3 = pCamera.getPosition();
    float f = (float)(this.x - vec3.x());
    float f1 = (float)(this.y - vec3.y());
    float f2 = (float)(this.z - vec3.z());
    this.renderOffsetRotatedQuad(pBuffer, pQuaternion, f, f1, f2, xOffset, yOffset);
  }

  public void renderOffsetRotatedQuad(VertexConsumer pBuffer, Quaternionf pQuaternion, float pX, float pY, float pZ, float xOffset, float yOffset) {
    float f = this.getQuadSize(1);
    float f1 = this.getU0();
    float f2 = this.getU1();
    float f3 = this.getV0();
    float f4 = this.getV1();
    this.renderVertex(pBuffer, pQuaternion, pX, pY, pZ, .5F + xOffset, -.5F + yOffset, f, f2, f4);
    this.renderVertex(pBuffer, pQuaternion, pX, pY, pZ, .5F + xOffset, .5F + yOffset, f, f2, f3);
    this.renderVertex(pBuffer, pQuaternion, pX, pY, pZ, -.5F + xOffset, .5F + yOffset, f, f1, f3);
    this.renderVertex(pBuffer, pQuaternion, pX, pY, pZ, -.5F + xOffset, -.5F + yOffset, f, f1, f4);
  }

  public void renderVertex(
      VertexConsumer pBuffer,
      Quaternionf pQuaternion,
      float pX,
      float pY,
      float pZ,
      float pXOffset,
      float pYOffset,
      float pQuadSize,
      float pU,
      float pV
  ) {
    int pPackedLight = this.getLightColor(1);
    Vector3f vector3f = new Vector3f(pXOffset, pYOffset, 0.0F).rotate(pQuaternion).mul(pQuadSize).add(pX, pY, pZ);
    pBuffer.addVertex(vector3f.x(), vector3f.y(), vector3f.z())
        .setUv(pU, pV)
        .setColor(this.rCol, this.gCol, this.bCol, this.alpha)
        .setLight(pPackedLight);
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
