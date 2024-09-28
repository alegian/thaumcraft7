package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.common.aspect.Aspect;
import me.alegian.thaumcraft7.impl.init.registries.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import me.alegian.thaumcraft7.impl.common.item.*;
import me.alegian.thaumcraft7.impl.common.util.DoubleMap;
import me.alegian.thaumcraft7.impl.common.wand.WandCoreMaterial;
import me.alegian.thaumcraft7.impl.common.wand.WandHandleMaterial;
import me.alegian.thaumcraft7.impl.init.registries.T7Tiers;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandCoreMaterial;
import me.alegian.thaumcraft7.impl.init.registries.deferred.util.DeferredWandHandleMaterial;
import net.minecraft.util.Unit;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Items {
  public static final DeferredRegister.Items REGISTRAR = DeferredRegister.createItems(Thaumcraft.MODID);

  public static final DeferredItem<Item> IRON_HANDLE = REGISTRAR.registerSimpleItem("iron_handle", new Item.Properties());
  public static final DeferredItem<Item> GOLD_HANDLE = REGISTRAR.registerSimpleItem("gold_handle", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_HANDLE = REGISTRAR.registerSimpleItem("orichalcum_handle", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_HANDLE = REGISTRAR.registerSimpleItem("arcanum_handle", new Item.Properties());

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

  public static final DeferredItem<ThaumometerItem> THAUMOMETER = REGISTRAR.registerItem("thaumometer", ThaumometerItem::new);
  public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = REGISTRAR.registerItem("thaumonomicon", ThaumonomiconItem::new);
  public static final DeferredItem<ArmorItem> GOGGLES = REGISTRAR.registerItem("goggles", GogglesItem::new, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15)));
  public static final DeferredItem<ArmorItem> ARCANUM_HELMET = REGISTRAR.registerItem("arcanum_helmet", ArcanumHelmetItem::new, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_CHESTPLATE = REGISTRAR.registerItem("arcanum_chestplate", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.CHESTPLATE, props), new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_LEGGINGS = REGISTRAR.registerItem("arcanum_leggings", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.LEGGINGS, props), new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_BOOTS = REGISTRAR.registerItem("arcanum_boots", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.BOOTS, props), new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(21)));

  public static final DeferredItem<TestaItem> IGNIS_TESTA = REGISTRAR.registerItem("ignis_testa", $ -> new TestaItem(Aspect.IGNIS));
  public static final DeferredItem<TestaItem> AER_TESTA = REGISTRAR.registerItem("aer_testa", $ -> new TestaItem(Aspect.AER));
  public static final DeferredItem<TestaItem> TERRA_TESTA = REGISTRAR.registerItem("terra_testa", $ -> new TestaItem(Aspect.TERRA));
  public static final DeferredItem<TestaItem> AQUA_TESTA = REGISTRAR.registerItem("aqua_testa", $ -> new TestaItem(Aspect.AQUA));
  public static final DeferredItem<TestaItem> ORDO_TESTA = REGISTRAR.registerItem("ordo_testa", $ -> new TestaItem(Aspect.ORDO));
  public static final DeferredItem<TestaItem> PERDITIO_TESTA = REGISTRAR.registerItem("perditio_testa", $ -> new TestaItem(Aspect.PERDITIO));

  public static final DoubleMap<String, String, DeferredItem<WandItem>> WANDS = new DoubleMap<>();

  /*
    Registers all wand combinations of handle and core.
    WARNING: does not register wands with materials from addons,
    these have to be registered manually by each addon.
   */
  static {
    for (var handleMaterial : WandHandleMaterials.ALL) {
      for (var coreMaterial : WandCoreMaterials.ALL) {
        addWand(handleMaterial, coreMaterial);
      }
    }
  }

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    for (var wand : WANDS.values()) {
      event.registerItem(T7Capabilities.AspectContainer.ITEM, (itemStack, context) -> new AspectContainer(itemStack, wand.get().capacity()), wand);
    }
    event.registerItem(T7Capabilities.REVEALING, (itemStack, context) -> Unit.INSTANCE, GOGGLES);
  }

  /**
   * Registers a wand with the given handle and core materials
   */
  static void addWand(DeferredWandHandleMaterial<WandHandleMaterial> handleMaterial, DeferredWandCoreMaterial<WandCoreMaterial> coreMaterial) {
    String handleName = handleMaterial.getId().getPath();
    String coreName = coreMaterial.getId().getPath();
    String wandName = WandItem.name(handleMaterial, coreMaterial);
    WANDS.put(handleName, coreName, REGISTRAR.registerItem(wandName, (props) -> new WandItem(props, handleMaterial, coreMaterial)));
  }

  /**
   * Helper that gets a wand from the DoubleMap of registered wands.
   * WARNING: cannot get wands from addons, these have to be accessed manually.
   */
  public static DeferredItem<WandItem> wand(DeferredWandHandleMaterial<WandHandleMaterial> handleMaterial, DeferredWandCoreMaterial<WandCoreMaterial> coreMaterial) {
    String handleName = handleMaterial.getId().getPath();
    String coreName = coreMaterial.getId().getPath();
    var wand = WANDS.get(handleName, coreName);

    if (wand == null)
      throw new IllegalArgumentException("Thaumcraft Exception: Trying to Access Unregistered Wand Combination" + WandItem.name(handleMaterial, coreMaterial));

    return wand;
  }
}
