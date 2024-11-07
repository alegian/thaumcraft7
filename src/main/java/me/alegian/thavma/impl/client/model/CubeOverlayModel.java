package me.alegian.thavma.impl.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thavma.impl.Thavma;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.ElementsModel;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static me.alegian.thavma.impl.client.model.BakedModelHelper.quad;
import static me.alegian.thavma.impl.client.model.BakedModelHelper.v;

/**
 * A custom model loader that adds a cuboid cutout overlay to a block model.
 * Used for infused stone cracks
 */
@OnlyIn(Dist.CLIENT)
public class CubeOverlayModel implements IUnbakedGeometry<CubeOverlayModel> {
  public static final ResourceLocation ID = Thavma.id("cube_overlay");
  public static final String SPRITE_KEY = "sprite_location";
  public static final String COLOR_KEY = "color";
  private final BlockModel base;
  private final ResourceLocation spriteLocation;
  private final int color;

  public CubeOverlayModel(BlockModel base, ResourceLocation spriteLocation, int color) {
    this.base = base;
    this.spriteLocation = spriteLocation;
    this.color = color;
  }

  @Override
  public @NotNull BakedModel bake(@NotNull IGeometryBakingContext context, @NotNull ModelBaker baker, @NotNull Function<Material, TextureAtlasSprite> spriteGetter, @NotNull ModelState modelState, @NotNull ItemOverrides overrides) {
    BakedModel bakedBase = new ElementsModel(this.base.getElements()).bake(context, baker, spriteGetter, modelState, overrides);
    return new Baked(bakedBase, this.spriteLocation, this.color);
  }

  @Override
  public void resolveParents(@NotNull Function<ResourceLocation, UnbakedModel> modelGetter, @NotNull IGeometryBakingContext context) {
    this.base.resolveParents(modelGetter);
  }

  public static class Loader implements IGeometryLoader<CubeOverlayModel> {
    public static final Loader INSTANCE = new Loader();

    private Loader() {
    }

    @Override
    public @NotNull CubeOverlayModel read(JsonObject jsonObject, @NotNull JsonDeserializationContext context) throws JsonParseException {
      jsonObject.remove("loader");
      var spriteLocation = ResourceLocation.parse(jsonObject.get(CubeOverlayModel.SPRITE_KEY).getAsString());
      jsonObject.remove(CubeOverlayModel.SPRITE_KEY);
      var color = jsonObject.get(CubeOverlayModel.COLOR_KEY).getAsInt();
      jsonObject.remove(CubeOverlayModel.COLOR_KEY);
      BlockModel base = context.deserialize(jsonObject, BlockModel.class);

      return new CubeOverlayModel(base, spriteLocation, color);
    }
  }

  public static class Baked extends BakedModelWrapper<BakedModel> implements IDynamicBakedModel {
    private final ResourceLocation spriteLocation;
    private final int color;

    public Baked(BakedModel base, ResourceLocation spriteLocation, int color) {
      super(base);
      this.spriteLocation = spriteLocation;
      this.color = color;
    }

    // used in block renderer
    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      return ChunkRenderTypeSet.of(RenderType.cutout(), RenderType.solid());
    }

    // used in item renderer
    @Override
    public @NotNull List<RenderType> getRenderTypes(@NotNull ItemStack itemStack, boolean fabulous) {
      return List.of(Sheets.cutoutBlockSheet());
    }

    // used in item renderer
    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext cameraTransformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
      this.originalModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
      return this;
    }

    // used in item renderer
    @Override
    public @NotNull List<BakedModel> getRenderPasses(@NotNull ItemStack itemStack, boolean fabulous) {
      return List.of(this);
    }

    // used in block renderer
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
      List<BakedQuad> quads = new ArrayList<>();

      if (renderType == null || renderType == RenderType.solid())
        quads.addAll(this.originalModel.getQuads(state, side, rand, extraData, renderType));

      if (renderType == null || renderType == RenderType.cutout() || renderType == Sheets.cutoutBlockSheet()) {
        var sprite = Minecraft.getInstance()
            .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
            .apply(this.spriteLocation);
        float offset = 0.001f; // avoid z-fighting
        float ONE = 1 + offset;
        float ZERO = 0 - offset;
        quads.add(quad(v(ZERO, ONE, ONE), v(ONE, ONE, ONE), v(ONE, ONE, ZERO), v(ZERO, ONE, ZERO), sprite, this.color));
        quads.add(quad(v(ZERO, ZERO, ZERO), v(ONE, ZERO, ZERO), v(ONE, ZERO, ONE), v(ZERO, ZERO, ONE), sprite, this.color));
        quads.add(quad(v(ONE, ZERO, ZERO), v(ONE, ONE, ZERO), v(ONE, ONE, ONE), v(ONE, ZERO, ONE), sprite, this.color));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ZERO, ONE, ONE), v(ZERO, ONE, ZERO), v(ZERO, ZERO, ZERO), sprite, this.color));
        quads.add(quad(v(ZERO, ONE, ZERO), v(ONE, ONE, ZERO), v(ONE, ZERO, ZERO), v(ZERO, ZERO, ZERO), sprite, this.color));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ONE, ZERO, ONE), v(ONE, ONE, ONE), v(ZERO, ONE, ONE), sprite, this.color));
      }
      return quads;
    }

    // used in item renderer
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
      return this.getQuads(state, side, rand, ModelData.EMPTY, null);
    }
  }

  public static class Builder<B extends ModelBuilder<B>> extends CustomLoaderBuilder<B> {
    private ResourceLocation spriteLocation;
    private Integer color;

    public Builder(B parent, ExistingFileHelper existingFileHelper) {
      super(
          CubeOverlayModel.ID,
          parent,
          existingFileHelper,
          false
      );
    }

    public Builder<B> spriteLocation(ResourceLocation spriteLocation) {
      this.spriteLocation = spriteLocation;
      return this;
    }

    public Builder<B> color(int color) {
      this.color = color;
      return this;
    }

    @Override
    public @NotNull JsonObject toJson(@NotNull JsonObject json) {
      if (this.spriteLocation == null)
        throw new IllegalStateException("Sprite location is required for CubeOverlayModel");
      if (this.color == null) throw new IllegalStateException("Color is required for CubeOverlayModel");
      json.add(CubeOverlayModel.SPRITE_KEY, new JsonPrimitive(this.spriteLocation.toString()));
      json.add(CubeOverlayModel.COLOR_KEY, new JsonPrimitive(this.color));
      return super.toJson(json);
    }
  }
}
