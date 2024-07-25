package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCCreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> REGISTRAR = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

  static {
    REGISTRAR.register(
        "thaumcraft_tab",
        () -> CreativeModeTab
            .builder()
            .title(Component.translatable("thaumcraft"))
            .icon(() -> TCItems.IRON_WOOD_WAND.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
              output.accept(TCBlocks.AURA_NODE_BLOCK.get());
              output.accept(TCBlocks.CRUCIBLE.get());

              output.accept(TCItems.IRON_CAP.get());
              output.accept(TCItems.IRON_WOOD_WAND.get());
              output.accept(TCItems.THAUMOMETER.get());
              output.accept(TCItems.THAUMONOMICON.get());
              output.accept(TCItems.GOGGLES.get());
            })
            .build()
    );
  }
}
