package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.capability.VisStorage;
import me.alegian.thaumcraft7.impl.common.item.ShardItem;
import me.alegian.thaumcraft7.impl.common.item.ThaumometerItem;
import me.alegian.thaumcraft7.impl.common.item.ThaumonomiconItem;
import me.alegian.thaumcraft7.impl.common.item.WandItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Items {
  public static final DeferredRegister.Items REGISTRAR = DeferredRegister.createItems(Thaumcraft.MODID);

  public static final DeferredItem<Item> IRON_CAP = REGISTRAR.registerSimpleItem("iron_cap", new Item.Properties());
  public static final DeferredItem<WandItem> IRON_WOOD_WAND = REGISTRAR.registerItem("iron_wood_wand", WandItem::new, new Item.Properties().stacksTo(1));
  public static final DeferredItem<ThaumometerItem> THAUMOMETER = REGISTRAR.registerItem("thaumometer", ThaumometerItem::new);
  public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = REGISTRAR.registerItem("thaumonomicon", ThaumonomiconItem::new);
  public static final DeferredItem<ArmorItem> GOGGLES = REGISTRAR.registerItem("goggles", (props) -> new ArmorItem(T7ArmorMaterials.GOGGLES, ArmorItem.Type.HELMET, props));

  public static final DeferredItem<ShardItem> IGNIS_SHARD = REGISTRAR.registerItem("ignis_shard", $ -> new ShardItem(Aspect.IGNIS));
  public static final DeferredItem<ShardItem> AER_SHARD = REGISTRAR.registerItem("aer_shard", $ -> new ShardItem(Aspect.AER));
  public static final DeferredItem<ShardItem> TERRA_SHARD = REGISTRAR.registerItem("terra_shard", $ -> new ShardItem(Aspect.TERRA));
  public static final DeferredItem<ShardItem> AQUA_SHARD = REGISTRAR.registerItem("aqua_shard", $ -> new ShardItem(Aspect.AQUA));
  public static final DeferredItem<ShardItem> ORDO_SHARD = REGISTRAR.registerItem("ordo_shard", $ -> new ShardItem(Aspect.ORDO));
  public static final DeferredItem<ShardItem> PERDITIO_SHARD = REGISTRAR.registerItem("perditio_shard", $ -> new ShardItem(Aspect.PERDITIO));

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerItem(T7Capabilities.VisStorage.ITEM, (itemStack, context) -> new VisStorage(50, itemStack), IRON_WOOD_WAND);
    event.registerItem(T7Capabilities.REVEALING, (itemStack, context) -> Unit.INSTANCE, GOGGLES);
  }

  // "using" predicate to switch item models
  // used in wand
  static {
    ItemProperties.registerGeneric(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "using"), (itemStack, clientLevel, livingEntity, seed) ->
        livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F : 0.0F
    );
  }
}
