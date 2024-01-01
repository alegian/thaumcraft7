package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.Thaumcraft;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemIndex {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thaumcraft.MODID);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.register("example_item", ()-> new Item(new Item.Properties()));
}
