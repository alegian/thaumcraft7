package me.alegian.thaumcraft7.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.RenderStateShard.*;

@OnlyIn(Dist.CLIENT)
public class TCRenderTypes {
    public static final RenderType SIMPLE_TRIANGLE = RenderType.create(
            Thaumcraft.MODID + "_simple_triangle", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, RenderType.SMALL_BUFFER_SIZE, true, true, simpleTriangleState()
    );

    private static RenderType.CompositeState simpleTriangleState() {
        return RenderType.CompositeState.builder()
                .setShaderState(POSITION_COLOR_SHADER)
                .setTransparencyState(TCRenderStateShards.SIMPLE_TRANSPARENCY)
                .setDepthTestState(NO_DEPTH_TEST)
                .createCompositeState(false);
    }
}
