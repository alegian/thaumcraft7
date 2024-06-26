package me.alegian.thaumcraft7.block;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.TCCapabilities;
import me.alegian.thaumcraft7.data.capability.AspectContainer;
import me.alegian.thaumcraft7.item.TCItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCBlocks {
    public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Thaumcraft.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = REGISTRAR.registerSimpleBlock("example_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE));
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = TCItems.REGISTRAR.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);
    public static final DeferredBlock<AuraNodeB> AURA_NODE_BLOCK = REGISTRAR.register("aura_node", AuraNodeB::new);
    public static final DeferredItem<BlockItem> AURA_NODE_BLOCK_ITEM = TCItems.REGISTRAR.registerSimpleBlockItem("aura_node", AURA_NODE_BLOCK);
    public static final DeferredBlock<CrucibleB> CRUCIBLE = REGISTRAR.register("crucible", ()->new CrucibleB());
    public static final DeferredBlock<LayeredCrucibleB> WATER_CRUCIBLE = REGISTRAR.register("water_crucible", ()->new LayeredCrucibleB());
    public static final DeferredItem<BlockItem> CRUCIBLE_ITEM = TCItems.REGISTRAR.registerSimpleBlockItem("crucible", CRUCIBLE);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        AspectList contents = new AspectList().add(Aspect.AER, 2).add(Aspect.POTENTIA, 4);
        event.registerBlock(TCCapabilities.AspectContainer.BLOCK, (level, pos, state, be, context)->new AspectContainer(contents), AURA_NODE_BLOCK.get());
    }
}
