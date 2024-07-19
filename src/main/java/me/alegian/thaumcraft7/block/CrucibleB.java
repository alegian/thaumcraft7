package me.alegian.thaumcraft7.block;

import com.mojang.serialization.MapCodec;
import me.alegian.thaumcraft7.blockentity.CrucibleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrucibleB extends AbstractCauldronBlock implements EntityBlock {
    public static final MapCodec<CrucibleB> CODEC = simpleCodec(CrucibleB::new);

    public CrucibleB(Properties props) {
        super(props, CrucibleInteractions.CRUCIBLE);
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrucibleBE(pPos, pState);
    }
}
