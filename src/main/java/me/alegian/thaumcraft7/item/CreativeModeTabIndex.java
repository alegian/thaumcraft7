package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.BlockIndex;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabIndex {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> THAUMCRAFT_TAB = CREATIVE_MODE_TABS.register(
        "thaumcraft_tab",
        () -> CreativeModeTab
            .builder()
                .title(Component.translatable("thaumcraft"))
            .icon(() -> ItemIndex.EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemIndex.EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(BlockIndex.EXAMPLE_BLOCK.get());
                output.accept(ItemIndex.IRON_CAP.get());
                output.accept(ItemIndex.IRON_WOOD_WAND.get());
                output.accept(ItemIndex.THAUMOMETER.get());
            })
            .build()
    );
}
