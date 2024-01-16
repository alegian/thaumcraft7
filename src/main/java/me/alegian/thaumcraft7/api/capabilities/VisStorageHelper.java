package me.alegian.thaumcraft7.api.capabilities;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class VisStorageHelper {
    /*
     * always check hasVis before using
     */
    public static float getVisStored(ItemStack stack){
        return Objects.requireNonNull(stack.getCapability(ThaumcraftCapabilities.VisStorage.ITEM)).getVisStored();
    }

    /*
     * always check hasVis before using
     */
    public static float extractVis(ItemStack stack, float maxExtract){
        return Objects.requireNonNull(stack.getCapability(ThaumcraftCapabilities.VisStorage.ITEM)).extractVis(maxExtract);
    }

    /*
     * always check hasVis before using
     */
    public static float receiveVis(ItemStack stack, float maxReceive){
        return Objects.requireNonNull(stack.getCapability(ThaumcraftCapabilities.VisStorage.ITEM)).receiveVis(maxReceive);
    }

    public static boolean hasVis(ItemStack stack){
        return stack.getCapability(ThaumcraftCapabilities.VisStorage.ITEM) != null;
    }

    public static boolean hasVisInHand(Player player){
        var visMain = hasVis(player.getItemInHand(InteractionHand.MAIN_HAND));
        var visOff = hasVis(player.getItemInHand(InteractionHand.MAIN_HAND));
        return visMain || visOff;
    }
}
