package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.model.CubeOverlayModel;
import me.alegian.thaumcraft7.impl.common.block.InfusedStoneBlock;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class T7BlockStateProvider extends BlockStateProvider {
  public T7BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Thaumcraft.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlockWithItem(T7Blocks.CRUCIBLE.get(), models().getBuilder(T7Blocks.CRUCIBLE.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/cauldron"))
        .texture("particle", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crucible_side"))
        .texture("top", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crucible_top"))
        .texture("bottom", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crucible_bottom"))
        .texture("side", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crucible_side"))
        .texture("inside", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crucible_inner"))
    );

    crystalOreBlockWithItem(T7Blocks.IGNIS_INFUSED_STONE.get());
    crystalOreBlockWithItem(T7Blocks.AER_INFUSED_STONE.get());
    crystalOreBlockWithItem(T7Blocks.TERRA_INFUSED_STONE.get());
    crystalOreBlockWithItem(T7Blocks.AQUA_INFUSED_STONE.get());
    crystalOreBlockWithItem(T7Blocks.ORDO_INFUSED_STONE.get());
    crystalOreBlockWithItem(T7Blocks.PERDITIO_INFUSED_STONE.get());

    logBlockWithItem(T7Blocks.GREATWOOD_LOG.get());
    simpleBlockWithItem(T7Blocks.GREATWOOD_PLANKS.get());
    leavesBlockWithItem(T7Blocks.GREATWOOD_LEAVES.get());
    saplingBlockWithItem(T7Blocks.GREATWOOD_SAPLING.get());

    logBlockWithItem(T7Blocks.SILVERWOOD_LOG.get());
    simpleBlockWithItem(T7Blocks.SILVERWOOD_PLANKS.get());
    leavesBlockWithItem(T7Blocks.SILVERWOOD_LEAVES.get());
    saplingBlockWithItem(T7Blocks.SILVERWOOD_SAPLING.get());

    simpleBlockWithItem(T7Blocks.ARCANUM_BLOCK.get());
    simpleBlockWithItem(T7Blocks.ORICHALCUM_BLOCK.get());

    simpleBlockWithItem(T7Blocks.ARCANE_WORKBENCH.get(), models().getBuilder(T7Blocks.ARCANE_WORKBENCH.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/crafting_table"))
    );

    itemModels().getBuilder(T7Blocks.AURA_NODE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType(RenderType.translucent().name).texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/aura_node"));
  }

  private void simpleBlockWithItem(Block block) {
    simpleBlockWithItem(block, cubeAll(block));
  }

  private void logBlockWithItem(RotatedPillarBlock block) {
    ResourceLocation blockRL = blockTexture(block);
    logBlock(block);
    itemModels().withExistingParent(name(block), blockRL);
  }

  private void crystalOreBlockWithItem(InfusedStoneBlock block) {
    var crystalOreBlockModel = models().withExistingParent(name(block), mcLoc("block/stone"))
        .customLoader(CubeOverlayModel.Builder::new)
        .spriteLocation(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crystal_ore"))
        .color((block).getAspect().getColor())
        .end();
    simpleBlockWithItem(block, crystalOreBlockModel);
  }

  public void leavesBlockWithItem(LeavesBlock block) {
    var blockRL = blockTexture(block);
    var model = models().leaves(name(block), blockRL);
    simpleBlockWithItem(block, model);
  }

  public void saplingBlockWithItem(SaplingBlock block) {
    var blockRL = blockTexture(block);
    var model = models().cross(name(block), blockRL).renderType(RenderType.cutout().name);
    simpleBlock(block, model);
    itemModels().withExistingParent(name(block), mcLoc("item/generated")).texture("layer0", blockRL);
  }

  private ModelFile existing(ResourceLocation location) {
    return itemModels().getExistingFile(location);
  }

  private ResourceLocation key(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block);
  }

  private String name(Block block) {
    return key(block).getPath();
  }
}
