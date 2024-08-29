package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import me.alegian.thaumcraft7.impl.common.item.TestaItem;
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

  public static final DeferredItem<Item> IRON_HANDLE = REGISTRAR.registerSimpleItem("iron_handle", new Item.Properties());
  public static final DeferredItem<Item> RUNE = REGISTRAR.registerSimpleItem("rune", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_INGOT = REGISTRAR.registerSimpleItem("arcanum_ingot", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_NUGGET = REGISTRAR.registerSimpleItem("arcanum_nugget", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_INGOT = REGISTRAR.registerSimpleItem("orichalcum_ingot", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_NUGGET = REGISTRAR.registerSimpleItem("orichalcum_nugget", new Item.Properties());

  public static final DeferredItem<WandItem> IRON_WOOD_WAND = REGISTRAR.registerItem("iron_wood_wand", WandItem::new, new Item.Properties().stacksTo(1));
  public static final DeferredItem<ThaumometerItem> THAUMOMETER = REGISTRAR.registerItem("thaumometer", ThaumometerItem::new);
  public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = REGISTRAR.registerItem("thaumonomicon", ThaumonomiconItem::new);
  public static final DeferredItem<ArmorItem> GOGGLES = REGISTRAR.registerItem("goggles", (props) -> new ArmorItem(T7ArmorMaterials.GOGGLES, ArmorItem.Type.HELMET, props));

  public static final DeferredItem<TestaItem> IGNIS_TESTA = REGISTRAR.registerItem("ignis_testa", $ -> new TestaItem(Aspect.IGNIS));
  public static final DeferredItem<TestaItem> AER_TESTA = REGISTRAR.registerItem("aer_testa", $ -> new TestaItem(Aspect.AER));
  public static final DeferredItem<TestaItem> TERRA_TESTA = REGISTRAR.registerItem("terra_testa", $ -> new TestaItem(Aspect.TERRA));
  public static final DeferredItem<TestaItem> AQUA_TESTA = REGISTRAR.registerItem("aqua_testa", $ -> new TestaItem(Aspect.AQUA));
  public static final DeferredItem<TestaItem> ORDO_TESTA = REGISTRAR.registerItem("ordo_testa", $ -> new TestaItem(Aspect.ORDO));
  public static final DeferredItem<TestaItem> PERDITIO_TESTA = REGISTRAR.registerItem("perditio_testa", $ -> new TestaItem(Aspect.PERDITIO));

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerItem(T7Capabilities.AspectContainer.ITEM, (itemStack, context) -> new AspectContainer(itemStack, 50), IRON_WOOD_WAND);
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
