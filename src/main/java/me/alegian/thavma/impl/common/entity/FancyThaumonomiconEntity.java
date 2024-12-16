package me.alegian.thavma.impl.common.entity;

import me.alegian.thavma.impl.init.registries.deferred.T7EntityTypes;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Shorthand to create Thaumonomicon ItemEntities after right-clicking bookcases with a wand.
 * Does not bob up and down, always spawns at the center of the bookcase, has default pickup delay.
 */
public class FancyThaumonomiconEntity extends ItemEntity {
  public FancyThaumonomiconEntity(Level pLevel, BlockPos blockPos) {
    super(T7EntityTypes.INSTANCE.getFANCY_ITEM().get(), pLevel);
    this.setPos(blockPos.getX() + .5f, blockPos.getY() + .5f, blockPos.getZ() + .5f);
    this.setDeltaMovement(0, 0, 0);
    var itemstack = new ItemStack(T7Items.THAUMONOMICON.get());
    this.setItem(itemstack);
    this.setDefaultPickUpDelay();
    this.lifespan = itemstack.getEntityLifespan(pLevel);
  }
}
