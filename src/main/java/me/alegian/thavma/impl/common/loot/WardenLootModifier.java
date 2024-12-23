package me.alegian.thavma.impl.common.loot;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.alegian.thavma.impl.init.registries.deferred.T7Items;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class WardenLootModifier extends LootModifier {
  public static final MapCodec<WardenLootModifier> CODEC = MapCodec.unit(new WardenLootModifier());

  public WardenLootModifier() {
    super(new LootItemCondition[]{
        LootTableIdCondition.builder(EntityType.WARDEN.getDefaultLootTable().location()).build()
    });
  }

  @Override
  protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
    generatedLoot.add(new ItemStack(T7Items.INSTANCE.getEYE_OF_WARDEN().get()));
    return generatedLoot;
  }

  @Override
  public MapCodec<? extends IGlobalLootModifier> codec() {
    return WardenLootModifier.CODEC;
  }
}
