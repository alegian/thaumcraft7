package me.alegian.thaumcraft7.impl.client.screen;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.menu.ArcaneWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu> {
  private static final ResourceLocation BG_LOCATION = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "textures/gui/container/arcane_workbench.png");

  public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    int i = this.leftPos;
    int j = (this.height - this.imageHeight) / 2;
    pGuiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
  }
}
