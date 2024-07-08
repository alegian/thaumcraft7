package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.renderer.RenderStateShard;

public class TCRenderStateShards {
    public static final RenderStateShard.TransparencyStateShard SIMPLE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            Thaumcraft.MODID +"_simple_transparency",
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
}
