package me.alegian.thavma.impl.init.registries.deferred

import me.alegian.thavma.impl.Thavma
import me.alegian.thavma.impl.common.block.*
import me.alegian.thavma.impl.init.data.worldgen.tree.GreatwoodTree
import me.alegian.thavma.impl.init.data.worldgen.tree.SilverwoodTree
import net.minecraft.core.Direction
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.grower.TreeGrower
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object T7Blocks {
    val REGISTRAR = DeferredRegister.createBlocks(Thavma.MODID)

    val AURA_NODE = register(
        "aura_node"
    ) { AuraNodeBlock() }

    val ESSENTIA_CONTAINER = register(
        "essentia_container"
    ) { Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)) }

    val CRUCIBLE = register(
        "crucible"
    ) { CrucibleBlock() }

    val ARCANE_WORKBENCH = register(
        "arcane_workbench"
    ) { WorkbenchBlock() }

    val MATRIX = register(
        "infusion_matrix"
    ) { MatrixBlock() }

    val PILLAR = register(
        "infusion_pillar"
    ) { PillarBlock() }

    val PEDESTAL = register(
        "infusion_pedestal"
    ) { PedestalBlock() }

    val RESEARCH_TABLE = register(
        "research_table"
    ) { ResearchTableBlock() }

    val ELEMENTAL_STONE = register(
        "elemental_stone"
    ) { Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)) }

    val IGNIS_INFUSED_STONE = register(
        "ignis_infused_stone"
    ) {
        InfusedBlock(
            Aspects.IGNIS,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val AER_INFUSED_STONE = register(
        "aer_infused_stone"
    ) {
        InfusedBlock(
            Aspects.AER,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val TERRA_INFUSED_STONE = register(
        "terra_infused_stone"
    ) {
        InfusedBlock(
            Aspects.TERRA,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val AQUA_INFUSED_STONE = register(
        "aqua_infused_stone"
    ) {
        InfusedBlock(
            Aspects.AQUA,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val ORDO_INFUSED_STONE = register(
        "ordo_infused_stone"
    ) {
        InfusedBlock(
            Aspects.ORDO,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val PERDITIO_INFUSED_STONE = register(
        "perditio_infused_stone"
    ) {
        InfusedBlock(
            Aspects.PERDITIO,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
        ) { Blocks.STONE }
    }

    val IGNIS_INFUSED_DEEPSLATE = register(
        "ignis_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.IGNIS,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val AER_INFUSED_DEEPSLATE = register(
        "aer_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.AER,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val TERRA_INFUSED_DEEPSLATE = register(
        "terra_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.TERRA,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val AQUA_INFUSED_DEEPSLATE = register(
        "aqua_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.AQUA,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val ORDO_INFUSED_DEEPSLATE = register(
        "ordo_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.ORDO,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val PERDITIO_INFUSED_DEEPSLATE = register(
        "perditio_infused_deepslate"
    ) {
        InfusedBlock(
            Aspects.PERDITIO,
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
        ) { Blocks.DEEPSLATE }
    }

    val INFUSED_BLOCKS = listOf(
        IGNIS_INFUSED_STONE,
        AER_INFUSED_STONE,
        TERRA_INFUSED_STONE,
        AQUA_INFUSED_STONE,
        ORDO_INFUSED_STONE,
        PERDITIO_INFUSED_STONE,
        IGNIS_INFUSED_DEEPSLATE,
        AER_INFUSED_DEEPSLATE,
        TERRA_INFUSED_DEEPSLATE,
        AQUA_INFUSED_DEEPSLATE,
        ORDO_INFUSED_DEEPSLATE,
        PERDITIO_INFUSED_DEEPSLATE
    )

    val GREATWOOD_LEAVES = register(
        "greatwood_leaves",
        ::leaves
    )

    val GREATWOOD_LOG = register(
        "greatwood_log"
    ) { log(MapColor.WOOD, MapColor.PODZOL) }

    val GREATWOOD_PLANKS = register(
        "greatwood_planks",
        ::plank
    )

    val GREATWOOD_SAPLING = register(
        "greatwood_sapling"
    ) { sapling(GreatwoodTree.GROWER) }

    val SILVERWOOD_LEAVES = register(
        "silverwood_leaves",
        ::leaves
    )

    val SILVERWOOD_LOG = register(
        "silverwood_log"
    ) { log(MapColor.WOOD, MapColor.PODZOL) }

    val SILVERWOOD_PLANKS = register(
        "silverwood_planks",
        ::plank
    )

    val SILVERWOOD_SAPLING = register(
        "silverwood_sapling"
    ) { sapling(SilverwoodTree.GROWER) }

    val ARCANUM_BLOCK = register(
        "arcanum_block"
    ) { Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)) }
    
    val ORICHALCUM_BLOCK = register(
        "orichalcum_block"
    ) { Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)) }

    private fun <T : Block> register(name: String, sup: Supplier<T>): DeferredBlock<T> {
        val block = REGISTRAR.register(name, sup)
        T7Items.REGISTRAR.registerSimpleBlockItem(name, block)
        return block
    }

    private fun leaves(): LeavesBlock {
        return LeavesBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2f)
                .randomTicks()
                .sound(SoundType.GRASS)
                .noOcclusion()
                .isValidSpawn { state, blockGetter, pos, entity ->
                    Blocks.ocelotOrParrot(
                        state,
                        blockGetter,
                        pos,
                        entity
                    )
                }
                .isSuffocating { _, _, _ -> false }
                .isViewBlocking { _, _, _ -> false }
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor { _, _, _ -> false }
        )
    }

    private fun log(pTopMapColor: MapColor, pSideMapColor: MapColor): RotatedPillarBlock {
        return RotatedPillarBlock(
            BlockBehaviour.Properties.of()
                .mapColor { blockState ->
                    if (blockState.getValue(
                            RotatedPillarBlock.AXIS
                        ) === Direction.Axis.Y
                    ) pTopMapColor else pSideMapColor
                }
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0f)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    }

    private fun plank(): Block {
        return Block(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0f, 3.0f)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        )
    }

    private fun sapling(treeGrower: TreeGrower): SaplingBlock {
        return SaplingBlock(
            treeGrower,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY)
        )
    }
}