package me.ichun.mods.betterthanbunnies.loader.fabric;

import me.ichun.mods.betterthanbunnies.common.core.EventHandlerClient;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.world.entity.EntityType;

public class EventHandlerClientFabric extends EventHandlerClient
{
    public EventHandlerClientFabric()
    {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityType == EntityType.RABBIT && entityRenderer instanceof RabbitRenderer rabbitRenderer)
            {
                addFancyLayer(rabbitRenderer);
            }
        });
    }
}
