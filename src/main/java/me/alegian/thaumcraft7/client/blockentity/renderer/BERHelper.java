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

        Tesselator t = Tesselator.getInstance();
        BufferBuilder builder = t.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        var andleDelta = 2 * Math.PI / triangleResolution;
        for (int i = 0; i < triangleResolution; i++) {
            builder.addVertex(poseStack.last(), (float) Math.cos(andleDelta * i)*radius, (float) Math.sin(andleDelta * i)*radius, 0).setColor(r, g, b, 0.2f);
            builder.addVertex(poseStack.last(), 0, 0, 0).setColor(r, g, b, 0.2f);
            builder.addVertex(poseStack.last(), (float) Math.cos(andleDelta * (i + 1))*radius, (float) Math.sin(andleDelta * (i + 1))*radius, 0).setColor(r, g, b, 0.2f);
        }

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }
}
