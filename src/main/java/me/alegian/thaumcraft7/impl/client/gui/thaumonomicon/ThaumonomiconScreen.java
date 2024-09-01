package me.alegian.thaumcraft7.impl.client.gui.thaumonomicon;

import me.alegian.thaumcraft7.impl.client.gui.thaumonomicon.widget.Frame;
import me.alegian.thaumcraft7.impl.client.gui.thaumonomicon.widget.Tab;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ThaumonomiconScreen extends Screen {
  private final Tab tab = new Tab(300, 300);
  private boolean isScrolling;

  public ThaumonomiconScreen() {
    super(Component.literal("Thaumonomicon"));
  }

  @Override
  protected void init() {
    super.init();
    this.addRenderableOnly(tab);
    this.addRenderableOnly(new Frame());
  }

  @Override
  public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
    if (button != 0) {
      this.isScrolling = false;
      return false;
    } else {
      if (!this.isScrolling) {
        this.isScrolling = true;
      } else {
        tab.handleScroll((float) dragX, (float) dragY);
      }

      return true;
    }
  }

  @Override
  public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
    tab.zoom((float) scrollY);
    return true;
  }
}

