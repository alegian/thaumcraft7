package me.alegian.thaumcraft7.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CrucibleB extends AbstractCauldronBlock {
    public static final MapCodec<CrucibleB> CODEC = simpleCodec(CrucibleB::new);

    public CrucibleB(Properties props) {
        super(props, CauldronInteraction.newInteractionMap("empty_crucible"));
    }

    public CrucibleB() {
        this(Properties.ofFullCopy(Blocks.CAULDRON));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState pState) {
        return false;
    }
}
