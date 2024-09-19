package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import me.alegian.thaumcraft7.impl.common.item.*;
import me.alegian.thaumcraft7.impl.init.registries.T7Tiers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
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
  public static final DeferredItem<Item> RESEARCH_SCROLL = REGISTRAR.registerSimpleItem("research_scroll", new Item.Properties().stacksTo(1));
  public static final DeferredItem<Item> COMPLETED_RESEARCH = REGISTRAR.registerSimpleItem("completed_research", new Item.Properties().stacksTo(1));

  public static final DeferredItem<SwordItem> ARCANUM_SWORD = REGISTRAR.register("arcanum_sword", () -> new SwordItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          SwordItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              3, -2.4f
          )
      )
  ));
  public static final DeferredItem<ShovelItem> ARCANUM_SHOVEL = REGISTRAR.register("arcanum_shovel", () -> new ShovelItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          ShovelItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              1.5F, -3.0F
          )
      )
  ));
  public static final DeferredItem<PickaxeItem> ARCANUM_PICKAXE = REGISTRAR.register("arcanum_pickaxe", () -> new PickaxeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          PickaxeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              1.0F, -2.8F
          )
      )
  ));
  public static final DeferredItem<AxeItem> ARCANUM_AXE = REGISTRAR.register("arcanum_axe", () -> new AxeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          AxeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              5.0F, -3.0F
          )
      )
  ));
  public static final DeferredItem<HoeItem> ARCANUM_HOE = REGISTRAR.register("arcanum_hoe", () -> new HoeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          HoeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              -3.0F, 0.0F
          )
      )
  ));
  public static final DeferredItem<KatanaItem> ARCANUM_KATANA = REGISTRAR.register("arcanum_katana", KatanaItem::new);

  public static final DeferredItem<WandItem> WAND = REGISTRAR.registerItem("wand", WandItem::new, new Item.Properties().stacksTo(1));
  public static final DeferredItem<ThaumometerItem> THAUMOMETER = REGISTRAR.registerItem("thaumometer", ThaumometerItem::new);
  public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = REGISTRAR.registerItem("thaumonomicon", ThaumonomiconItem::new);
  public static final DeferredItem<ArmorItem> GOGGLES = REGISTRAR.registerItem("goggles", GogglesItem::new);

  public static final DeferredItem<TestaItem> IGNIS_TESTA = REGISTRAR.registerItem("ignis_testa", $ -> new TestaItem(Aspect.IGNIS));
  public static final DeferredItem<TestaItem> AER_TESTA = REGISTRAR.registerItem("aer_testa", $ -> new TestaItem(Aspect.AER));
  public static final DeferredItem<TestaItem> TERRA_TESTA = REGISTRAR.registerItem("terra_testa", $ -> new TestaItem(Aspect.TERRA));
  public static final DeferredItem<TestaItem> AQUA_TESTA = REGISTRAR.registerItem("aqua_testa", $ -> new TestaItem(Aspect.AQUA));
  public static final DeferredItem<TestaItem> ORDO_TESTA = REGISTRAR.registerItem("ordo_testa", $ -> new TestaItem(Aspect.ORDO));
  public static final DeferredItem<TestaItem> PERDITIO_TESTA = REGISTRAR.registerItem("perditio_testa", $ -> new TestaItem(Aspect.PERDITIO));

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerItem(T7Capabilities.AspectContainer.ITEM, (itemStack, context) -> new AspectContainer(itemStack, 50), WAND);
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
