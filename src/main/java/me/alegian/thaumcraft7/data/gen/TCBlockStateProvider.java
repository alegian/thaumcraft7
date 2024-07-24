package me.alegian.thaumcraft7.data.gen;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TCBlockStateProvider extends BlockStateProvider {
  public TCBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Thaumcraft.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    horizontalBlock(TCBlocks.CRUCIBLE.get(), models().getExistingFile(mcLoc("block/cauldron")));
    itemModels().getBuilder(TCBlocks.CRUCIBLE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.withDefaultNamespace("item/cauldron"));

    itemModels().getBuilder(TCBlocks.AURA_NODE_BLOCK.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType("translucent").texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/aura_node"));
  }

  public void simpleBlockWithItem(Block block) {
    var model = cubeAll(block);
    simpleBlock(block, model);
    simpleBlockItem(block, model);
  }
}
