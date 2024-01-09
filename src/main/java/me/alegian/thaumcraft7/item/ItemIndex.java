package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.api.capabilities.ThaumcraftCapabilities;
import me.alegian.thaumcraft7.capability.VisStorage;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemIndex {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    public static final DeferredItem<Item> IRON_CAP = ITEMS.registerSimpleItem("iron_cap", new Item.Properties());
    public static final DeferredItem<WandItem> IRON_WOOD_WAND = ITEMS.registerItem("iron_wood_wand", WandItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<ThaumometerItem> THAUMOMETER = ITEMS.registerItem("thaumometer", ThaumometerItem::new);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(ThaumcraftCapabilities.VisStorage.ITEM, (itemStack, context)->new VisStorage(50), IRON_WOOD_WAND);
    }
}
