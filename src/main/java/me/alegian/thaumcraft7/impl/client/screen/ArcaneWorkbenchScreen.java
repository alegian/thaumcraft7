package me.alegian.thaumcraft7.impl.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import me.alegian.thaumcraft7.impl.client.T7GuiGraphics;
import me.alegian.thaumcraft7.impl.client.renderer.AspectRenderer;
import me.alegian.thaumcraft7.impl.client.texture.Texture;
import me.alegian.thaumcraft7.impl.common.menu.ArcaneWorkbenchMenu;
import me.alegian.thaumcraft7.impl.init.registries.deferred.Aspects;
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
public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchMenu> {
  private static final Texture WORKBENCH_BG = new Texture("gui/container/arcane_workbench", 216, 127, 255, 255);
  private static final Texture INVENTORY_BG = new Texture("gui/container/inventory", 176, 99, 255, 255);
  private static final Texture SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_slot", 18, 18);
  private static final Texture RESULT_SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_result_slot", 26, 26);
  private static final Texture ASPECT_SLOT_TEXTURE = new Texture("gui/container/arcane_workbench_aspect_slot", 20, 20);
  protected static final int PADDING = 2;
  protected static final int SLOT_SIZE = 16;

  public ArcaneWorkbenchScreen(ArcaneWorkbenchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
    this.inventoryLabelX += 20;
    this.inventoryLabelY += 62;
    this.titleLabelY -= 1;
    this.imageWidth = Math.max(WORKBENCH_BG.width(), INVENTORY_BG.width());
    this.imageHeight = WORKBENCH_BG.height() + PADDING + INVENTORY_BG.height();
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    renderAspects(pGuiGraphics);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    var t7graphics = new T7GuiGraphics(pGuiGraphics);
    t7graphics.push();
    t7graphics.translateXY(this.leftPos, this.topPos);

    t7graphics.blit(WORKBENCH_BG);
    t7graphics.translateXY((WORKBENCH_BG.width() - INVENTORY_BG.width()) / 2f, WORKBENCH_BG.height());
    t7graphics.translateXY(0, PADDING);
    t7graphics.blit(INVENTORY_BG);

    t7graphics.pop();

    for (var slot : this.menu.slots) {
      renderSlotBg(t7graphics, slot);
    }
  }

  protected void renderSlotBg(T7GuiGraphics t7graphics, Slot slot) {
    Texture texture = SLOT_TEXTURE;
    if (slot instanceof ResultSlot) texture = RESULT_SLOT_TEXTURE;

    t7graphics.push();
    // go to the center of the slot and draw the texture centered there
    t7graphics.translateXY(this.leftPos + slot.x + SLOT_SIZE / 2f, this.topPos + slot.y + SLOT_SIZE / 2f);
    t7graphics.translateXY(-texture.width() / 2f, -texture.height() / 2f);
    t7graphics.blit(texture);
    t7graphics.pop();
  }

  protected void renderAspects(GuiGraphics guiGraphics) {
    final int RADIUS = 48;
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
      Texture texture = ASPECT_SLOT_TEXTURE;
      t7graphics.blit(texture.location(), -2, -2, 0, 0, texture.width(), texture.height(), texture.width(), texture.height());
      AspectRenderer.drawAspectIcon(t7graphics, a.get(), 0, 0);
      t7graphics.pop();
      i++;
    }

    t7graphics.pop();
  }

  @Override
  protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemstack, Slot slot, @Nullable String countString) {
    super.renderSlotContents(guiGraphics, itemstack, slot, countString);
    //todo if insufficient vis
    if (slot instanceof ResultSlot)
      guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), slot.x, slot.y, slot.x + 16, slot.y + 16, 0x30FFFFFF);
  }
}
