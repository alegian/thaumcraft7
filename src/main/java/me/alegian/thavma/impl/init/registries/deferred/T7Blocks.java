package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.*;
import me.alegian.thavma.impl.init.data.worldgen.tree.GreatwoodTree;
import me.alegian.thavma.impl.init.data.worldgen.tree.SilverwoodTree;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class T7Blocks {
  public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Thavma.MODID);

  public static final DeferredBlock<AuraNodeBlock> AURA_NODE = T7Blocks.register("aura_node", AuraNodeBlock::new);
  public static final DeferredBlock<Block> ESSENTIA_CONTAINER = T7Blocks.register("essentia_container", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));

  public static final DeferredBlock<CrucibleBlock> CRUCIBLE = T7Blocks.register("crucible", CrucibleBlock::new);
  public static final DeferredBlock<WorkbenchBlock> ARCANE_WORKBENCH = T7Blocks.register("arcane_workbench", WorkbenchBlock::new);
  public static final DeferredBlock<MatrixBlock> MATRIX = T7Blocks.register("infusion_matrix", MatrixBlock::new);
  public static final DeferredBlock<PillarBlock> PILLAR = T7Blocks.register("infusion_pillar", PillarBlock::new);
  public static final DeferredBlock<PedestalBlock> PEDESTAL = T7Blocks.register("infusion_pedestal", PedestalBlock::new);
  public static final DeferredBlock<ResearchTableBlock> RESEARCH_TABLE = T7Blocks.register("research_table", ResearchTableBlock::new);

  public static final DeferredBlock<Block> ELEMENTAL_STONE = T7Blocks.register("elemental_stone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));

  public static final DeferredBlock<InfusedBlock> IGNIS_INFUSED_STONE = T7Blocks.register("ignis_infused_stone", () -> new InfusedBlock(Aspects.IGNIS, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> AER_INFUSED_STONE = T7Blocks.register("aer_infused_stone", () -> new InfusedBlock(Aspects.AER, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> TERRA_INFUSED_STONE = T7Blocks.register("terra_infused_stone", () -> new InfusedBlock(Aspects.TERRA, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> AQUA_INFUSED_STONE = T7Blocks.register("aqua_infused_stone", () -> new InfusedBlock(Aspects.AQUA, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> ORDO_INFUSED_STONE = T7Blocks.register("ordo_infused_stone", () -> new InfusedBlock(Aspects.ORDO, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> PERDITIO_INFUSED_STONE = T7Blocks.register("perditio_infused_stone", () -> new InfusedBlock(Aspects.PERDITIO, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE), () -> Blocks.STONE));
  public static final DeferredBlock<InfusedBlock> IGNIS_INFUSED_DEEPSLATE = T7Blocks.register("ignis_infused_deepslate", () -> new InfusedBlock(Aspects.IGNIS, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final DeferredBlock<InfusedBlock> AER_INFUSED_DEEPSLATE = T7Blocks.register("aer_infused_deepslate", () -> new InfusedBlock(Aspects.AER, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final DeferredBlock<InfusedBlock> TERRA_INFUSED_DEEPSLATE = T7Blocks.register("terra_infused_deepslate", () -> new InfusedBlock(Aspects.TERRA, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final DeferredBlock<InfusedBlock> AQUA_INFUSED_DEEPSLATE = T7Blocks.register("aqua_infused_deepslate", () -> new InfusedBlock(Aspects.AQUA, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final DeferredBlock<InfusedBlock> ORDO_INFUSED_DEEPSLATE = T7Blocks.register("ordo_infused_deepslate", () -> new InfusedBlock(Aspects.ORDO, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final DeferredBlock<InfusedBlock> PERDITIO_INFUSED_DEEPSLATE = T7Blocks.register("perditio_infused_deepslate", () -> new InfusedBlock(Aspects.PERDITIO, BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE), () -> Blocks.DEEPSLATE));
  public static final List<DeferredBlock<InfusedBlock>> INFUSED_BLOCKS = List.of(T7Blocks.IGNIS_INFUSED_STONE, T7Blocks.AER_INFUSED_STONE, T7Blocks.TERRA_INFUSED_STONE, T7Blocks.AQUA_INFUSED_STONE, T7Blocks.ORDO_INFUSED_STONE, T7Blocks.PERDITIO_INFUSED_STONE, T7Blocks.IGNIS_INFUSED_DEEPSLATE, T7Blocks.AER_INFUSED_DEEPSLATE, T7Blocks.TERRA_INFUSED_DEEPSLATE, T7Blocks.AQUA_INFUSED_DEEPSLATE, T7Blocks.ORDO_INFUSED_DEEPSLATE, T7Blocks.PERDITIO_INFUSED_DEEPSLATE);

  public static final DeferredBlock<LeavesBlock> GREATWOOD_LEAVES = T7Blocks.register("greatwood_leaves", T7Blocks::leaves);
  public static final DeferredBlock<RotatedPillarBlock> GREATWOOD_LOG = T7Blocks.register("greatwood_log", () -> T7Blocks.log(MapColor.WOOD, MapColor.PODZOL));
  public static final DeferredBlock<Block> GREATWOOD_PLANKS = T7Blocks.register("greatwood_planks", T7Blocks::plank);
  public static final DeferredBlock<SaplingBlock> GREATWOOD_SAPLING = T7Blocks.register("greatwood_sapling", () -> T7Blocks.sapling(GreatwoodTree.GROWER));

  public static final DeferredBlock<LeavesBlock> SILVERWOOD_LEAVES = T7Blocks.register("silverwood_leaves", T7Blocks::leaves);
  public static final DeferredBlock<RotatedPillarBlock> SILVERWOOD_LOG = T7Blocks.register("silverwood_log", () -> T7Blocks.log(MapColor.WOOD, MapColor.PODZOL));
  public static final DeferredBlock<Block> SILVERWOOD_PLANKS = T7Blocks.register("silverwood_planks", T7Blocks::plank);
  public static final DeferredBlock<SaplingBlock> SILVERWOOD_SAPLING = T7Blocks.register("silverwood_sapling", () -> T7Blocks.sapling(SilverwoodTree.GROWER));

  public static final DeferredBlock<Block> ARCANUM_BLOCK = T7Blocks.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
  public static final DeferredBlock<Block> ORICHALCUM_BLOCK = T7Blocks.register("orichalcum_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

  private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> sup) {
    DeferredBlock<T> block = T7Blocks.REGISTRAR.register(name, sup);
    T7Items.REGISTRAR.registerSimpleBlockItem(name, block);
    return block;
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

  private static SaplingBlock sapling(TreeGrower treeGrower) {
    return new SaplingBlock(
        treeGrower,
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
