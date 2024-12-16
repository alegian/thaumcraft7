package me.alegian.thavma.impl.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class T7VertexFormats {
  public static VertexFormatElement CENTER;
  public static VertexFormatElement SCALE;
  public static VertexFormat AURA_NODE;

  static {
    T7VertexFormats.CENTER = T7VertexFormats.registerFormatElement(VertexFormatElement.Type.FLOAT, 3);
    T7VertexFormats.SCALE = T7VertexFormats.registerFormatElement(VertexFormatElement.Type.FLOAT, 1);
    T7VertexFormats.AURA_NODE = VertexFormat.builder()
        .add("Position", VertexFormatElement.POSITION)
        .add("Color", VertexFormatElement.COLOR)
        .add("Center", T7VertexFormats.CENTER)
        .add("Scale", T7VertexFormats.SCALE)
        .build();
  }

  private static VertexFormatElement registerFormatElement(VertexFormatElement.Type type, int count) {
    // the first 5 ids are vanilla
    for (int i = 6; i < 32; i++)
      try {
        return VertexFormatElement.register(i, 0, type, VertexFormatElement.Usage.GENERIC, count);
      } catch (IllegalArgumentException ignored) {
      }

    throw new RuntimeException("Thavma Exception: Failed to register vertex format element");
  }
}
