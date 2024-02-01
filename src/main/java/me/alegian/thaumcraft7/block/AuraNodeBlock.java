package me.alegian.thaumcraft7.block;


import me.alegian.thaumcraft7.blockentity.AuraNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AuraNodeBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16,16);

    protected AuraNodeBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter plevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
        return new AuraNodeBlockEntity(pos, blockState);
    }
}
