package me.alegian.thaumcraft7.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AuraNodeBlockEntity extends BlockEntity {

    public AuraNodeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityIndex.AURA_NODE.get(), pos, blockState);
    }
}
