package me.ichun.mods.betterthanbunnies.loader.forge;

import me.ichun.mods.betterthanbunnies.common.core.EventHandlerClient;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class EventHandlerClientForge extends EventHandlerClient
{
    public EventHandlerClientForge()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onAddLayers);
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
