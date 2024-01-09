package me.alegian.thaumcraft7.block;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.aspects.Aspect;
import me.alegian.thaumcraft7.api.aspects.AspectList;
import me.alegian.thaumcraft7.api.capabilities.ThaumcraftCapabilities;
import me.alegian.thaumcraft7.capability.AspectContainer;
import me.alegian.thaumcraft7.item.ItemIndex;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockIndex {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thaumcraft.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    public static final DeferredBlock<NodeBlock> NODE_BLOCK = BLOCKS.register("node",
            () -> new NodeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noCollission()));
    public static final DeferredItem<BlockItem> NODE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("node", NODE_BLOCK);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        AspectList contents = new AspectList().add(Aspect.AER, 2).add(Aspect.POTENTIA, 4);
        event.registerBlock(ThaumcraftCapabilities.ThaumometerScannable.BLOCK, (level, pos, state, be, context)->new AspectContainer(contents), NODE_BLOCK.get());
    }
}
