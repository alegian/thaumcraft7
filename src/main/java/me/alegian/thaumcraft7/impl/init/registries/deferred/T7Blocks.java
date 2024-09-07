package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeBlock;
import me.alegian.thaumcraft7.impl.common.block.CrucibleBlock;
import me.alegian.thaumcraft7.impl.common.block.InfusedStoneBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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

  public static final DeferredBlock<LeavesBlock> GREATWOOD_LEAVES = REGISTRAR.register("greatwood_leaves", T7Blocks::leaves);
  public static final DeferredItem<BlockItem> GREATWOOD_LEAVES_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("greatwood_leaves", GREATWOOD_LEAVES);
  public static final DeferredBlock<RotatedPillarBlock> GREATWOOD_LOG = REGISTRAR.register("greatwood_log", () -> log(MapColor.WOOD, MapColor.PODZOL));
  public static final DeferredItem<BlockItem> GREATWOOD_LOG_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("greatwood_log", GREATWOOD_LOG);
  public static final DeferredBlock<Block> GREATWOOD_PLANKS = REGISTRAR.register("greatwood_planks", T7Blocks::plank);
  public static final DeferredItem<BlockItem> GREATWOOD_PLANKS_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("greatwood_planks", GREATWOOD_PLANKS);
  public static final DeferredBlock<SaplingBlock> GREATWOOD_SAPLING = REGISTRAR.register("greatwood_sapling", T7Blocks::sapling);
  public static final DeferredItem<BlockItem> GREATWOOD_SAPLING_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("greatwood_sapling", GREATWOOD_SAPLING);

  public static final DeferredBlock<Block> ARCANUM_BLOCK = REGISTRAR.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
  public static final DeferredItem<BlockItem> ARCANUM_BLOCK_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("arcanum_block", ARCANUM_BLOCK);
  public static final DeferredBlock<Block> ORICHALCUM_BLOCK = REGISTRAR.register("orichalcum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
  public static final DeferredItem<BlockItem> ORICHALCUM_BLOCK_ITEM = T7Items.REGISTRAR.registerSimpleBlockItem("orichalcum_block", ORICHALCUM_BLOCK);

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
  }

  private static LeavesBlock leaves() {
    return new LeavesBlock(
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot)
            .isSuffocating((a, b, c) -> false)
            .isViewBlocking((a, b, c) -> false)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor((a, b, c) -> false)
    );
  }

  private static RotatedPillarBlock log(MapColor pTopMapColor, MapColor pSideMapColor) {
    return new RotatedPillarBlock(
        BlockBehaviour.Properties.of()
            .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    );
  }

  private static Block plank() {
    return new Block(
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()
    );
  }

  private static SaplingBlock sapling() {
    return new SaplingBlock(
        TreeGrower.OAK,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.DESTROY)
    );
  }
}
