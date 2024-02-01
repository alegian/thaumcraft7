package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.BlockIndex;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabIndex {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

    static{
        CREATIVE_MODE_TABS.register(
            "thaumcraft_tab",
            () -> CreativeModeTab
                .builder()
                .title(Component.translatable("thaumcraft"))
                .icon(() -> ItemIndex.IRON_WOOD_WAND.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(BlockIndex.EXAMPLE_BLOCK.get());
                    output.accept(BlockIndex.AURA_NODE_BLOCK.get());

                    output.accept(ItemIndex.IRON_CAP.get());
                    output.accept(ItemIndex.IRON_WOOD_WAND.get());
                    output.accept(ItemIndex.THAUMOMETER.get());
                    output.accept(ItemIndex.THAUMONOMICON.get());
                })
                .build()
        );
    }
}
