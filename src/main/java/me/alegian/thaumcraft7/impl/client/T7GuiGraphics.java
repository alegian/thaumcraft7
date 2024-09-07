package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;

public class T7GuiGraphics extends GuiGraphics {
  public T7GuiGraphics(Minecraft pMinecraft, PoseStack pPose, MultiBufferSource.BufferSource pBufferSource) {
    super(pMinecraft, pPose, pBufferSource);
  }

  public T7GuiGraphics(GuiGraphics guiGraphics) {
    super(Minecraft.getInstance(), guiGraphics.pose(), guiGraphics.bufferSource());
  }

  public void blit(
      int pX,
      int pY,
      int pBlitOffset,
      int pWidth,
      int pHeight,
      TextureAtlasSprite pSprite,
      int color,
      RenderStateShard.ShaderStateShard shaderStateShard
  ) {
    this.innerBlit(
        pSprite.atlasLocation(),
        pX,
        pX + pWidth,
        pY,
        pY + pHeight,
        pBlitOffset,
        pSprite.getU0(),
        pSprite.getU1(),
        pSprite.getV0(),
        pSprite.getV1(),
        FastColor.ARGB32.red(color) / 255f,
        FastColor.ARGB32.green(color) / 255f,
        FastColor.ARGB32.blue(color) / 255f,
        FastColor.ARGB32.alpha(color) / 255f,
        shaderStateShard
    );
  }

  void innerBlit(
      ResourceLocation pAtlasLocation,
      int pX1,
      int pX2,
      int pY1,
      int pY2,
      int pBlitOffset,
      float pMinU,
      float pMaxU,
      float pMinV,
      float pMaxV,
      float pRed,
      float pGreen,
      float pBlue,
      float pAlpha,
      RenderStateShard.ShaderStateShard shaderStateShard
  ) {
    RenderSystem.setShaderTexture(0, pAtlasLocation);
    shaderStateShard.setupRenderState();
    RenderSystem.enableBlend();
    Matrix4f matrix4f = this.pose().last().pose();
    BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
    bufferbuilder.addVertex(matrix4f, (float) pX1, (float) pY1, (float) pBlitOffset)
        .setUv(pMinU, pMinV)
        .setColor(pRed, pGreen, pBlue, pAlpha);
    bufferbuilder.addVertex(matrix4f, (float) pX1, (float) pY2, (float) pBlitOffset)
        .setUv(pMinU, pMaxV)
        .setColor(pRed, pGreen, pBlue, pAlpha);
    bufferbuilder.addVertex(matrix4f, (float) pX2, (float) pY2, (float) pBlitOffset)
        .setUv(pMaxU, pMaxV)
        .setColor(pRed, pGreen, pBlue, pAlpha);
    bufferbuilder.addVertex(matrix4f, (float) pX2, (float) pY1, (float) pBlitOffset)
        .setUv(pMaxU, pMinV)
        .setColor(pRed, pGreen, pBlue, pAlpha);
    BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    RenderSystem.disableBlend();
  }
}