package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.capability.T7Capabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.block.entity.AuraNodeBE;
import me.alegian.thaumcraft7.impl.common.block.entity.CrucibleBE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7BlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> REGISTRAR = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thaumcraft.MODID);

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AuraNodeBE>> AURA_NODE =
      REGISTRAR.register(
          "aura_node",
          () -> BlockEntityType.Builder.of(
              AuraNodeBE::new,
              T7Blocks.AURA_NODE.get()
          ).build(null)
      );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrucibleBE>> CRUCIBLE =
      REGISTRAR.register(
          "crucible",
          () -> BlockEntityType.Builder.of(
              CrucibleBE::new,
              T7Blocks.CRUCIBLE.get()
          ).build(null)
      );

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, CRUCIBLE.get(), (be, context) -> be.getFluidHandler());
    event.registerBlockEntity(T7Capabilities.AspectContainer.BLOCK, CRUCIBLE.get(), (be, context) -> be.getAspectContainer());
  }
}
