package me.alegian.thaumcraft7.block;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.ThaumcraftAspects;
import me.alegian.thaumcraft7.capability.ThaumcraftCapabilities;
import me.alegian.thaumcraft7.capability.ThaumometerScannable;
import me.alegian.thaumcraft7.capability.VisStorage;
import me.alegian.thaumcraft7.item.ItemIndex;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockIndex {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thaumcraft.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    public static final DeferredBlock<NodeBlock> NODE_BLOCK = BLOCKS.register("node",
            () -> new NodeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noCollission()));
    public static final DeferredItem<BlockItem> NODE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("node", NODE_BLOCK);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        ThaumcraftAspects.Contained[] contents = {new ThaumcraftAspects.Contained(ThaumcraftAspects.AER, 2), new ThaumcraftAspects.Contained(ThaumcraftAspects.POTENTIA, 4)};
        event.registerBlock(ThaumcraftCapabilities.ThaumometerScannable.BLOCK, (level, pos, state, be, context)->new ThaumometerScannable(Arrays.asList(contents)), NODE_BLOCK.get());
    }
}
