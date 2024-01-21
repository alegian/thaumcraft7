package me.alegian.thaumcraft7.item;

import me.alegian.thaumcraft7.client.ThaumonomiconScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThaumonomiconItem extends Item {

    public ThaumonomiconItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide())
            Minecraft.getInstance().setScreen(new ThaumonomiconScreen());

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
