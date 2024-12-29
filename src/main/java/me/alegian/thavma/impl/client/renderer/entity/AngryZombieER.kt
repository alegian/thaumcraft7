package me.alegian.thavma.impl.client.renderer.entity

import me.alegian.thavma.impl.Thavma.rl
import net.minecraft.client.model.ZombieModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.ZombieRenderer
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.world.entity.monster.Zombie

open class AngryZombieER(ctx: Context) : ZombieRenderer(ctx) {
    init {
        this.addLayer(AngryZombieEyesLayer(this))
    }
}


internal class AngryZombieEyesLayer<T : Zombie>(layerParent: RenderLayerParent<T, ZombieModel<T>>) : EyesLayer<T, ZombieModel<T>>(layerParent) {
    override fun renderType(): RenderType {
        return ANGRY_ZOMBIE_EYES
    }

    companion object {
        private val ANGRY_ZOMBIE_EYES: RenderType = RenderType.eyes(rl("textures/entity/angry_zombie_eyes.png"))
    }
}