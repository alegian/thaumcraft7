package me.alegian.thaumcraft7.block;


import me.alegian.thaumcraft7.capability.VisStorage;
import me.alegian.thaumcraft7.item.WandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NodeBlock extends Block {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16,16);

    protected NodeBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide()) player.sendSystemMessage(Component.literal("use block" + player.getItemInHand(hand).getItem()));

        if(player.getItemInHand(hand).getItem() instanceof WandItem) {

            var cap = player.getItemInHand(hand).getCapability(VisStorage.ITEM);


            if (cap != null) {
                player.sendSystemMessage(Component.literal("CAP : " + cap));
                float test = cap.receiveVis(5);

            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void destroy(LevelAccessor p_49860_, BlockPos p_49861_, BlockState p_49862_) {
//        super.destroy(p_49860_, p_49861_, p_49862_);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter plevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
////        return
//    }
}
