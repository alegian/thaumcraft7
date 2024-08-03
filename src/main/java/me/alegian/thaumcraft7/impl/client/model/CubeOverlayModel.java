package me.alegian.thaumcraft7.impl.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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

public class CubeOverlayModel {
  public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "cube_overlay");

  public static class GeometryLoader implements IGeometryLoader<UnbakedGeometry> {
    public static final GeometryLoader INSTANCE = new GeometryLoader();

    private GeometryLoader() {
    }

    @Override
    public @NotNull UnbakedGeometry read(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
      // Trick the deserializer into thinking this is a normal model by removing the loader field and then passing it back into the deserializer.
      jsonObject.remove("loader");
      BlockModel base = context.deserialize(jsonObject, BlockModel.class);

      return new UnbakedGeometry(base);
    }
  }

  public static class UnbakedGeometry implements IUnbakedGeometry<UnbakedGeometry> {
    private final BlockModel base;

    public UnbakedGeometry(BlockModel base) {
      this.base = base;
    }

    @Override
    public @NotNull BakedModel bake(@NotNull IGeometryBakingContext context, @NotNull ModelBaker baker, @NotNull Function<Material, TextureAtlasSprite> spriteGetter, @NotNull ModelState modelState, @NotNull ItemOverrides overrides) {
      BakedModel bakedBase = new ElementsModel(base.getElements()).bake(context, baker, spriteGetter, modelState, overrides);
      return new DynamicBakedModel(bakedBase);
    }

    @Override
    public void resolveParents(@NotNull Function<ResourceLocation, UnbakedModel> modelGetter, @NotNull IGeometryBakingContext context) {
      base.resolveParents(modelGetter);
    }
  }

  // BakedModelWrapper can be used as well to return default values for most methods, allowing you to only override what actually needs to be overridden.
  public static class DynamicBakedModel extends BakedModelWrapper<BakedModel> implements IDynamicBakedModel {

    public DynamicBakedModel(BakedModel base) {
      super(base);
    }

    @Override
    public @NotNull ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      return ChunkRenderTypeSet.of(RenderType.cutoutMipped(), RenderType.solid());
    }

    @Override
    public @NotNull List<RenderType> getRenderTypes(@NotNull ItemStack itemStack, boolean fabulous) {
      return List.of(Sheets.cutoutBlockSheet());
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
      List<BakedQuad> quads = new ArrayList<>();

      if (renderType == null || renderType == RenderType.solid())
        quads.addAll(originalModel.getQuads(state, side, rand, extraData, renderType));

      if (renderType == null || renderType == RenderType.cutoutMipped() || renderType == Sheets.cutoutBlockSheet()) {
        var sprite = Minecraft.getInstance()
            .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
            .apply(ResourceLocation.fromNamespaceAndPath(Thaumcraft.MODID, "block/crystal_ore"));
        float offset = 0.001f; // avoid z-fighting
        float ONE = 1 + offset;
        float ZERO = 0 - offset;
        quads.add(quad(v(ZERO, ONE, ONE), v(ONE, ONE, ONE), v(ONE, ONE, ZERO), v(ZERO, ONE, ZERO), sprite));
        quads.add(quad(v(ZERO, ZERO, ZERO), v(ONE, ZERO, ZERO), v(ONE, ZERO, ONE), v(ZERO, ZERO, ONE), sprite));
        quads.add(quad(v(ONE, ZERO, ZERO), v(ONE, ONE, ZERO), v(ONE, ONE, ONE), v(ONE, ZERO, ONE), sprite));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ZERO, ONE, ONE), v(ZERO, ONE, ZERO), v(ZERO, ZERO, ZERO), sprite));
        quads.add(quad(v(ZERO, ONE, ZERO), v(ONE, ONE, ZERO), v(ONE, ZERO, ZERO), v(ZERO, ZERO, ZERO), sprite));
        quads.add(quad(v(ZERO, ZERO, ONE), v(ONE, ZERO, ONE), v(ONE, ONE, ONE), v(ZERO, ONE, ONE), sprite));
      }
      return quads;
    }
  }

  public static class LoaderBuilder<B extends ModelBuilder<B>> extends CustomLoaderBuilder<B> {
    public LoaderBuilder(B parent, ExistingFileHelper existingFileHelper) {
      super(
          ID,
          parent,
          existingFileHelper,
          false
      );
    }

    @Override
    public @NotNull JsonObject toJson(@NotNull JsonObject json) {
      // TODO: add fields to the given JsonObject
      return super.toJson(json);
    }
  }
}
