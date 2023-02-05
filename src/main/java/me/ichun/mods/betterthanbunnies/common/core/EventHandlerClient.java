package me.ichun.mods.betterthanbunnies.common.core;

import me.ichun.mods.betterthanbunnies.client.render.BunnyFancyLayer;
import me.ichun.mods.betterthanbunnies.mixin.LivingEntityRendererAccessorMixin;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.Rabbit;

public abstract class EventHandlerClient
{
    @SuppressWarnings("unchecked")
    public void addFancyLayer(RabbitRenderer rabbitRenderer)
    {
        boolean flag = false;
        for(RenderLayer<Rabbit, RabbitModel<Rabbit>> layer : ((LivingEntityRendererAccessorMixin<Rabbit, RabbitModel<Rabbit>>)rabbitRenderer).getLayers())
        {
            if(layer instanceof BunnyFancyLayer)
            {
                flag = true;
                break;
            }
        }
        if(!flag)
        {
            ((LivingEntityRendererAccessorMixin<Rabbit, RabbitModel<Rabbit>>)rabbitRenderer).invokeAddLayer(new BunnyFancyLayer(rabbitRenderer));
        }

    }
}
