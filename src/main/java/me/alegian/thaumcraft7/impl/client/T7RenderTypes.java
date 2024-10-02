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
  public static final RenderType AURA_NODE = RenderType.create(
      Thaumcraft.MODID + "_simple_triangle", T7VertexFormats.AURA_NODE, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, false, true, auraNodeState()
  );

  public static final RenderType TRANSLUCENT_TRIANGLES = RenderType.create(
      Thaumcraft.MODID + "_vis",
      DefaultVertexFormat.POSITION_COLOR,
      VertexFormat.Mode.TRIANGLE_STRIP,
      1536,
      false,
      true,
      RenderType.CompositeState.builder()
          .setShaderState(POSITION_COLOR_SHADER)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setCullState(NO_CULL)
          .createCompositeState(false)
  );

  private static RenderType.CompositeState auraNodeState() {
    return RenderType.CompositeState.builder()
        .setShaderState(T7RenderStateShards.AURA_NODE_SHADER)
        .setTransparencyState(T7RenderStateShards.SIMPLE_TRANSPARENCY)
        .setDepthTestState(NO_DEPTH_TEST)
        .setTextureState(NO_TEXTURE)
        .setOutputState(TRANSLUCENT_TARGET)
        .setCullState(NO_CULL)
        .createCompositeState(false);
  }
}
