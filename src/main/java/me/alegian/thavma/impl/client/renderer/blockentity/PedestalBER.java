package me.alegian.thavma.impl.client.renderer.blockentity;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.entity.PedestalBE;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class PedestalBER extends GeoBlockRenderer<PedestalBE> {
  public PedestalBER() {
    super(new DefaultedBlockGeoModel<>(Thavma.rl("infusion_pedestal")));
  }
}
