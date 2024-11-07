package me.alegian.thavma.impl.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class T7VertexFormats {
  public static VertexFormatElement CENTER;

  static {
    RuntimeException exception = null;
    // the first 5 ids are vanilla
    for (int i = 6; i < 32; i++)
      try {
        T7VertexFormats.CENTER = VertexFormatElement.register(i, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 3);
        exception = null;
        break;
      } catch (IllegalArgumentException e) {
        exception = e;
      }

    if (exception != null)
      throw new RuntimeException("Thavma Exception: Failed to register RADIUS vertex format element", exception);
  }

  public static final VertexFormat AURA_NODE = VertexFormat.builder()
      .add("Position", VertexFormatElement.POSITION)
      .add("Color", VertexFormatElement.COLOR)
      .add("Center", T7VertexFormats.CENTER)
      .build();
}
