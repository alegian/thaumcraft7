package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.block.CrucibleBlock;
import me.alegian.thaumcraft7.impl.common.block.InfusedStoneBlock;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Blocks {
  public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Thaumcraft.MODID);

  public static final DeferredBlock<AuraNodeBlock> AURA_NODE = REGISTRAR.register("aura_node", AuraNodeBlock::new);
  public static final DeferredItem<BlockItem> AURA_NODE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("aura_node", AURA_NODE);
  public static final DeferredBlock<CrucibleBlock> CRUCIBLE = REGISTRAR.register("crucible", CrucibleBlock::new);
  public static final DeferredItem<BlockItem> CRUCIBLE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("crucible", CRUCIBLE);
  public static final DeferredBlock<InfusedStoneBlock> IGNIS_INFUSED_STONE = REGISTRAR.register("ignis_ore", $ -> new InfusedStoneBlock(Aspect.IGNIS));
  public static final DeferredItem<BlockItem> IGNIS_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("ignis_ore", IGNIS_INFUSED_STONE);
  public static final DeferredBlock<InfusedStoneBlock> AER_INFUSED_STONE = REGISTRAR.register("aer_ore", $ -> new InfusedStoneBlock(Aspect.AER));
  public static final DeferredItem<BlockItem> AER_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("aer_ore", AER_INFUSED_STONE);
  public static final DeferredBlock<InfusedStoneBlock> TERRA_INFUSED_STONE = REGISTRAR.register("terra_ore", $ -> new InfusedStoneBlock(Aspect.TERRA));
  public static final DeferredItem<BlockItem> TERRA_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("terra_ore", TERRA_INFUSED_STONE);
  public static final DeferredBlock<InfusedStoneBlock> AQUA_INFUSED_STONE = REGISTRAR.register("aqua_ore", $ -> new InfusedStoneBlock(Aspect.AQUA));
  public static final DeferredItem<BlockItem> AQUA_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("aqua_ore", AQUA_INFUSED_STONE);
  public static final DeferredBlock<InfusedStoneBlock> ORDO_INFUSED_STONE = REGISTRAR.register("ordo_ore", $ -> new InfusedStoneBlock(Aspect.ORDO));
  public static final DeferredItem<BlockItem> ORDO_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("ordo_ore", ORDO_INFUSED_STONE);
  public static final DeferredBlock<InfusedStoneBlock> PERDITIO_INFUSED_STONE = REGISTRAR.register("perditio_ore", $ -> new InfusedStoneBlock(Aspect.PERDITIO));
  public static final DeferredItem<BlockItem> PERDITIO_INFUSED_STONE_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("perditio_ore", PERDITIO_INFUSED_STONE);

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
  }
}
