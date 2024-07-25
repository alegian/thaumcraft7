package me.alegian.thaumcraft7.impl.init.registries.deferred;

import me.alegian.thaumcraft7.api.aspect.Aspect;
import me.alegian.thaumcraft7.api.aspect.AspectList;
import me.alegian.thaumcraft7.api.capability.TCCapabilities;
import me.alegian.thaumcraft7.impl.Thaumcraft;
import me.alegian.thaumcraft7.impl.common.block.AuraNodeB;
import me.alegian.thaumcraft7.impl.common.block.CrucibleB;
import me.alegian.thaumcraft7.impl.common.data.capability.AspectContainer;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TCBlocks {
  public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(Thaumcraft.MODID);

  public static final DeferredBlock<AuraNodeB> AURA_NODE_BLOCK = REGISTRAR.register("aura_node", AuraNodeB::new);
  public static final DeferredItem<BlockItem> AURA_NODE_BLOCK_ITEM = TCItems.REGISTRAR.registerSimpleBlockItem("aura_node", AURA_NODE_BLOCK);
  public static final DeferredBlock<CrucibleB> CRUCIBLE = REGISTRAR.register("crucible", CrucibleB::new);
  public static final DeferredItem<BlockItem> CRUCIBLE_ITEM = TCItems.REGISTRAR.registerSimpleBlockItem("crucible", CRUCIBLE);

  public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    AspectList contents = new AspectList().add(Aspect.AER, 2).add(Aspect.POTENTIA, 4);
    event.registerBlock(TCCapabilities.AspectContainer.BLOCK, (level, pos, state, be, context) -> new AspectContainer(contents), AURA_NODE_BLOCK.get());
  }
}
