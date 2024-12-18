package me.alegian.thavma.impl.init.registries

import me.alegian.thavma.impl.Thavma.rl
import me.alegian.thavma.impl.common.aspect.AspectMap
import net.minecraft.core.registries.Registries
import net.neoforged.neoforge.registries.datamaps.DataMapType

object T7DataMaps {
    object AspectContent {
        val ITEM = DataMapType
            .builder(
                rl("aspect_content"),
                Registries.ITEM,
                AspectMap.CODEC
            )
            .synced(AspectMap.CODEC, true)
            .build()

        val BLOCK = DataMapType
            .builder(
                rl("aspect_content"),
                Registries.BLOCK,
                AspectMap.CODEC
            )
            .synced(AspectMap.CODEC, true)
            .build()
    }
}
