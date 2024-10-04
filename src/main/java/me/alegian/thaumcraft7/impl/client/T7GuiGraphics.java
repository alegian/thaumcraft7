package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
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
      int color
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
        FastColor.ARGB32.alpha(color) / 255f
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
      float pAlpha
  ) {
    RenderSystem.setShaderTexture(0, pAtlasLocation);
    RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
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

  public void drawSeeThroughText(Font font, String text, float pX, float pY, int color, boolean shadow) {
    font.drawInBatch(
        text,
        pX,
        pY,
        color,
        shadow,
        this.pose().last().pose(),
        this.bufferSource(),
        Font.DisplayMode.SEE_THROUGH,
        0,
        15728880,
        font.isBidirectional()
    );
  }

  // assumes no z-index, no texture offset and no cropping
  public void blitSimple(ResourceLocation resourceLocation, int screenLeftX, int screenTopY, int drawWidth, int drawHeight) {
    this.blit(
        resourceLocation,
        screenLeftX,
        screenTopY,
        0,
        0,
        0,
        drawWidth,
        drawHeight,
        drawWidth,
        drawHeight
    );
  }

  public void enableCrop(int leftX, int topY) {
    int screenHeight = this.guiHeight();
    int screenWidth = this.guiWidth();

    this.enableScissor(leftX, topY, screenWidth - leftX, screenHeight - topY);
  }

  public void disableCrop() {
    this.disableScissor();
  }

  public void translateXY(float x, float y) {
    this.pose().mulPose(new Matrix4f().translate(x, y, 0));
  }

  public void rotateZ(float deg) {
    this.pose().mulPose(new Matrix4f().rotateZ((float) (deg / 180 * Math.PI)));
  }

  public void scaleXY(float scale) {
    this.pose().mulPose(new Matrix4f().scale(scale, scale, 1));
  }

  public void push() {
    this.pose().pushPose();
  }

  public void pop() {
    this.pose().popPose();
  }
}
