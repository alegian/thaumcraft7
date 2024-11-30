package me.alegian.thavma.impl.init.registries.deferred;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.entity.*;
import me.alegian.thavma.impl.common.data.capability.AspectContainer;
import me.alegian.thavma.impl.init.registries.T7Capabilities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class T7BlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> REGISTRAR = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thavma.MODID);

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, T7BlockEntities.CRUCIBLE.get(), (be, context) -> be.getFluidHandler());
    event.registerBlockEntity(T7Capabilities.AspectContainer.BLOCK, T7BlockEntities.CRUCIBLE.get(), (be, context) -> new AspectContainer(be));
    event.registerBlockEntity(T7Capabilities.AspectContainer.BLOCK, T7BlockEntities.AURA_NODE.get(), (be, context) -> new AspectContainer(be));
  }

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AuraNodeBE>> AURA_NODE =
      T7BlockEntities.REGISTRAR.register(
          "aura_node",
          () -> BlockEntityType.Builder.of(
              AuraNodeBE::new,
              T7Blocks.AURA_NODE.get()
          ).build(null)
      );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrucibleBE>> CRUCIBLE =
      T7BlockEntities.REGISTRAR.register(
          "crucible",
          () -> BlockEntityType.Builder.of(
              CrucibleBE::new,
              T7Blocks.CRUCIBLE.get()
          ).build(null)
      );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WorkbenchBE>> WORKBENCH =
      T7BlockEntities.REGISTRAR.register(
          "arcane_workbench",
          () -> BlockEntityType.Builder.of(
              WorkbenchBE::new,
              T7Blocks.ARCANE_WORKBENCH.get()
          ).build(null)
      );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatrixBE>> MATRIX =
      T7BlockEntities.REGISTRAR.register(
          "infusion_matrix",
          () -> BlockEntityType.Builder.of(
              MatrixBE::new,
              T7Blocks.MATRIX.get()
          ).build(null)
      );

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PillarBE>> PILLAR =
      T7BlockEntities.REGISTRAR.register(
          "infusion_pillar",
          () -> BlockEntityType.Builder.of(
              PillarBE::new,
              T7Blocks.PILLAR.get()
          ).build(null)
      );
}
