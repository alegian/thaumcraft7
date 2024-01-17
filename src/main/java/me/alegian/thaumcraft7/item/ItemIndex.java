package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.capabilities.ThaumcraftCapabilities;
import me.alegian.thaumcraft7.capability.VisStorage;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemIndex {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    public static final DeferredItem<Item> IRON_CAP = ITEMS.registerSimpleItem("iron_cap", new Item.Properties());
    public static final DeferredItem<WandItem> IRON_WOOD_WAND = ITEMS.registerItem("iron_wood_wand", WandItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<ThaumometerItem> THAUMOMETER = ITEMS.registerItem("thaumometer", ThaumometerItem::new);
    public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = ITEMS.registerItem("thaumonomicon", ThaumonomiconItem::new);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(ThaumcraftCapabilities.VisStorage.ITEM, (itemStack, context)->new VisStorage(50, itemStack), IRON_WOOD_WAND);
    }

    static {
        ItemProperties.registerGeneric(new ResourceLocation("thaumcraft7", "using"), (itemStack, clientLevel, livingEntity, seed) ->
            livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F : 0.0F
        );
    }
}
