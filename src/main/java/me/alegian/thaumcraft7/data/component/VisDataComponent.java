package me.alegian.thaumcraft7.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class VisDataComponent {
    public record Vis(int value1, boolean value2) {}

    public static final Codec<Vis> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("value1").forGetter(Vis::value1),
                    Codec.BOOL.fieldOf("value2").forGetter(Vis::value2)
            ).apply(instance, Vis::new)
    );

    public static final StreamCodec<ByteBuf, Vis> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, Vis::value1,
            ByteBufCodecs.BOOL, Vis::value2,
            Vis::new
    );
}
