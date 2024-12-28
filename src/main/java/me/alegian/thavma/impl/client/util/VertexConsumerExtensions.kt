package me.alegian.thavma.impl.client.util

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.world.phys.Vec3

fun VertexConsumer.setColorDebug() = setColor(0x33FF0000.toInt())

fun VertexConsumer.addVertex(pose: PoseStack, position: Vec3) = addVertex(pose.last(), position.toVector3f())