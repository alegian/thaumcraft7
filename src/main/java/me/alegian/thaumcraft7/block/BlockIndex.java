package me.alegian.thaumcraft7.block;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.item.ItemIndex;
import me.alegian.thaumcraft7.item.WandItem;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockIndex {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thaumcraft.MODID);

    // Creates a new block with the id "examplemod:example_block"
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    public static final DeferredBlock<Block> AURA_NODE_BLOCK = BLOCKS.register("aura_node_block",
            () -> new AuraNodeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noCollission()));
    public static final DeferredItem<BlockItem> NODE_BLOCK_ITEM = ItemIndex.ITEMS.registerSimpleBlockItem("aura_node_block", AURA_NODE_BLOCK);

}
