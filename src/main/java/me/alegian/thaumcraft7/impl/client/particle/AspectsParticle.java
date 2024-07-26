package me.alegian.thaumcraft7.impl.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
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
  public static final int ROW_SIZE = 5;
  public static boolean kill = false;
  public static BlockPos blockPos = null;
  public static AspectsParticle instance = null;
  public static AspectList aspects = new AspectList();

  static {
    aspects.add(Aspect.IGNIS, 4);
    aspects.add(Aspect.AER, 1);
    aspects.add(Aspect.MORTUUS, 2);
    aspects.add(Aspect.METALLUM, 4);
    aspects.add(Aspect.LUX, 7);
    aspects.add(Aspect.VINCULUM, 2);
  }

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
  public void render(VertexConsumer pBuffer, Camera camera, float pPartialTicks) {
    Quaternionf quaternionf = new Quaternionf();
    this.getFacingCameraMode().setRotation(quaternionf, camera, pPartialTicks);
    if (this.roll != 0.0F) {
      quaternionf.rotateZ(Mth.lerp(pPartialTicks, this.oRoll, this.roll));
    }

    float[][] offsets = calculateOffsets();
    int i = 0;
    for(Aspect aspect : aspects.aspects.keySet()){
      var currOffsets = offsets[i];
      this.renderOffsetRotatedQuad(pBuffer, camera, quaternionf, currOffsets[0], currOffsets[1]);
      i++;
    }
  }

  public float[][] calculateOffsets(){
    float[][] offsets = new float[aspects.size()][2];

    int rows = (int) Math.ceil(1f * aspects.size() / ROW_SIZE);
    for (int i = 0; i < rows; i++) {
      int cols = Math.min(aspects.size() - (i * ROW_SIZE), ROW_SIZE);
      for (int j = 0; j < cols; j++) {
        float xOffset = (cols - 1) / -2f + j;
        offsets[i * ROW_SIZE + j][0] = xOffset;
        offsets[i * ROW_SIZE + j][1] = i;
      }
    }

    return offsets;
  }

  public void renderOffsetRotatedQuad(VertexConsumer pBuffer, Camera pCamera, Quaternionf pQuaternion, float xOffset, float yOffset) {
    Vec3 vec3 = pCamera.getPosition();
    float x = (float) (this.x - vec3.x());
    float y = (float) (this.y - vec3.y());
    float z = (float) (this.z - vec3.z());
    float f1 = this.getU0();
    float f2 = this.getU1();
    float f3 = this.getV0();
    float f4 = this.getV1();

    this.renderVertex(pBuffer, pQuaternion, x, y, z, .5F + xOffset, -.5F + yOffset, f2, f4);
    this.renderVertex(pBuffer, pQuaternion, x, y, z, .5F + xOffset, .5F + yOffset, f2, f3);
    this.renderVertex(pBuffer, pQuaternion, x, y, z, -.5F + xOffset, .5F + yOffset, f1, f3);
    this.renderVertex(pBuffer, pQuaternion, x, y, z, -.5F + xOffset, -.5F + yOffset, f1, f4);
  }

  public void renderVertex(
      VertexConsumer pBuffer,
      Quaternionf pQuaternion,
      float pX,
      float pY,
      float pZ,
      float pXOffset,
      float pYOffset,
      float pU,
      float pV
  ) {
    int pPackedLight = this.getLightColor(1);
    float quadSize = this.getQuadSize(1);

    Vector3f vector3f = new Vector3f(pXOffset, pYOffset, 0).rotate(pQuaternion).mul(quadSize).add(pX, pY, pZ);
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
