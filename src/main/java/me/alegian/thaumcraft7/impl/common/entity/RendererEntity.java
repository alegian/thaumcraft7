package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7EntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// helper entity used for spawning Renderers
public abstract class RendererEntity extends Entity {
  // position is used to determine if the render section is active, otherwise the entity is culled
  public RendererEntity(Level pLevel, Vec3 pos) {
    super(T7EntityTypes.VIS.get(), pLevel);
    setPos(pos);
  }
}
