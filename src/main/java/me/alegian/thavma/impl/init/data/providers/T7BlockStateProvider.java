package me.alegian.thavma.impl.init.data.providers;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.model.CubeOverlayModel;
import me.alegian.thavma.impl.client.model.WithTransformParentModel;
import me.alegian.thavma.impl.common.block.InfusedStoneBlock;
import me.alegian.thavma.impl.init.registries.deferred.T7Blocks;
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
    super(output, Thavma.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    this.simpleBlockWithItem(T7Blocks.CRUCIBLE.get(), this.models().getBuilder(T7Blocks.CRUCIBLE.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/cauldron"))
        .texture("particle", Thavma.id("block/crucible_side"))
        .texture("top", Thavma.id("block/crucible_top"))
        .texture("bottom", Thavma.id("block/crucible_bottom"))
        .texture("side", Thavma.id("block/crucible_side"))
        .texture("inside", Thavma.id("block/crucible_inner"))
        .customLoader(WithTransformParentModel.Builder::new)
        .transformParent(ResourceLocation.withDefaultNamespace("block/block"))
        .end()
    );

    this.infusedOreBlockWithItem(T7Blocks.IGNIS_INFUSED_STONE.get());
    this.infusedOreBlockWithItem(T7Blocks.AER_INFUSED_STONE.get());
    this.infusedOreBlockWithItem(T7Blocks.TERRA_INFUSED_STONE.get());
    this.infusedOreBlockWithItem(T7Blocks.AQUA_INFUSED_STONE.get());
    this.infusedOreBlockWithItem(T7Blocks.ORDO_INFUSED_STONE.get());
    this.infusedOreBlockWithItem(T7Blocks.PERDITIO_INFUSED_STONE.get());

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

    this.simpleBlockWithItem(T7Blocks.ARCANE_WORKBENCH.get(), this.models().getBuilder(T7Blocks.ARCANE_WORKBENCH.getId().getPath())
        .parent(new ModelFile.UncheckedModelFile("block/crafting_table"))
    );

    this.simpleBlockWithItem(T7Blocks.ESSENTIA_CONTAINER.get(), this.models().getExistingFile(this.modLoc("essentia_container")));
    this.simpleBlockWithItem(T7Blocks.RESEARCH_TABLE.get(), this.models().getExistingFile(this.modLoc("research_table")));

    this.itemModels().getBuilder(T7Blocks.AURA_NODE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType(RenderType.translucent().name).texture("layer0", Thavma.id("item/aura_node"));
  }

  private void simpleBlockWithItem(Block block) {
    this.simpleBlockWithItem(block, this.cubeAll(block));
  }

  private void logBlockWithItem(RotatedPillarBlock block) {
    ResourceLocation blockRL = this.blockTexture(block);
    this.logBlock(block);
    this.itemModels().withExistingParent(this.name(block), blockRL);
  }

  private void infusedOreBlockWithItem(InfusedStoneBlock block) {
    var infusedOreBlockModel = this.models().withExistingParent(this.name(block), this.mcLoc("block/stone"))
        .customLoader(CubeOverlayModel.Builder::new)
        .spriteLocation(Thavma.id("block/infused_ore"))
        .color((block).getAspect().getColor())
        .end();
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
    this.itemModels().withExistingParent(this.name(block), this.mcLoc("item/generated")).texture("layer0", blockRL);
  }

  private ModelFile existing(ResourceLocation location) {
    return this.itemModels().getExistingFile(location);
  }

  private ResourceLocation key(Block block) {
    return BuiltInRegistries.BLOCK.getKey(block);
  }

  private String name(Block block) {
    return this.key(block).getPath();
  }
}
