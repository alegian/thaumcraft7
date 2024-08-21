package me.alegian.thaumcraft7.impl.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataComponentBE extends BlockEntity implements MutableDataComponentHolder {
  final PatchedDataComponentMap components = new PatchedDataComponentMap(DataComponentMap.EMPTY);

  public DataComponentBE(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
    super(pType, pPos, pBlockState);
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

  @Override
  protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.loadAdditional(pTag, pRegistries);
    var componentTypes = getComponents().keySet();
    for (var componentType : componentTypes) {
      loadComponent(pTag, componentType);
    }
  }

  protected <T> void loadComponent(CompoundTag pTag, DataComponentType<T> pComponentType) {
    T loaded = pComponentType.codecOrThrow().decode(NbtOps.INSTANCE, pTag).getOrThrow().getFirst();
    set(pComponentType, loaded);
  }

  @Override
  protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.saveAdditional(pTag, pRegistries);
    var componentTypes = getComponents().keySet();
    for (var componentType : componentTypes) {
      saveComponent(pTag, componentType);
    }
  }

  protected <T> void saveComponent(CompoundTag pTag, DataComponentType<T> pComponentType) {
    Tag tag = pComponentType.codecOrThrow().encode(get(pComponentType), NbtOps.INSTANCE, pTag).getOrThrow();
    pTag.merge((CompoundTag) tag);
  }
}
