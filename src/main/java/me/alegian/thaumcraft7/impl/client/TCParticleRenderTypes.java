package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.alegian.thaumcraft7.impl.client.texture.TCTextureAtlases;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureManager;

public class TCParticleRenderTypes {
  public static final ParticleRenderType PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH = new ParticleRenderType() {
    @Override
    public BufferBuilder begin(Tesselator tesselator, TextureManager textureManager) {
      RenderSystem.setShaderTexture(0, TCTextureAtlases.ASPECT);
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(
          GlStateManager.SourceFactor.SRC_ALPHA,
          GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
          GlStateManager.SourceFactor.ONE,
          GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
      );
      RenderSystem.disableDepthTest();
      return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public String toString() {
      return "PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH";
    }
  };
}
