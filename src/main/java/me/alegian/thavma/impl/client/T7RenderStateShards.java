package me.alegian.thavma.impl.client;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class T7RenderStateShards {
  public static final RenderStateShard.DepthTestStateShard NOT_EQUAL_DEPTH_TEST = new RenderStateShard.DepthTestStateShard("!=", GL11.GL_NOTEQUAL);
  public static ShaderInstance auraNodeShader;
  public static final RenderStateShard.ShaderStateShard AURA_NODE_SHADER = new RenderStateShard.ShaderStateShard(() -> T7RenderStateShards.auraNodeShader);
}
