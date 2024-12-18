package me.alegian.thavma.impl.common.aspect;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import me.alegian.thavma.impl.init.registries.T7Registries;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Aspect {
  public static final StreamCodec<ByteBuf, Aspect> STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(
      s -> T7Registries.INSTANCE.getASPECT().get(ResourceLocation.parse(s)),
      a -> T7Registries.INSTANCE.getASPECT().getKey(a).toString()
  );
  public static final Codec<Aspect> CODEC = T7Registries.INSTANCE.getASPECT().byNameCodec();
  String id;
  int color;
  List<Supplier<Aspect>> components;

  public Aspect(String id, int color, List<Supplier<Aspect>> components) {
    this.id = id;
    this.color = color;
    this.components = components;
  }

  public static Aspect getRandomAspect() {
    var registeredAspects = Aspects.INSTANCE.getREGISTRAR().getEntries();
    return registeredAspects.stream().skip(new Random().nextInt(registeredAspects.size())).findFirst().get().get();
  }

  public boolean isPrimal() {
    return this.components == null;
  }

  public String getId() {
    return this.id;
  }

  public int getColor() {
    return this.color;
  }

  public int[] getColorRGB() {
    int r = (this.color & 0xFF0000) >> 16;
    int g = (this.color & 0xFF00) >> 8;
    int b = (this.color & 0xFF);

    return new int[]{r, g, b};
  }

  public List<Supplier<Aspect>> getComponents() {
    return this.components;
  }
}
