package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7CreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thavma.MODID);

  static {
    T7CreativeModeTabs.REGISTRAR.register(
        Thavma.MODID,
        () -> CreativeModeTab
            .builder()
            .title(Component.translatable(Thavma.MODID))
            .icon(() -> T7Items.THAUMONOMICON.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
              output.accept(T7Blocks.AURA_NODE.get());
              output.accept(T7Blocks.ESSENTIA_CONTAINER.get());

              output.accept(T7Blocks.CRUCIBLE.get());
              output.accept(T7Blocks.ARCANE_WORKBENCH.get());
              output.accept(T7Blocks.MATRIX.get());
              output.accept(T7Blocks.PILLAR.get());
              output.accept(T7Blocks.PEDESTAL.get());
              output.accept(T7Blocks.RESEARCH_TABLE.get());

              output.accept(T7Blocks.ELEMENTAL_STONE.get());

              for (var infusedBlock : T7Blocks.INFUSED_BLOCKS)
                output.accept(infusedBlock.get());

              output.accept(T7Blocks.ARCANUM_BLOCK.get());
              output.accept(T7Blocks.ORICHALCUM_BLOCK.get());

              output.accept(T7Blocks.GREATWOOD_LEAVES.get());
              output.accept(T7Blocks.GREATWOOD_LOG.get());
              output.accept(T7Blocks.GREATWOOD_PLANKS.get());
              output.accept(T7Blocks.GREATWOOD_SAPLING.get());

              output.accept(T7Blocks.SILVERWOOD_LEAVES.get());
              output.accept(T7Blocks.SILVERWOOD_SAPLING.get());
              output.accept(T7Blocks.SILVERWOOD_LOG.get());
              output.accept(T7Blocks.SILVERWOOD_PLANKS.get());

              output.accept(T7Items.IRON_HANDLE.get());
              output.accept(T7Items.GOLD_HANDLE.get());
              output.accept(T7Items.ORICHALCUM_HANDLE.get());
              output.accept(T7Items.ARCANUM_HANDLE.get());

              output.accept(T7Items.EYE_OF_WARDEN.get());

              output.accept(T7Items.GREATWOOD_CORE.get());
              output.accept(T7Items.SILVERWOOD_CORE.get());

              output.accept(T7Items.RUNE.get());
              output.accept(T7Items.ARCANUM_INGOT.get());
              output.accept(T7Items.ARCANUM_NUGGET.get());
              output.accept(T7Items.ORICHALCUM_INGOT.get());
              output.accept(T7Items.ORICHALCUM_NUGGET.get());
              output.accept(T7Items.RESEARCH_SCROLL.get());
              output.accept(T7Items.COMPLETED_RESEARCH.get());
              output.accept(T7Items.OCULUS.get());
              output.accept(T7Items.THAUMONOMICON.get());

              output.accept(T7Items.wandOrThrow(WandHandleMaterials.IRON.get(), WandCoreMaterials.WOOD.get()));
              output.accept(T7Items.wandOrThrow(WandHandleMaterials.GOLD.get(), WandCoreMaterials.GREATWOOD.get()));
              output.accept(T7Items.wandOrThrow(WandHandleMaterials.ARCANUM.get(), WandCoreMaterials.SILVERWOOD.get()));

              output.accept(T7Items.GOGGLES.get());
              output.accept(T7Items.GOGGLES_ACCESSORY.get());
              output.accept(T7Items.DAWN_CHARM.get());
              output.accept(T7Items.RESEARCHER_CHESTPLATE.get());
              output.accept(T7Items.RESEARCHER_LEGGINGS.get());
              output.accept(T7Items.RESEARCHER_BOOTS.get());

              output.accept(T7Items.ARCANUM_HELMET.get());
              output.accept(T7Items.ARCANUM_CHESTPLATE.get());
              output.accept(T7Items.ARCANUM_LEGGINGS.get());
              output.accept(T7Items.ARCANUM_BOOTS.get());

              output.accept(T7Items.CUSTOS_ARCANUM_HELMET.get());
              output.accept(T7Items.CUSTOS_ARCANUM_CHESTPLATE.get());
              output.accept(T7Items.CUSTOS_ARCANUM_LEGGINGS.get());
              output.accept(T7Items.CUSTOS_ARCANUM_BOOTS.get());

              output.accept(T7Items.IGNIS_TESTA.get());
              output.accept(T7Items.AER_TESTA.get());
              output.accept(T7Items.TERRA_TESTA.get());
              output.accept(T7Items.AQUA_TESTA.get());
              output.accept(T7Items.ORDO_TESTA.get());
              output.accept(T7Items.PERDITIO_TESTA.get());

              output.accept(T7Items.ARCANUM_AXE.get());
              output.accept(T7Items.ARCANUM_PICKAXE.get());
              output.accept(T7Items.ARCANUM_HAMMER.get());
              output.accept(T7Items.ARCANUM_SHOVEL.get());
              output.accept(T7Items.ARCANUM_HOE.get());

              output.accept(T7Items.ARCANUM_SWORD.get());
              output.accept(T7Items.ARCANUM_KATANA.get());
              output.accept(T7Items.ZEPHYR.get());
            })
            .build()
    );
  }
}
