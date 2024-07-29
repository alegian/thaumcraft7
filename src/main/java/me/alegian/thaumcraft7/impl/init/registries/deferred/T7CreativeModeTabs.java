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
              output.accept(T7Blocks.AURA_NODE_BLOCK.get());
              output.accept(T7Blocks.CRUCIBLE.get());

              output.accept(T7Items.IRON_CAP.get());
              output.accept(T7Items.IRON_WOOD_WAND.get());
              output.accept(T7Items.THAUMOMETER.get());
              output.accept(T7Items.THAUMONOMICON.get());
              output.accept(T7Items.GOGGLES.get());
            })
            .build()
    );
  }
}
