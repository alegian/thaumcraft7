package me.alegian.thavma.impl.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.alegian.thavma.impl.Thavma;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

@OnlyIn(Dist.CLIENT)
public class T7RenderTypes {
  public static final RenderType AURA_NODE = RenderType.create(
      Thavma.MODID + "_aura_node", T7VertexFormats.AURA_NODE, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, false, true, T7RenderTypes.auraNodeState()
  );

  public static final RenderType TRANSLUCENT_TRIANGLES = RenderType.create(
      Thavma.MODID + "_vis",
      DefaultVertexFormat.POSITION_COLOR,
      VertexFormat.Mode.TRIANGLE_STRIP,
      1536,
      false,
      true,
      RenderType.CompositeState.builder()
          .setShaderState(POSITION_COLOR_SHADER)
          .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
          .setCullState(NO_CULL)
          .setWriteMaskState(COLOR_WRITE)
          .createCompositeState(false)
  );

  /**
   * named after the similar EYES vanilla RenderType, used for emissive entity eyes
   * used in Thavma for infusion green effects, because Gecko deletes the original pixels
   * so we have to write depth to avoid bugs
   */
  public static final Function<ResourceLocation, RenderType> EYES_WITH_DEPTH = Util.memoize(
      (p_311464_) -> {
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_311464_, false, false);
        return RenderType.create(
            "eyes",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_EYES_SHADER)
                .setTextureState(renderstateshard$texturestateshard)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .createCompositeState(false)
        );
      }
  );

  private static RenderType.CompositeState auraNodeState() {
    return RenderType.CompositeState.builder()
        .setShaderState(T7RenderStateShards.AURA_NODE_SHADER)
        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
        .setTextureState(NO_TEXTURE)
        .setDepthTestState(T7RenderStateShards.NOT_EQUAL_DEPTH_TEST) // alpha colors do not stack in aura node layers, and aura nodes can be seen through blocks
        .setOutputState(TRANSLUCENT_TARGET)
        .setCullState(NO_CULL)
        .createCompositeState(false);
  }
}
