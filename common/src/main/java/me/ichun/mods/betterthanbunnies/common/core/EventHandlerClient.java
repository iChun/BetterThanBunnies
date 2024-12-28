package me.ichun.mods.betterthanbunnies.common.core;

import me.ichun.mods.betterthanbunnies.client.render.BunnyFancyLayer;
import me.ichun.mods.ichunutil.loader.event.client.LivingRenderPreEvent;
import me.ichun.mods.ichunutil.mixin.client.LivingEntityRendererAccessorMixin;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.RabbitRenderState;
import net.minecraft.world.entity.animal.Rabbit;

public abstract class EventHandlerClient
{
    public final LivingRenderPreEvent.LastRenderedEntitySupplier<Rabbit> rabbitRendered;

    public EventHandlerClient()
    {
        rabbitRendered = new LivingRenderPreEvent.LastRenderedEntitySupplier<>(event -> event.renderer() instanceof RabbitRenderer renderer && renderer.getModel().getClass().equals(RabbitModel.class) && event.livingEntity() instanceof Rabbit && event.renderState() instanceof RabbitRenderState);
    }

    @SuppressWarnings("unchecked")
    public void addFancyLayer(RabbitRenderer rabbitRenderer)
    {
        boolean flag = false;
        for(RenderLayer<RabbitRenderState, RabbitModel> layer : ((LivingEntityRendererAccessorMixin<Rabbit, RabbitRenderState, RabbitModel>)rabbitRenderer).getLayers())
        {
            if(layer instanceof BunnyFancyLayer)
            {
                flag = true;
                break;
            }
        }
        if(!flag)
        {
            ((LivingEntityRendererAccessorMixin<Rabbit, RabbitRenderState, RabbitModel>)rabbitRenderer).invokeAddLayer(new BunnyFancyLayer(rabbitRenderer));
        }
    }
}
