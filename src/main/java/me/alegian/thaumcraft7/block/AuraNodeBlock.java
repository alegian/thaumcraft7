package me.alegian.thaumcraft7.block;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class AuraNodeBlock extends Block {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 8, 8,8);

    protected AuraNodeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level level, BlockPos p_60505_, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if(level.isClientSide() && player instanceof Player) player.sendSystemMessage(Component.literal("use block"));

        return super.use(p_60503_, level, p_60505_, player, p_60507_, p_60508_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter plevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
////        return
//    }
}
