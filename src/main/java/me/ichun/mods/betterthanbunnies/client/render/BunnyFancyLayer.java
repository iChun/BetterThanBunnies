package me.ichun.mods.betterthanbunnies.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import me.ichun.mods.betterthanbunnies.client.model.BunnyFancyModel;
import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

import java.util.Random;

public class BunnyFancyLayer extends RenderLayer<Rabbit, RabbitModel<Rabbit>>
{
    public static final ResourceLocation TEX_FANCY_BUNNY = new ResourceLocation("betterthanbunnies","textures/model/fancybunny.png");
    public static final ResourceLocation TEX_FANCY_BUNNY_COLORIZER = new ResourceLocation("betterthanbunnies","textures/model/fancybunnycolorizer.png");
    public BunnyFancyModel modelFancyBunny = new BunnyFancyModel();
    public Random rand;

    public BunnyFancyLayer(RabbitRenderer renderer)
    {
        super(renderer);
        this.rand = new Random();
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Rabbit rabbit, float limbSwing, float limbSwingAmount, float renderTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        boolean iChunRabbit = rabbit.hasCustomName() && "iChun".equals(rabbit.getName().getString());
        if(iChunRabbit)
        {
            rand.setSeed(Math.abs("iChun".hashCode() + (rabbit.getId() * 63268L) * 5642L));
        }
        else
        {
            rand.setSeed(Math.abs((rabbit.hasCustomName() ? rabbit.getName().getString().hashCode() : rabbit.getUUID().hashCode()) * 5642L));
        }
        if(!rabbit.isInvisible() && (iChunRabbit || rand.nextFloat() < (BetterThanBunnies.config.fancyChance.get() / 100F)))
        {
            boolean renderHat, renderMonocle, renderPipe, renderSuit;
            if(iChunRabbit)
            {
                renderHat = rand.nextBoolean();
                renderMonocle = rand.nextBoolean();
                renderPipe = rand.nextBoolean();
                renderSuit = rand.nextBoolean();
            }
            else
            {
                renderHat = rand.nextFloat() < BetterThanBunnies.config.hatChance.get() / 100F;
                renderMonocle = rand.nextFloat() < BetterThanBunnies.config.monocleChance.get() / 100F;
                renderPipe = rand.nextFloat() < BetterThanBunnies.config.pipeChance.get() / 100F;
                renderSuit = rand.nextFloat() < BetterThanBunnies.config.suitChance.get() / 100F;
            }

            if(renderHat || renderMonocle || renderPipe || renderSuit)
            {
                modelFancyBunny.setupAnim(rabbit, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                float[] clr = new float[3];
                if (iChunRabbit)
                {
                    int i = rabbit.tickCount / 25 + rabbit.getId();
                    int j = DyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f = ((float)(rabbit.tickCount % 25) + renderTick) / 25.0F;
                    float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
                    float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
                    clr[0] = afloat1[0] * (1.0F - f) + afloat2[0] * f;
                    clr[1] = afloat1[1] * (1.0F - f) + afloat2[1] * f;
                    clr[2] = afloat1[2] * (1.0F - f) + afloat2[2] * f;
                }
                else
                {
                    rand.setSeed(Math.abs(rabbit.getId() * 1234L));
                    clr = Sheep.getColorArray(DyeColor.byId(rand.nextInt(16)));
                }

                VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY));

                int packedOverlay = LivingEntityRenderer.getOverlayCoords(rabbit, 0.0F);

                //push for body renderBody
                matrixStackIn.pushPose();

                if(rabbit.isBaby()) //child has an additional pushpop for head
                {
                    matrixStackIn.scale(0.56666666F, 0.56666666F, 0.56666666F);
                    matrixStackIn.translate(0.0D, 1.375D, 0.125D);
                }
                else
                {
                    matrixStackIn.scale(0.6F, 0.6F, 0.6F);
                    matrixStackIn.translate(0.0D, 1.0D, 0.0D);
                }

                if(renderHat || renderMonocle || renderPipe)
                {
                    matrixStackIn.pushPose();

                    matrixStackIn.translate(0F, 1F, -0.0625F);
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(netHeadYaw));
                    matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(interpolateValues(rabbit.xRotO, rabbit.getXRot(), renderTick)));
                    matrixStackIn.translate(0F, -1F, 0.0625F);

                    modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, false, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    if(renderHat)
                    {
                        ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                        modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, true, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, clr[0], clr[1], clr[2], 1.0F);
                        ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY));
                    }
                    matrixStackIn.popPose();
                }

                if(renderSuit)
                {
                    //Render body
                    if(rabbit.isBaby()) //transform/scale for body
                    {
                        matrixStackIn.popPose();
                        matrixStackIn.pushPose();
                        matrixStackIn.scale(0.4F, 0.4F, 0.4F);
                        matrixStackIn.translate(0.0D, 2.25D, 0.0D);
                    }

                    modelFancyBunny.renderBody(false, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

                    ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                    modelFancyBunny.renderBody(true, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, clr[0], clr[1], clr[2], 1.0F);
                }
                matrixStackIn.popPose();
            }
        }
    }

    public static float interpolateValues(float prevVal, float nextVal, float partialTick)
    {
        return prevVal + partialTick * (nextVal - prevVal);
    }
}
