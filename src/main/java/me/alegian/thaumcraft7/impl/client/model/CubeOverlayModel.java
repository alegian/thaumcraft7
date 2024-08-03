package me.alegian.thaumcraft7.impl.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.impl.Thaumcraft;
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

import static me.alegian.thaumcraft7.impl.client.model.BakedModelHelper.quad;
import static me.alegian.thaumcraft7.impl.client.model.BakedModelHelper.v;

public class CubeOverlayModel implements IUnbakedGeometry<CubeOverlayModel> {
  public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "cube_overlay");
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
    BakedModel bakedBase = new ElementsModel(base.getElements()).bake(context, baker, spriteGetter, modelState, overrides);
    return new Baked(bakedBase, spriteLocation, color);
  }

  @Override
  public void resolveParents(@NotNull Function<ResourceLocation, UnbakedModel> modelGetter, @NotNull IGeometryBakingContext context) {
    base.resolveParents(modelGetter);
  }

  public static class Loader implements IGeometryLoader<CubeOverlayModel> {
    public static final Loader INSTANCE = new Loader();

    private Loader() {
    }

    @Override
    public @NotNull CubeOverlayModel read(JsonObject jsonObject, @NotNull JsonDeserializationContext context) throws JsonParseException {
      jsonObject.remove("loader");
      var spriteLocation = ResourceLocation.parse(jsonObject.get(SPRITE_KEY).getAsString());
      jsonObject.remove(SPRITE_KEY);
      var color = jsonObject.get(COLOR_KEY).getAsInt();
      jsonObject.remove(COLOR_KEY);
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
      return ChunkRenderTypeSet.of(RenderType.cutoutMipped(), RenderType.solid());
    }

    // used in item renderer
    @Override
    public @NotNull List<RenderType> getRenderTypes(@NotNull ItemStack itemStack, boolean fabulous) {
      return List.of(Sheets.cutoutBlockSheet());
    }

    // used in item renderer
    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext cameraTransformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
      originalModel.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
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
        quads.addAll(originalModel.getQuads(state, side, rand, extraData, renderType));

      if (renderType == null || renderType == RenderType.cutoutMipped() || renderType == Sheets.cutoutBlockSheet()) {
        var sprite = Minecraft.getInstance()
            .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
            .apply(spriteLocation);
        float offset = 0.001f; // avoid z-fighting
        float ONE = 1 + offset;
        float ZERO = 0 - offset;
        quads.add(quad(v(ZERO, ONE, ONE), v(ONE, ONE, ONE), v(ONE, ONE, ZERO), v(ZERO, ONE, ZERO), sprite, color));
        quads.add(quad(v(ZERO, ZERO, ZERO), v(ONE, ZERO, ZERO), v(ONE, ZERO, ONE), v(ZERO, ZERO, ONE), sprite, color));
        quads.add(quad(v(ONE, ZERO, ZERO), v(ONE, ONE, ZERO), v(ONE, ONE, ONE), v(ONE, ZERO, ONE), sprite, color));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ZERO, ONE, ONE), v(ZERO, ONE, ZERO), v(ZERO, ZERO, ZERO), sprite, color));
        quads.add(quad(v(ZERO, ONE, ZERO), v(ONE, ONE, ZERO), v(ONE, ZERO, ZERO), v(ZERO, ZERO, ZERO), sprite, color));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ONE, ZERO, ONE), v(ONE, ONE, ONE), v(ZERO, ONE, ONE), sprite, color));
      }
      return quads;
    }

    // used in item renderer
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
      return getQuads(state, side, rand, ModelData.EMPTY, null);
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
      if(spriteLocation == null) throw new IllegalStateException("Sprite location is required for CubeOverlayModel");
      if(color == null) throw new IllegalStateException("Color is required for CubeOverlayModel");
      json.add(SPRITE_KEY, new JsonPrimitive(spriteLocation.toString()));
      json.add(COLOR_KEY, new JsonPrimitive(color));
      return super.toJson(json);
    }
  }
}
