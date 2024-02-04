package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.capabilities.TCCapabilities;
import me.alegian.thaumcraft7.capability.VisStorage;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    public static final DeferredItem<Item> IRON_CAP = ITEMS.registerSimpleItem("iron_cap", new Item.Properties());
    public static final DeferredItem<WandI> IRON_WOOD_WAND = ITEMS.registerItem("iron_wood_wand", WandI::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<ThaumometerI> THAUMOMETER = ITEMS.registerItem("thaumometer", ThaumometerI::new);
    public static final DeferredItem<ThaumonomiconI> THAUMONOMICON = ITEMS.registerItem("thaumonomicon", ThaumonomiconI::new);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(TCCapabilities.VisStorage.ITEM, (itemStack, context)->new VisStorage(50, itemStack), IRON_WOOD_WAND);
    }

    // "using" predicate to switch item models
    // used in wand
    static {
        ItemProperties.registerGeneric(new ResourceLocation("thaumcraft7", "using"), (itemStack, clientLevel, livingEntity, seed) ->
            livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F : 0.0F
        );
    }
}
