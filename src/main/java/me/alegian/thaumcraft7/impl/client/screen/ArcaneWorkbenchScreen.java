package me.alegian.thaumcraft7.impl.client.screen;

import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.client.T7GuiGraphics;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import me.alegian.thaumcraft7.impl.common.menu.ArcaneWorkbenchMenu;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu> {
  private static final ResourceLocation BG_LOCATION = Thaumcraft.id("textures/gui/container/arcane_workbench.png");

  public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    renderAspects(pGuiGraphics);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    int i = this.leftPos;
    int j = (this.height - this.imageHeight) / 2;
    pGuiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
  }

  protected void renderAspects(GuiGraphics guiGraphics) {
    final int RADIUS = 45;
    var middleSlot = this.menu.slots.get(4);
    var t7graphics = new T7GuiGraphics(guiGraphics);

    t7graphics.push();
    t7graphics.translateXY(this.leftPos, this.topPos);
    t7graphics.translateXY(middleSlot.x, middleSlot.y);

    // draw aspects at hexagon points
    int i = 0;
    for (var a : Aspects.PRIMAL_ASPECTS) {
      t7graphics.push();
      t7graphics.rotateZ(60 * i);
      t7graphics.translateXY(RADIUS, 0);
      t7graphics.rotateZ(-60 * i);
      AspectRenderer.drawAspectIcon(t7graphics, a.get(), 0, 0);
      t7graphics.pop();
      i++;
    }

    t7graphics.pop();
  }
}
