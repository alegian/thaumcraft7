package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.model.CubeOverlayModel;
import me.alegian.thaumcraft7.impl.common.block.InfusedStoneBlock;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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

    crystalOreBlockWithItem(T7Blocks.IGNIS_INFUSED_STONE);
    crystalOreBlockWithItem(T7Blocks.AER_INFUSED_STONE);
    crystalOreBlockWithItem(T7Blocks.TERRA_INFUSED_STONE);
    crystalOreBlockWithItem(T7Blocks.AQUA_INFUSED_STONE);
    crystalOreBlockWithItem(T7Blocks.ORDO_INFUSED_STONE);
    crystalOreBlockWithItem(T7Blocks.PERDITIO_INFUSED_STONE);

    itemModels().getBuilder(T7Blocks.AURA_NODE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType(RenderType.translucent().name).texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/aura_node"));
  }

  private void crystalOreBlockWithItem(DeferredBlock<InfusedStoneBlock> deferredBlock){
    var crystalOreBlockModel = models().getBuilder(deferredBlock.getId().getPath())
        .customLoader(CubeOverlayModel.Builder::new)
        .spriteLocation(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crystal_ore"))
        .color((deferredBlock.get()).getAspect().getColor())
        .end()
        .parent(new ModelFile.UncheckedModelFile("block/stone"));
    simpleBlockWithItem(deferredBlock.get(), crystalOreBlockModel);
  }
}
