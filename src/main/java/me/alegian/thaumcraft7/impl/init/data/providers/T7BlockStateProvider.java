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
        .texture("particle", Thaumcraft.id("block/crucible_side"))
        .texture("top", Thaumcraft.id("block/crucible_top"))
        .texture("bottom", Thaumcraft.id("block/crucible_bottom"))
        .texture("side", Thaumcraft.id("block/crucible_side"))
        .texture("inside", Thaumcraft.id("block/crucible_inner"))
    );

    infusedOreBlockWithItem(T7Blocks.IGNIS_INFUSED_STONE.get());
    infusedOreBlockWithItem(T7Blocks.AER_INFUSED_STONE.get());
    infusedOreBlockWithItem(T7Blocks.TERRA_INFUSED_STONE.get());
    infusedOreBlockWithItem(T7Blocks.AQUA_INFUSED_STONE.get());
    infusedOreBlockWithItem(T7Blocks.ORDO_INFUSED_STONE.get());
    infusedOreBlockWithItem(T7Blocks.PERDITIO_INFUSED_STONE.get());

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

    simpleBlockWithItem(T7Blocks.ELEMENTAL_STONE.get());

    simpleBlockWithItem(T7Blocks.ARCANE_WORKBENCH.get(), models().getBuilder(T7Blocks.ARCANE_WORKBENCH.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/crafting_table"))
    );

    simpleBlockWithItem(T7Blocks.ESSENTIA_CONTAINER.get(), models().getExistingFile(modLoc("essentia_container")));

    itemModels().getBuilder(T7Blocks.AURA_NODE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType(RenderType.translucent().name).texture("layer0", Thaumcraft.id("item/aura_node"));
  }

  private void simpleBlockWithItem(Block block) {
    simpleBlockWithItem(block, cubeAll(block));
  }

  private void logBlockWithItem(RotatedPillarBlock block) {
    ResourceLocation blockRL = blockTexture(block);
    logBlock(block);
    itemModels().withExistingParent(name(block), blockRL);
  }

  private void infusedOreBlockWithItem(InfusedStoneBlock block) {
    var infusedOreBlockModel = models().withExistingParent(name(block), mcLoc("block/stone"))
        .customLoader(CubeOverlayModel.Builder::new)
        .spriteLocation(Thaumcraft.id("block/infused_ore"))
        .color((block).getAspect().getColor())
        .end();
    simpleBlockWithItem(block, infusedOreBlockModel);
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
