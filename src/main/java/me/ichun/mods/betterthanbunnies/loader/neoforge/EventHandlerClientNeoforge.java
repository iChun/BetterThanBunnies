package me.ichun.mods.betterthanbunnies.loader.neoforge;

import me.ichun.mods.betterthanbunnies.common.core.EventHandlerClient;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class EventHandlerClientNeoforge extends EventHandlerClient
{
    public EventHandlerClientNeoforge(IEventBus modEventBus)
    {
        modEventBus.addListener(this::onAddLayers);
    }

    private void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        LivingEntityRenderer<Rabbit, ? extends EntityModel<Rabbit>> render = event.getRenderer(EntityType.RABBIT);
        if(render instanceof RabbitRenderer rabbitRenderer)
        {
            addFancyLayer(rabbitRenderer);
        }
    }
}
