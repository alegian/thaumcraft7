package me.alegian.thavma.impl.client.screen;

import me.alegian.thavma.impl.client.T7GuiGraphics;
import me.alegian.thavma.impl.client.renderer.AspectRenderer;
import me.alegian.thavma.impl.client.texture.Texture;
import me.alegian.thavma.impl.common.aspect.AspectStack;
import me.alegian.thavma.impl.common.menu.WorkbenchMenu;
import me.alegian.thavma.impl.init.registries.deferred.Aspects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
  protected static final int PADDING = 2;
  protected static final int SLOT_SIZE = 16;
  private static final Texture WORKBENCH_BG = new Texture("gui/container/arcane_workbench", 216, 134, 255, 255);
  private static final Texture INVENTORY_BG = new Texture("gui/container/inventory", 176, 99, 255, 255);
  private static final Texture SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_slot", 18, 18);
  private static final Texture RESULT_SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_result_slot", 34, 34);
  private static final Texture ASPECT_SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_aspect_slot", 25, 25);

  public WorkbenchScreen(WorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
    this.inventoryLabelX += 20;
    this.inventoryLabelY += 69;
    this.titleLabelY -= 15;
    this.imageWidth = Math.max(WorkbenchScreen.WORKBENCH_BG.width(), WorkbenchScreen.INVENTORY_BG.width());
    this.imageHeight = WorkbenchScreen.WORKBENCH_BG.height() + WorkbenchScreen.PADDING + WorkbenchScreen.INVENTORY_BG.height();
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    this.renderAspects(pGuiGraphics);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x83FF9B, false);
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    var t7graphics = new T7GuiGraphics(pGuiGraphics);
    t7graphics.push();
    t7graphics.translateXY(this.leftPos, this.topPos);

    t7graphics.blit(WorkbenchScreen.WORKBENCH_BG);
    t7graphics.translateXY((WorkbenchScreen.WORKBENCH_BG.width() - WorkbenchScreen.INVENTORY_BG.width()) / 2f, WorkbenchScreen.WORKBENCH_BG.height());
    t7graphics.translateXY(0, WorkbenchScreen.PADDING);
    t7graphics.blit(WorkbenchScreen.INVENTORY_BG);

    t7graphics.pop();

    for (var slot : this.menu.slots) this.renderSlotBg(t7graphics, slot);
  }

  @Override
  protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemstack, Slot slot, @Nullable String countString) {
    super.renderSlotContents(guiGraphics, itemstack, slot, countString);
    if (slot instanceof ResultSlot resultSlot && !resultSlot.mayPickup(this.menu.getPlayer()))
      guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), slot.x, slot.y, slot.x + 16, slot.y + 16, 0x50FFFFFF);
  }

  protected void renderSlotBg(T7GuiGraphics t7graphics, Slot slot) {
    Texture texture = WorkbenchScreen.SLOT_TEXTURE;
    if (slot instanceof ResultSlot) texture = WorkbenchScreen.RESULT_SLOT_TEXTURE;

    t7graphics.push();
    // go to the center of the slot and draw the texture centered there
    t7graphics.translateXY(this.leftPos + slot.x + WorkbenchScreen.SLOT_SIZE / 2f, this.topPos + slot.y + WorkbenchScreen.SLOT_SIZE / 2f);
    t7graphics.translateXY(-texture.width() / 2f, -texture.height() / 2f);
    t7graphics.blit(texture);
    t7graphics.pop();
  }

  protected void renderAspects(GuiGraphics guiGraphics) {
    final int BASE_RADIUS = 52;
    final float ANGLE = 360f / Aspects.INSTANCE.getPRIMAL_ASPECTS().size();
    var middleSlot = this.menu.slots.get(4);
    var t7graphics = new T7GuiGraphics(guiGraphics);

    t7graphics.push();
    t7graphics.translateXY(this.leftPos, this.topPos);
    t7graphics.translateXY(middleSlot.x, middleSlot.y);

    // draw aspects at hexagon points (or N-gon if more primals are added by addons)
    int i = 0;
    for (var a : Aspects.INSTANCE.getPRIMAL_ASPECTS()) {
      var requiredAmount = this.menu.getRequiredAspects().get(a.get());
      var requiredStack = new AspectStack(a.get(), requiredAmount);
      t7graphics.push();
      t7graphics.rotateZ(ANGLE * i);
      var fac = Math.abs(Math.cos(2*Math.PI / Aspects.INSTANCE.getPRIMAL_ASPECTS().size() * i));
      t7graphics.translateXY((float) (BASE_RADIUS * (1-0.16*fac*fac)), 0);
      t7graphics.rotateZ(-ANGLE * i);
      Texture texture = WorkbenchScreen.ASPECT_SLOT_TEXTURE;
      t7graphics.blit(texture.location(), (SLOT_TEXTURE.width()-ASPECT_SLOT_TEXTURE.width())/2, (SLOT_TEXTURE.height()-ASPECT_SLOT_TEXTURE.height())/2, 0, 0, texture.width(), texture.height(), texture.width(), texture.height());
      if (requiredAmount != 0)
        AspectRenderer.renderAspect(t7graphics, requiredStack, 0, 0);
      t7graphics.pop();
      i++;
    }

    t7graphics.pop();
  }
}
