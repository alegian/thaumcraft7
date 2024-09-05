package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.texture.atlas.AspectAtlas;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class T7RenderStateShards {
  public static ShaderInstance customShader;
  public static ShaderInstance aspectOutline;

  public static final RenderStateShard.TransparencyStateShard SIMPLE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
      Thaumcraft.MODID + "_simple_transparency",
      () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
        );
      },
      () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
      }
  );

  public static final RenderStateShard.TextureStateShard ASPECTS_TEXTURE = new RenderStateShard.TextureStateShard(
      AspectAtlas.LOCATION, false, true
  );

  public static final RenderStateShard.ShaderStateShard CUSTOM_SHADER = new RenderStateShard.ShaderStateShard(() -> customShader);
  public static final RenderStateShard.ShaderStateShard ASPECT_OUTLINE = new RenderStateShard.ShaderStateShard(() -> aspectOutline);
}
