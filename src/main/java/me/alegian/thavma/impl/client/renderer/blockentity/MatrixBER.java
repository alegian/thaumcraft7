package me.alegian.thavma.impl.client.renderer.blockentity;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.entity.MatrixBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class MatrixBER extends GeoBlockRenderer<MatrixBE> {
  public MatrixBER() {
    super(new DefaultedBlockGeoModel<>(Thavma.rl("infusion_matrix")));
    this.addRenderLayer(new AutoGlowingGeoLayer<>(this) {
      // TODO, see how to fix cloud bug by comparing EYES to rendertype_entity_translucent_emissive
      @Override
      protected RenderType getRenderType(MatrixBE animatable, @Nullable MultiBufferSource bufferSource) {
        return RenderType.eyes(AutoGlowingTexture.getEmissiveResource(this.getTextureResource(animatable)));
      }
    });
  }
}
