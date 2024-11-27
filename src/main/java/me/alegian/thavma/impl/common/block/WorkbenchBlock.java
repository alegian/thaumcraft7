package me.alegian.thavma.impl.common.block;

import me.alegian.thavma.impl.Thavma;
import me.alegian.thavma.impl.common.block.entity.WorkbenchBE;
import me.alegian.thavma.impl.common.menu.WorkbenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorkbenchBlock extends Block implements EntityBlock {
  private static final Component CONTAINER_TITLE = Component.translatable("container." + Thavma.MODID + ".arcane_workbench");

  public WorkbenchBlock() {
    super(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion());
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
    if (pLevel.isClientSide)
      return InteractionResult.SUCCESS;
    else {
      pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
      return InteractionResult.CONSUME;
    }
  }

  @NotNull
  @Override
  public RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Override
  protected MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
    return new SimpleMenuProvider(
        (pContainerId, pPlayerInventory, player) -> new WorkbenchMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(pLevel, pPos)),
        WorkbenchBlock.CONTAINER_TITLE
    );
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState blockState) {
    return new WorkbenchBE(pos, blockState);
  }
}
