package me.alegian.thaumcraft7.datagen;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.BlockIndex;
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
        simpleBlockAndItem(BlockIndex.EXAMPLE_BLOCK.get(), "node");
    }

    protected void simpleBlockAndItem(Block block) {
        var model = cubeAll(block);
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    protected void simpleBlockAndItem(Block block, ModelFile model) {
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    protected void simpleBlockAndItem(Block block, String id) {
        var model = models().cubeAll(id, new ResourceLocation(Thaumcraft.MODID, "block/"+id));
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }
}
