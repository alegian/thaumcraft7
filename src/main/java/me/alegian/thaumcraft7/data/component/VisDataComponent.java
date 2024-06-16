package me.alegian.thaumcraft7.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class VisDataComponent {
    public record Vis(float vis) {

        public Vis(){
            this(0);
        }
    }

    public static final Codec<Vis> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("vis").forGetter(Vis::vis)
            ).apply(instance, Vis::new)
    );

    public static final StreamCodec<ByteBuf, Vis> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            Vis::vis,
            Vis::new
    );
}
