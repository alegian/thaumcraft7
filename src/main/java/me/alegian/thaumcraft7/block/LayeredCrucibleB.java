package me.alegian.thaumcraft7.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class LayeredCrucibleB extends AbstractCauldronBlock {
    public static final MapCodec<LayeredCrucibleB> CODEC = simpleCodec(LayeredCrucibleB::new);
    public static final int MIN_FILL_LEVEL = 1;
    public static final int MAX_FILL_LEVEL = 3;
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
    private static final int BASE_CONTENT_HEIGHT = 6;
    private static final double HEIGHT_PER_LEVEL = 3.0;


    public LayeredCrucibleB(Properties props) {
        super(props, CrucibleInteractions.CRUCIBLE);
    }

    public LayeredCrucibleB() {
        this(Properties.ofFullCopy(Blocks.CAULDRON));
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide && pEntity instanceof ItemEntity && this.isEntityInsideContent(pState, pPos, pEntity)) {
            if (pEntity.mayInteract(pLevel, pPos)) {
                pEntity.kill();
                pLevel.playSound(pEntity, pPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1F, 1.0F);
                lowerFillLevel(pState, pLevel, pPos);
            }
        }
    }

    public static void lowerFillLevel(BlockState pState, Level pLevel, BlockPos pPos) {
        int newLevel = pState.getValue(LEVEL) - 1;
        boolean willEmpty = newLevel == MIN_FILL_LEVEL - 1;
        BlockState blockstate = willEmpty ? TCBlocks.CRUCIBLE.get().defaultBlockState() : pState.setValue(LEVEL, newLevel);
        pLevel.setBlockAndUpdate(pPos, blockstate);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(blockstate));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LEVEL);
    }

    @Override
    protected double getContentHeight(BlockState pState) {
        return (BASE_CONTENT_HEIGHT + (double)pState.getValue(LEVEL) * HEIGHT_PER_LEVEL) / 16.0;
    }

    @Override
    public boolean isFull(BlockState pState) {
        return pState.getValue(LEVEL) == MAX_FILL_LEVEL;
    }

    @Override
    public MapCodec<LayeredCrucibleB> codec() {
        return CODEC;
    }
}
