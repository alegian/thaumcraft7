package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.capability.VisStorage;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemIndex {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    // Creates a new item with the id "examplemod:example_item"
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item");
    public static final DeferredItem<Item> IRON_CAP = ITEMS.registerSimpleItem("iron_cap", new Item.Properties());
    public static final DeferredItem<Item> IRON_WOOD_WAND = ITEMS.registerItem("iron_wood_wand", WandItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> THAUMOMETER = ITEMS.registerItem("thaumometer", ThaumometerItem::new);

    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerItem(VisStorage.ITEM, (itemStack, context)->new VisStorage(50), IRON_WOOD_WAND);
        event.registerItem(Capabilities.EnergyStorage.ITEM, (itemStack, context)->new EnergyStorage(5000), IRON_WOOD_WAND);
    }
}
