package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemIndex {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    // Creates a new item with the id "examplemod:example_item"
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties());
    public static final DeferredItem<Item> IRON_CAP = ITEMS.registerSimpleItem("iron_cap", new Item.Properties());
    public static final DeferredItem<Item> IRON_WOOD_WAND = ITEMS.registerSimpleItem("iron_wood_wand", new Item.Properties().stacksTo(1));
}
