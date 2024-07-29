package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class T7BlockStateProvider extends BlockStateProvider {
  public T7BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Thaumcraft.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    horizontalBlock(T7Blocks.CRUCIBLE.get(), models().getExistingFile(mcLoc("block/cauldron")));
    itemModels().getBuilder(T7Blocks.CRUCIBLE.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.withDefaultNamespace("item/cauldron"));

    itemModels().getBuilder(T7Blocks.AURA_NODE_BLOCK.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType("translucent").texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/aura_node"));
  }

  public void simpleBlockWithItem(Block block) {
    var model = cubeAll(block);
    simpleBlock(block, model);
    simpleBlockItem(block, model);
  }
}
