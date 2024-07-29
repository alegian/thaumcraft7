package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.RenderStateShard.*;

@OnlyIn(Dist.CLIENT)
public class T7RenderTypes {
  public static final RenderType SIMPLE_TRIANGLE = RenderType.create(
      Thaumcraft.MODID + "_simple_triangle", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, true, true, simpleTriangleState()
  );

  public static final RenderType ASPECT_QUAD = RenderType.create(
      Thaumcraft.MODID + "_aspect_quad", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, RenderType.SMALL_BUFFER_SIZE, true, true, aspectQuadState()
  );

  private static RenderType.CompositeState simpleTriangleState() {
    return RenderType.CompositeState.builder()
        .setShaderState(POSITION_COLOR_SHADER)
        .setTransparencyState(T7RenderStateShards.SIMPLE_TRANSPARENCY)
        .setDepthTestState(NO_DEPTH_TEST)
        .setTextureState(NO_TEXTURE)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(false);
  }

  private static RenderType.CompositeState aspectQuadState() {
    return RenderType.CompositeState.builder()
        .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
        .setTransparencyState(T7RenderStateShards.SIMPLE_TRANSPARENCY)
        .setDepthTestState(NO_DEPTH_TEST)
        .setTextureState(T7RenderStateShards.ASPECTS_TEXTURE)
        .setOutputState(TRANSLUCENT_TARGET)
        .createCompositeState(false);
  }
}
