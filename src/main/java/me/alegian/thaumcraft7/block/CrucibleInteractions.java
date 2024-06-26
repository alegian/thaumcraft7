package me.alegian.thaumcraft7.block;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public class CrucibleInteractions {
    public static CauldronInteraction.InteractionMap CRUCIBLE = CauldronInteraction.newInteractionMap("crucible");
    public static CauldronInteraction FILL_WATER = (blockState, level, blockPos, player, hand, itemStack) -> CauldronInteraction.emptyBucket(
            level,
            blockPos,
            player,
            hand,
            itemStack,
            TCBlocks.WATER_CRUCIBLE.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, LayeredCrucibleB.MAX_FILL_LEVEL),
            SoundEvents.BUCKET_EMPTY
    );

    static{
        CRUCIBLE.map().put(Items.WATER_BUCKET, FILL_WATER);
    }
}
