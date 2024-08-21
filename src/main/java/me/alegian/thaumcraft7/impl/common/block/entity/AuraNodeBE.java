package me.alegian.thaumcraft7.impl.common.block.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuraNodeBE extends BlockEntity implements MutableDataComponentHolder {
  final PatchedDataComponentMap components = new PatchedDataComponentMap(DataComponentMap.EMPTY);

  public AuraNodeBE(BlockPos pos, BlockState blockState) {
    super(T7BlockEntities.AURA_NODE.get(), pos, blockState);
  }

  @Override
  public <T> @Nullable T set(@NotNull DataComponentType<? super T> componentType, @Nullable T value) {
    return this.components.set(componentType, value);
  }

  @Override
  public <T> @Nullable T remove(@NotNull DataComponentType<? extends T> componentType) {
    return this.components.remove(componentType);
  }

  @Override
  public void applyComponents(@NotNull DataComponentPatch patch) {
    this.components.applyPatch(patch);
  }

  @Override
  public void applyComponents(@NotNull DataComponentMap components) {
    this.components.setAll(components);
  }

  @Override
  public @NotNull DataComponentMap getComponents() {
    return components;
  }
}
