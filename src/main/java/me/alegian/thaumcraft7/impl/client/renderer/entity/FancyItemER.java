package me.alegian.thaumcraft7.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.alegian.thaumcraft7.impl.client.renderer.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.entity.ItemEntityRenderer.getSeedForItemStack;
import static net.minecraft.client.renderer.entity.ItemEntityRenderer.renderMultipleFromCount;

@OnlyIn(Dist.CLIENT)
public class FancyItemER extends EntityRenderer<ItemEntity> {
  private final RandomSource random = RandomSource.create();

  public FancyItemER(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(ItemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    // WARNING: the two render method's poses are coupled
    pPoseStack.pushPose();
    renderItem(pEntity, pPoseStack, pBuffer, pPartialTicks, pPackedLight);
    renderEnderDragonRays(pEntity, pPoseStack, pBuffer, pPartialTicks);
    pPoseStack.popPose();

    super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
  }

  private void renderItem(ItemEntity pEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, float pPartialTicks, int pPackedLight) {
    var itemRenderer = Minecraft.getInstance().getItemRenderer();
    ItemStack itemstack = pEntity.getItem();
    this.random.setSeed(getSeedForItemStack(itemstack));
    BakedModel bakedmodel = itemRenderer.getModel(itemstack, pEntity.level(), null, pEntity.getId());
    boolean flag = bakedmodel.isGui3d();
    boolean shouldBob = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(itemstack).shouldBobAsEntity(itemstack);
    float f1 = shouldBob ? Mth.sin(((float) pEntity.getAge() + pPartialTicks) / 10.0F + pEntity.bobOffs) * 0.1F + 0.1F : 0;
    float f2 = ItemTransforms.NO_TRANSFORMS.getTransform(ItemDisplayContext.GROUND).scale.y();
    pPoseStack.translate(0.0F, f1 + 0.25F * f2, 0.0F);
    var angle = RenderHelper.calculatePlayerAngle(pEntity.getEyePosition());
    pPoseStack.mulPose(Axis.YP.rotation(angle));
    renderMultipleFromCount(itemRenderer, pPoseStack, pBuffer, pPackedLight, itemstack, bakedmodel, flag, this.random);
    pPoseStack.translate(0, 0, -.5f);
    pPoseStack.mulPose(Axis.YP.rotation(-angle));
  }

  private void renderEnderDragonRays(ItemEntity pEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, float pPartialTicks) {
    float f3 = pEntity.getSpin(pPartialTicks);
    pPoseStack.mulPose(Axis.YP.rotation(f3));
    pPoseStack.scale(.1f, .1f, .1f);
    EnderDragonRenderer.renderRays(pPoseStack, 0.7f, pBuffer.getBuffer(RenderType.dragonRays()));
    EnderDragonRenderer.renderRays(pPoseStack, 0.7f, pBuffer.getBuffer(RenderType.dragonRaysDepth()));
  }

  @Override
  public ResourceLocation getTextureLocation(ItemEntity pEntity) {
    return InventoryMenu.BLOCK_ATLAS;
  }
}
