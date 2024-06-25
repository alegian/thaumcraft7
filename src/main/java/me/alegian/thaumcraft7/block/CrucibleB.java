package me.alegian.thaumcraft7.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CrucibleB extends AbstractCauldronBlock {
    public static final MapCodec<CrucibleB> CODEC = simpleCodec(CrucibleB::new);
    public static CauldronInteraction.InteractionMap EMPTY_CRUCIBLE = CauldronInteraction.newInteractionMap("empty_crucible");
    public static CauldronInteraction FILL_WATER = (blockState, level, blockPos, player, hand, itemStack) -> CauldronInteraction.emptyBucket(
            level,
            blockPos,
            player,
            hand,
            itemStack,
            TCBlocks.WATER_CRUCIBLE.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, Integer.valueOf(3)),
            SoundEvents.BUCKET_EMPTY
    );

    static{
        EMPTY_CRUCIBLE.map().put(Items.WATER_BUCKET, FILL_WATER);
    }

    public CrucibleB(Properties props) {
        super(props, EMPTY_CRUCIBLE);
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
