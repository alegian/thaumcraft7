package me.alegian.thaumcraft7.datagen;

import me.alegian.thaumcraft7.Thaumcraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThaumcraftDataGenerators {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var pack = generator.getVanillaPack(true);

        pack.addProvider((packOutput)-> new TCBlockStateProvider(packOutput, existingFileHelper));
    }
}
