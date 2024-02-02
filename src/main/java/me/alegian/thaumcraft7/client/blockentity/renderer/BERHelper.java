package me.alegian.thaumcraft7.client.blockentity.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class BERHelper {
    public static void renderAuraNodeLayer(PoseStack poseStack, float radius, int triangleResolution, float r, float g, float b) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // what type of data our vertices contain
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        // origin
        Matrix4f matrix = poseStack.last().pose();

        Tesselator t = Tesselator.getInstance();
        BufferBuilder builder = t.getBuilder();
        builder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        var andleDelta = 2 * Math.PI / triangleResolution;
        for (int i = 0; i < triangleResolution; i++) {
            builder.vertex(matrix, (float) Math.cos(andleDelta * i)*radius, (float) Math.sin(andleDelta * i)*radius, 0).color(r, g, b, 0.2f).endVertex();
            builder.vertex(matrix, 0, 0, 0).color(r, g, b, 0.2f).endVertex();
            builder.vertex(matrix, (float) Math.cos(andleDelta * (i + 1))*radius, (float) Math.sin(andleDelta * (i + 1))*radius, 0).color(r, g, b, 0.2f).endVertex();
        }
        t.end();
    }
}
