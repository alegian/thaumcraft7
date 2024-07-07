package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class TCRenderTypes {
    public static final RenderType SIMPLE_TRIANGLE = RenderType.create(
            "translucent", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, true, true, simpleTriangleState(RenderStateShard.RENDERTYPE_TRANSLUCENT_SHADER)
    );

    private static RenderType.CompositeState simpleTriangleState(RenderStateShard.ShaderStateShard pState) {
        return RenderType.CompositeState.builder()
                .setLightmapState(RenderStateShard.LIGHTMAP)
                .setShaderState(pState)
                .setTextureState(BLOCK_SHEET_MIPPED)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setOutputState(TRANSLUCENT_TARGET)
                .createCompositeState(true);
    }
}
