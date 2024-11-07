package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.entity.VisEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7EntityTypes {
  public static final DeferredRegister<EntityType<?>> REGISTRAR = DeferredRegister.create(Registries.ENTITY_TYPE, Thavma.MODID);

  public static final DeferredHolder<EntityType<?>, EntityType<ItemEntity>> FANCY_ITEM = REGISTRAR.register("fancy_item", () -> EntityType.Builder.<ItemEntity>of(ItemEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).eyeHeight(0.2125F).clientTrackingRange(6).updateInterval(20).build("fancy_item"));
  public static final DeferredHolder<EntityType<?>, EntityType<VisEntity>> VIS = REGISTRAR.register("vis", () -> EntityType.Builder.<VisEntity>of((entityType, level) -> new VisEntity(level, null), MobCategory.MISC).sized(0.25F, 0.25F).eyeHeight(0.2125F).clientTrackingRange(6).updateInterval(20).build("vis"));
}
