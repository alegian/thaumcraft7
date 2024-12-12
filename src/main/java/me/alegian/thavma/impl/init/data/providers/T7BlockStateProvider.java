package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.model.WithTransformParentModel;
import me.alegian.thavma.impl.common.block.InfusedStoneBlock;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class T7BlockStateProvider extends BlockStateProvider {
  public T7BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Thavma.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    this.simpleBlockWithItem(T7Blocks.CRUCIBLE.get(), this.models().getBuilder(T7Blocks.CRUCIBLE.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/cauldron"))
        .texture("particle", Thavma.rl("block/crucible_side"))
        .texture("top", Thavma.rl("block/crucible_top"))
        .texture("bottom", Thavma.rl("block/crucible_bottom"))
        .texture("side", Thavma.rl("block/crucible_side"))
        .texture("inside", Thavma.rl("block/crucible_inner"))
        .customLoader(WithTransformParentModel.Builder::new)
        .transformParent(ResourceLocation.withDefaultNamespace("block/block"))
        .end()
    );

    for (var infusedStone : T7Blocks.INFUSED_STONES)
      this.infusedBlockWithItem(infusedStone.get(), Blocks.STONE);

    this.logBlockWithItem(T7Blocks.GREATWOOD_LOG.get());
    this.simpleBlockWithItem(T7Blocks.GREATWOOD_PLANKS.get());
    this.leavesBlockWithItem(T7Blocks.GREATWOOD_LEAVES.get());
    this.saplingBlockWithItem(T7Blocks.GREATWOOD_SAPLING.get());

    this.logBlockWithItem(T7Blocks.SILVERWOOD_LOG.get());
    this.simpleBlockWithItem(T7Blocks.SILVERWOOD_PLANKS.get());
    this.leavesBlockWithItem(T7Blocks.SILVERWOOD_LEAVES.get());
    this.saplingBlockWithItem(T7Blocks.SILVERWOOD_SAPLING.get());

    this.simpleBlockWithItem(T7Blocks.ARCANUM_BLOCK.get());
    this.simpleBlockWithItem(T7Blocks.ORICHALCUM_BLOCK.get());

    this.simpleBlockWithItem(T7Blocks.ELEMENTAL_STONE.get());

    this.blockEntity1x1x1(T7Blocks.ARCANE_WORKBENCH.get());
    this.blockEntity1x1x1(T7Blocks.MATRIX.get());
    this.blockEntity1x1x1(T7Blocks.PEDESTAL.get());
    this.blockEntity1x2x1(T7Blocks.PILLAR.get());

    this.simpleBlockWithItem(T7Blocks.ESSENTIA_CONTAINER.get(), this.models().getExistingFile(Thavma.rl("essentia_container")));
    this.simpleBlockWithItem(T7Blocks.RESEARCH_TABLE.get(), this.models().getExistingFile(Thavma.rl("research_table")));

    this.itemModels().getBuilder(T7Blocks.AURA_NODE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType(RenderType.translucent().name).texture("layer0", Thavma.rl("item/aura_node"));
  }

  private void simpleBlockWithItem(Block block) {
    this.simpleBlockWithItem(block, this.cubeAll(block));
  }

  private void logBlockWithItem(RotatedPillarBlock block) {
    ResourceLocation blockRL = this.blockTexture(block);
    this.logBlock(block);
    this.itemModels().withExistingParent(this.name(block), blockRL);
  }

  private void infusedBlockWithItem(InfusedStoneBlock block, Block baseBlock) {
    var infusedOreBlockModel = this.models().withExistingParent(this.name(block), Thavma.rl("block/infused_stone"))
        .texture("layer0", this.blockTexture(baseBlock))
        .texture("layer1", Thavma.rl("block/infused_stone"));
    this.simpleBlockWithItem(block, infusedOreBlockModel);
  }

  public void leavesBlockWithItem(LeavesBlock block) {
    var blockRL = this.blockTexture(block);
    var model = this.models().leaves(this.name(block), blockRL);
    this.simpleBlockWithItem(block, model);
  }

  public void saplingBlockWithItem(SaplingBlock block) {
    var blockRL = this.blockTexture(block);
    var model = this.models().cross(this.name(block), blockRL).renderType(RenderType.cutout().name);
    this.simpleBlock(block, model);
    this.itemModels().withExistingParent(this.name(block), "item/generated").texture("layer0", blockRL);
  }

  public void blockEntity1x1x1(Block block) {
    this.simpleBlockWithItem(block, this.models().getBuilder(this.name(block)).texture("particle", this.blockTexture(block)));
    this.itemModels().withExistingParent(this.name(block), "item/chest").texture("particle", this.blockTexture(block));
  }

  public void blockEntity1x2x1(Block block) {
    this.simpleBlockWithItem(block, this.models().getBuilder(this.name(block)).texture("particle", this.blockTexture(block)));
    this.itemModels().withExistingParent(this.name(block), "item/template_bed").texture("particle", this.blockTexture(block));
  }

  private ResourceLocation key(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block);
  }

  private String name(Block block) {
    return this.key(block).getPath();
  }
}
