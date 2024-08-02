package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.block.CrucibleBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Blocks {
  public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Thaumcraft.MODID);

  public static final DeferredBlock<AuraNodeBlock> AURA_NODE_BLOCK = REGISTRAR.register("aura_node", AuraNodeBlock::new);
  public static final DeferredItem<BlockItem> AURA_NODE_BLOCK_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("aura_node", AURA_NODE_BLOCK);
  public static final DeferredBlock<Block> CRYSTAL_ORE = REGISTRAR.register("crystal_ore", $ -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)));
  public static final DeferredItem<BlockItem> CRYSTAL_ORE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("crystal_ore", CRYSTAL_ORE);
  public static final DeferredBlock<CrucibleBlock> CRUCIBLE = REGISTRAR.register("crucible", CrucibleBlock::new);
  public static final DeferredItem<BlockItem> CRUCIBLE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("crucible", CRUCIBLE);

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
  }
}
