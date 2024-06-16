package me.alegian.thaumcraft7.blockentity;

import me.alegian.thaumcraft7.Thaumcraft;
import me.alegian.thaumcraft7.block.TCBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRAR = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Thaumcraft.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AuraNodeBE>> AURA_NODE =
            REGISTRAR.register(
            "aura_node",
            () -> BlockEntityType.Builder.of(
                AuraNodeBE::new,
                TCBlocks.AURA_NODE_BLOCK.get()
            ).build(null)
        );
}
