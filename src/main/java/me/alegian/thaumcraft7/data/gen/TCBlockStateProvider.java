package me.alegian.thaumcraft7.data.gen;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class TCBlockStateProvider extends BlockStateProvider {
  public TCBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, Thaumcraft.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlockWithItem(TCBlocks.EXAMPLE_BLOCK.get());
  }

  public void simpleBlockWithItem(Block block) {
    var model = cubeAll(block);
    simpleBlock(block, model);
    simpleBlockItem(block, model);
  }
}
