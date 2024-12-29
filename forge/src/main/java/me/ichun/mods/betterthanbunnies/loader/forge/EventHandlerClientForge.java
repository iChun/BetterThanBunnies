package me.ichun.mods.betterthanbunnies.loader.forge;

import me.ichun.mods.betterthanbunnies.common.core.EventHandlerClient;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.state.RabbitRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class EventHandlerClientForge extends EventHandlerClient
{
    public EventHandlerClientForge(IEventBus modEventBus)
    {
        modEventBus.addListener(this::onAddLayers);
    }

    private void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        LivingEntityRenderer<Rabbit, RabbitRenderState, RabbitModel> render = event.getEntityRenderer(EntityType.RABBIT);
        if(render instanceof RabbitRenderer rabbitRenderer)
        {
            addFancyLayer(rabbitRenderer);
        }
    }
}
