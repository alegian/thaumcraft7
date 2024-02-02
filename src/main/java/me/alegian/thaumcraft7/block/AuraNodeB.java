package me.alegian.thaumcraft7.block;


import me.alegian.thaumcraft7.blockentity.AuraNodeBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AuraNodeB extends TransparentBlock implements EntityBlock {
    protected AuraNodeB(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new AuraNodeBE(pos, blockState);
    }
}
