package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7CreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

  static {
    REGISTRAR.register(
        "thaumcraft_tab",
        () -> CreativeModeTab
            .builder()
            .title(Component.translatable("thaumcraft"))
            .icon(() -> T7Items.IRON_WOOD_WAND.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
              output.accept(T7Blocks.AURA_NODE.get());
              output.accept(T7Blocks.IGNIS_INFUSED_STONE.get());
              output.accept(T7Blocks.AER_INFUSED_STONE.get());
              output.accept(T7Blocks.TERRA_INFUSED_STONE.get());
              output.accept(T7Blocks.AQUA_INFUSED_STONE.get());
              output.accept(T7Blocks.ORDO_INFUSED_STONE.get());
              output.accept(T7Blocks.PERDITIO_INFUSED_STONE.get());
              output.accept(T7Blocks.GREATWOOD_LEAVES.get());
              output.accept(T7Blocks.GREATWOOD_LOG.get());
              output.accept(T7Blocks.GREATWOOD_PLANKS.get());
              output.accept(T7Blocks.GREATWOOD_SAPLING.get());

              output.accept(T7Items.IRON_CAP.get());
              output.accept(T7Items.IRON_WOOD_WAND.get());
              output.accept(T7Items.THAUMOMETER.get());
              output.accept(T7Items.THAUMONOMICON.get());
              output.accept(T7Items.GOGGLES.get());
              output.accept(T7Items.IGNIS_SHARD.get());
              output.accept(T7Items.AER_SHARD.get());
              output.accept(T7Items.TERRA_SHARD.get());
              output.accept(T7Items.AQUA_SHARD.get());
              output.accept(T7Items.ORDO_SHARD.get());
              output.accept(T7Items.PERDITIO_SHARD.get());
            })
            .build()
    );
  }
}
