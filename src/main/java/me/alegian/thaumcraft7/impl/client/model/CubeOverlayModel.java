package me.alegian.thaumcraft7.impl.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.ElementsModel;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.CustomLoaderBuilder;
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
      return new DynamicBakedModel(bakedBase, context.useAmbientOcclusion(), context.isGui3d(), context.useBlockLight(),
          spriteGetter.apply(context.getMaterial("particle")), overrides);
    }

    @Override
    public void resolveParents(@NotNull Function<ResourceLocation, UnbakedModel> modelGetter, @NotNull IGeometryBakingContext context) {
      base.resolveParents(modelGetter);
    }
  }

  // BakedModelWrapper can be used as well to return default values for most methods, allowing you to only override what actually needs to be overridden.
  public static class DynamicBakedModel implements IDynamicBakedModel {
    private final BakedModel base;
    private final boolean useAmbientOcclusion;
    private final boolean isGui3d;
    private final boolean usesBlockLight;
    private final TextureAtlasSprite particle;
    private final ItemOverrides overrides;

    public DynamicBakedModel(BakedModel base, boolean useAmbientOcclusion, boolean isGui3d, boolean usesBlockLight, TextureAtlasSprite particle, ItemOverrides overrides) {
      this.base = base;
      this.useAmbientOcclusion = useAmbientOcclusion;
      this.isGui3d = isGui3d;
      this.usesBlockLight = usesBlockLight;
      this.particle = particle;
      this.overrides = overrides;
    }

    // Use our attributes. Refer to the article on baked models for more information on the method's effects.
    @Override
    public boolean useAmbientOcclusion() {
      return useAmbientOcclusion;
    }

    @Override
    public boolean isGui3d() {
      return isGui3d;
    }

    @Override
    public boolean usesBlockLight() {
      return usesBlockLight;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
      // Return MISSING_TEXTURE.sprite() if you don't need a particle, e.g. when in an item model context.
      return particle;
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
      // Return ItemOverrides.EMPTY when in a block model context.
      return overrides;
    }

    // Override this to true if you want to use a custom block entity renderer instead of the default renderer.
    @Override
    public boolean isCustomRenderer() {
      return false;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
      List<BakedQuad> quads = new ArrayList<>(base.getQuads(state, side, rand, extraData, renderType));
      var sprite = Minecraft.getInstance()
          .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
          .apply(ResourceLocation.withDefaultNamespace("block/netherrack"));
      quads.add(quad(v(0, 1, 1), v(1, 1, 1), v(1, 1, 0), v(0, 1, 0), sprite));
      return quads;
    }

    // Apply the base model's transforms to our model as well.
    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
      return base.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }
  }

  public static class LoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder> {
    public LoaderBuilder(BlockModelBuilder parent, ExistingFileHelper existingFileHelper) {
      super(
          ID,
          parent,
          existingFileHelper,
          false
      );
    }

    public BlockModelBuilder parent(ModelFile parent) {
      return this.parent.parent(parent);
    }

    @Override
    public @NotNull JsonObject toJson(@NotNull JsonObject json) {
      // TODO: add fields to the given JsonObject
      return super.toJson(json);
    }
  }
}
