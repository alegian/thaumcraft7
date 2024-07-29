package me.alegian.thaumcraft7.impl.init.data.providers;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Blocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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

    itemModels().getBuilder(T7Blocks.AURA_NODE_BLOCK.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/generated")).renderType("translucent").texture("layer0", ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "item/aura_node"));
  }
}
