package me.alegian.thavma.impl.client.renderer.blockentity;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.client.renderer.geo.layer.EmissiveGeoLayer;
import me.alegian.thavma.impl.common.block.entity.PillarBE;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class PillarBER extends GeoBlockRenderer<PillarBE> {
  public PillarBER() {
    super(new DefaultedBlockGeoModel<>(Thavma.Companion.rl("infusion_pillar")));
    this.addRenderLayer(new EmissiveGeoLayer<>(this));
  }
}
