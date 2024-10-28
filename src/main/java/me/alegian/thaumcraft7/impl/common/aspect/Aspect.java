package me.alegian.thaumcraft7.impl.common.aspect;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import me.alegian.thaumcraft7.impl.init.registries.T7Registries;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Aspect {
  String id;
  int color;
  List<Supplier<Aspect>> components;

  public Aspect(String id, int color, List<Supplier<Aspect>> components) {
    this.id = id;
    this.color = color;
    this.components = components;
  }

  public boolean isPrimal() {
    return components == null;
  }

  public String getId() {
    return id;
  }

  public int getColor() {
    return color;
  }

  public int[] getColorRGB() {
    int r = (color & 0xFF0000) >> 16;
    int g = (color & 0xFF00) >> 8;
    int b = (color & 0xFF);

    return new int[]{r, g, b};
  }

  public List<Supplier<Aspect>> getComponents() {
    return components;
  }

  public static Aspect getRandomAspect() {
    var registeredAspects = Aspects.REGISTRAR.getEntries();
    return registeredAspects.stream().skip(new Random().nextInt(registeredAspects.size())).findFirst().get().get();
  }

  public static final StreamCodec<ByteBuf, Aspect> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(
      s -> T7Registries.ASPECT.get(ResourceLocation.parse(s)),
      a -> T7Registries.ASPECT.getKey(a).toString()
  );

  public static final Codec<Aspect> CODEC = T7Registries.ASPECT.byNameCodec();
}
