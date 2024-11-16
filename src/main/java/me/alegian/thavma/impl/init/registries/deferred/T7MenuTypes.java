package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.menu.WorkbenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7MenuTypes {
  public static final DeferredRegister<MenuType<?>> REGISTRAR = DeferredRegister.create(Registries.MENU, Thavma.MODID);

  public static final DeferredHolder<MenuType<?>, MenuType<WorkbenchMenu>> ARCANE_WORKBENCH = T7MenuTypes.REGISTRAR.register("arcane_workbench", () -> new MenuType<>(WorkbenchMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
