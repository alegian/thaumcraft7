package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.menu.ArcaneWorkbenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7MenuTypes {
  public static final DeferredRegister<MenuType<?>> REGISTRAR = DeferredRegister.create(Registries.MENU, Thaumcraft.MODID);

  public static final DeferredHolder<MenuType<?>, MenuType<ArcaneWorkbenchMenu>> ARCANE_WORKBENCH = REGISTRAR.register("arcane_workbench", () -> new MenuType<>(ArcaneWorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
