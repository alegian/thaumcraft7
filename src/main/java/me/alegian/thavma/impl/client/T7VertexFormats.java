package me.alegian.thavma.impl.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class T7VertexFormats {
  public static VertexFormatElement CENTER;
  public static VertexFormatElement ANGLE;

  static {
    T7VertexFormats.CENTER = T7VertexFormats.registerFormatElement(VertexFormatElement.Type.FLOAT, 3);
    T7VertexFormats.ANGLE = T7VertexFormats.registerFormatElement(VertexFormatElement.Type.FLOAT, 1);
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

  public static final VertexFormat AURA_NODE = VertexFormat.builder()
      .add("Position", VertexFormatElement.POSITION)
      .add("Color", VertexFormatElement.COLOR)
      .add("Center", T7VertexFormats.CENTER)
      .add("Angle", T7VertexFormats.ANGLE)
      .build();
}
