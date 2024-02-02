package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

    static{
        CREATIVE_MODE_TABS.register(
            "thaumcraft_tab",
            () -> CreativeModeTab
                .builder()
                .title(Component.translatable("thaumcraft"))
                .icon(() -> TCItems.IRON_WOOD_WAND.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(TCBlocks.EXAMPLE_BLOCK.get());
                    output.accept(TCBlocks.AURA_NODE_BLOCK.get());

                    output.accept(TCItems.IRON_CAP.get());
                    output.accept(TCItems.IRON_WOOD_WAND.get());
                    output.accept(TCItems.THAUMOMETER.get());
                    output.accept(TCItems.THAUMONOMICON.get());
                })
                .build()
        );
    }
}
