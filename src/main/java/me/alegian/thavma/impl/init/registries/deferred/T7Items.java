package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.common.item.*;
import me.alegian.thavma.impl.common.util.DoubleMap;
import me.alegian.thavma.impl.common.wand.WandCoreMaterial;
import me.alegian.thavma.impl.common.wand.WandHandleMaterial;
import me.alegian.thavma.impl.init.registries.T7Capabilities;
import me.alegian.thavma.impl.init.registries.T7Tiers;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7Items {
  public static final DeferredRegister.Items REGISTRAR = DeferredRegister.createItems(Thavma.MODID);

  public static final DeferredItem<Item> IRON_HANDLE = T7Items.REGISTRAR.registerSimpleItem("iron_handle", new Item.Properties());
  public static final DeferredItem<Item> GOLD_HANDLE = T7Items.REGISTRAR.registerSimpleItem("gold_handle", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_HANDLE = T7Items.REGISTRAR.registerSimpleItem("orichalcum_handle", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_HANDLE = T7Items.REGISTRAR.registerSimpleItem("arcanum_handle", new Item.Properties());

  public static final DeferredItem<Item> EYE_OF_WARDEN = T7Items.REGISTRAR.registerSimpleItem("eye_of_warden", new Item.Properties().rarity(Rarity.EPIC));

  public static final DeferredItem<Item> GREATWOOD_CORE = T7Items.REGISTRAR.registerSimpleItem("greatwood_core", new Item.Properties());
  public static final DeferredItem<Item> SILVERWOOD_CORE = T7Items.REGISTRAR.registerSimpleItem("silverwood_core", new Item.Properties());

  public static final DeferredItem<Item> RUNE = T7Items.REGISTRAR.registerSimpleItem("rune", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_INGOT = T7Items.REGISTRAR.registerSimpleItem("arcanum_ingot", new Item.Properties());
  public static final DeferredItem<Item> ARCANUM_NUGGET = T7Items.REGISTRAR.registerSimpleItem("arcanum_nugget", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_INGOT = T7Items.REGISTRAR.registerSimpleItem("orichalcum_ingot", new Item.Properties());
  public static final DeferredItem<Item> ORICHALCUM_NUGGET = T7Items.REGISTRAR.registerSimpleItem("orichalcum_nugget", new Item.Properties());
  public static final DeferredItem<Item> RESEARCH_SCROLL = T7Items.REGISTRAR.registerSimpleItem("research_scroll", new Item.Properties().stacksTo(1));
  public static final DeferredItem<Item> COMPLETED_RESEARCH = T7Items.REGISTRAR.registerSimpleItem("completed_research", new Item.Properties().stacksTo(1));

  public static final DeferredItem<SwordItem> ARCANUM_SWORD = T7Items.REGISTRAR.register("arcanum_sword", () -> new SwordItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          SwordItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              3, -2.4f
          )
      )
  ));
  public static final DeferredItem<ShovelItem> ARCANUM_SHOVEL = T7Items.REGISTRAR.register("arcanum_shovel", () -> new ShovelItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          ShovelItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              1.5F, -3.0F
          )
      )
  ));
  public static final DeferredItem<PickaxeItem> ARCANUM_PICKAXE = T7Items.REGISTRAR.register("arcanum_pickaxe", () -> new PickaxeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          PickaxeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              1.0F, -2.8F
          )
      )
  ));
  public static final DeferredItem<HammerItem> ARCANUM_HAMMER = T7Items.REGISTRAR.register("arcanum_hammer", () -> new HammerItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          HammerItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              4.0F, -3.0F
          )
      )
  ));
  public static final DeferredItem<AxeItem> ARCANUM_AXE = T7Items.REGISTRAR.register("arcanum_axe", () -> new AxeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          AxeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              5.0F, -3.0F
          )
      )
  ));
  public static final DeferredItem<HoeItem> ARCANUM_HOE = T7Items.REGISTRAR.register("arcanum_hoe", () -> new HoeItem(
      T7Tiers.ARCANUM_TIER,
      new Item.Properties().attributes(
          HoeItem.createAttributes(
              T7Tiers.ARCANUM_TIER,
              -3.0F, 0.0F
          )
      )
  ));
  public static final DeferredItem<KatanaItem> ARCANUM_KATANA = T7Items.REGISTRAR.register("arcanum_katana", KatanaItem::new);
  public static final DeferredItem<ZephyrItem> ZEPHYR = T7Items.REGISTRAR.register("zephyr", ZephyrItem::new);

  public static final DeferredItem<OculusItem> OCULUS = T7Items.REGISTRAR.registerItem("oculus", OculusItem::new);
  public static final DeferredItem<ThaumonomiconItem> THAUMONOMICON = T7Items.REGISTRAR.registerItem("thaumonomicon", ThaumonomiconItem::new);

  public static final DeferredItem<GogglesItem> GOGGLES = T7Items.REGISTRAR.registerItem("goggles", GogglesItem::new);
  public static final DeferredItem<ResearcherArmorItem> RESEARCHER_CHESTPLATE = T7Items.REGISTRAR.registerItem("researcher_chestplate", props -> new ResearcherArmorItem(ArmorItem.Type.CHESTPLATE, props), new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(21)));
  public static final DeferredItem<DawnCharmItem> DAWN_CHARM = T7Items.REGISTRAR.registerItem("charm_of_the_dawn", DawnCharmItem::new);
  public static final DeferredItem<ResearcherArmorItem> RESEARCHER_LEGGINGS = T7Items.REGISTRAR.registerItem("researcher_leggings", props -> new ResearcherArmorItem(ArmorItem.Type.LEGGINGS, props), new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(21)));
  public static final DeferredItem<ResearcherArmorItem> RESEARCHER_BOOTS = T7Items.REGISTRAR.registerItem("researcher_boots", props -> new ResearcherArmorItem(ArmorItem.Type.BOOTS, props), new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_HELMET = T7Items.REGISTRAR.registerItem("arcanum_helmet", ArcanumHelmetItem::new, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_CHESTPLATE = T7Items.REGISTRAR.registerItem("arcanum_chestplate", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.CHESTPLATE, props), new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_LEGGINGS = T7Items.REGISTRAR.registerItem("arcanum_leggings", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.LEGGINGS, props), new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(21)));
  public static final DeferredItem<ArmorItem> ARCANUM_BOOTS = T7Items.REGISTRAR.registerItem("arcanum_boots", props -> new ArmorItem(T7ArmorMaterials.ARCANUM, ArmorItem.Type.BOOTS, props), new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(21)));
  public static final DeferredItem<CustosArcanumArmorItem> CUSTOS_ARCANUM_HELMET = T7Items.REGISTRAR.registerItem("custos_arcanum_helmet", props -> new CustosArcanumArmorItem(ArmorItem.Type.HELMET, props), new Item.Properties().fireResistant().durability(ArmorItem.Type.HELMET.getDurability(21)));
  public static final DeferredItem<CustosArcanumArmorItem> CUSTOS_ARCANUM_CHESTPLATE = T7Items.REGISTRAR.registerItem("custos_arcanum_chestplate", props -> new CustosArcanumArmorItem(ArmorItem.Type.CHESTPLATE, props), new Item.Properties().fireResistant().durability(ArmorItem.Type.CHESTPLATE.getDurability(21)));
  public static final DeferredItem<CustosArcanumArmorItem> CUSTOS_ARCANUM_LEGGINGS = T7Items.REGISTRAR.registerItem("custos_arcanum_leggings", props -> new CustosArcanumArmorItem(ArmorItem.Type.LEGGINGS, props), new Item.Properties().fireResistant().durability(ArmorItem.Type.LEGGINGS.getDurability(21)));
  public static final DeferredItem<CustosArcanumArmorItem> CUSTOS_ARCANUM_BOOTS = T7Items.REGISTRAR.registerItem("custos_arcanum_boots", props -> new CustosArcanumArmorItem(ArmorItem.Type.BOOTS, props), new Item.Properties().fireResistant().durability(ArmorItem.Type.BOOTS.getDurability(21)));
  public static final DeferredItem<TestaItem> IGNIS_TESTA = T7Items.REGISTRAR.registerItem("ignis_testa", $ -> new TestaItem(Aspects.IGNIS));
  public static final DeferredItem<TestaItem> AER_TESTA = T7Items.REGISTRAR.registerItem("aer_testa", $ -> new TestaItem(Aspects.AER));
  public static final DeferredItem<TestaItem> TERRA_TESTA = T7Items.REGISTRAR.registerItem("terra_testa", $ -> new TestaItem(Aspects.TERRA));
  public static final DeferredItem<TestaItem> AQUA_TESTA = T7Items.REGISTRAR.registerItem("aqua_testa", $ -> new TestaItem(Aspects.AQUA));
  public static final DeferredItem<TestaItem> ORDO_TESTA = T7Items.REGISTRAR.registerItem("ordo_testa", $ -> new TestaItem(Aspects.ORDO));
  public static final DeferredItem<TestaItem> PERDITIO_TESTA = T7Items.REGISTRAR.registerItem("perditio_testa", $ -> new TestaItem(Aspects.PERDITIO));
  // (handleName, coreName)->wand. populated on Item Registry bake
  public static final DoubleMap<String, String, WandItem> WANDS = new DoubleMap<>();

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    for (var wand : T7Items.WANDS.values())
      event.registerItem(T7Capabilities.AspectContainer.ITEM, (itemStack, context) -> new AspectContainer(itemStack, wand.capacity()), wand);
  }

  /**
   * Registers a wand with the given handle and core materials
   */
  public static void registerWand(Registry<Item> registry, WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    String handleName = handleMaterial.getRegisteredName();
    String coreName = coreMaterial.getRegisteredName();
    String wandName = WandItem.name(handleMaterial, coreMaterial);

    var newWand = new WandItem(new Item.Properties(), handleMaterial, coreMaterial);
    Registry.register(registry, Thavma.rl(wandName), newWand);
    T7Items.WANDS.put(handleName, coreName, newWand);
  }

  /**
   * Helper that gets a wand from the DoubleMap of registered wands.
   * WARNING: cannot get wands from addons, these have to be accessed manually.
   */
  public static WandItem wandOrThrow(WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    String handleName = handleMaterial.getRegisteredName();
    String coreName = coreMaterial.getRegisteredName();
    var wand = T7Items.WANDS.get(handleName, coreName);

    if (wand == null)
      throw new IllegalArgumentException("Thavma Exception: Trying to Access Unregistered Wand Combination" + WandItem.name(handleMaterial, coreMaterial));

    return wand;
  }

  public static boolean isWandRegistered(WandHandleMaterial handleMaterial, WandCoreMaterial coreMaterial) {
    String handleName = handleMaterial.getRegisteredName();
    String coreName = coreMaterial.getRegisteredName();
    return T7Items.WANDS.get(handleName, coreName) != null;
  }


}
