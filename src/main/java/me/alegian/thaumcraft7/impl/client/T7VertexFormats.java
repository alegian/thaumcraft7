package me.alegian.thaumcraft7.impl.client;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public class T7VertexFormats {
  public static VertexFormatElement CENTER;

  static {
    RuntimeException exception = null;
    for (int i = 6; i < 32; i++) { // the first 5 ids are vanilla
      try {
        CENTER = VertexFormatElement.register(i, 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.GENERIC, 3);
        exception = null;
        break;
      } catch (IllegalArgumentException e) {
        exception = e;
      }
    }

    if (exception != null)
      throw new RuntimeException("Thaumcraft Exception: Failed to register RADIUS vertex format element", exception);
  }

  public static final VertexFormat AURA_NODE = VertexFormat.builder()
      .add("Position", VertexFormatElement.POSITION)
      .add("Color", VertexFormatElement.COLOR)
      .add("Center", CENTER)
      .build();
}
