package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7EntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// helper entity used for spawning Renderers
public class RendererEntity extends Entity {
  // position is used to determine if the render section is active, otherwise the entity is culled
  public RendererEntity(Level pLevel, Vec3 pos) {
    super(T7EntityTypes.RENDERER.get(), pLevel);
    setPos(pos);
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {

  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {

  }

  @Override
  protected void addAdditionalSaveData(CompoundTag pCompound) {

  }

  @Override
  public boolean shouldRender(double pX, double pY, double pZ) {
    return true;
  }
}
