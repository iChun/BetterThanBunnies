package me.ichun.mods.betterthanbunnies.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import me.ichun.mods.betterthanbunnies.client.model.BunnyFancyModel;
import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.RabbitRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

import java.util.Random;

public class BunnyFancyLayer extends RenderLayer<RabbitRenderState, RabbitModel>
{
    public static final ResourceLocation TEX_FANCY_BUNNY = ResourceLocation.fromNamespaceAndPath("betterthanbunnies","textures/model/fancybunny.png");
    public static final ResourceLocation TEX_FANCY_BUNNY_COLORIZER = ResourceLocation.fromNamespaceAndPath("betterthanbunnies","textures/model/fancybunnycolorizer.png");
    public BunnyFancyModel modelFancyBunny = new BunnyFancyModel();
    public Random rand = new Random();

    public BunnyFancyLayer(RabbitRenderer renderer)
    {
        super(renderer);
    }

    @Override
    //    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Rabbit rabbit, float limbSwing, float limbSwingAmount, float renderTick, float ageInTicks, float netHeadYaw, float headPitch)
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, RabbitRenderState renderState, float yRot, float xRot)
    {
        Rabbit rabbit = BetterThanBunnies.eventHandlerClient.rabbitRendered.get();
        if(rabbit == null) return;

        if(!renderState.isInvisible)
        {
            boolean iChunRabbit = renderState.customName != null && "iChun".equals(renderState.customName.getString());
            if(iChunRabbit)
            {
                rand.setSeed(Math.abs("iChun".hashCode() + (rabbit.getId() * 63268L) * 5642L));
            }
            else
            {
                rand.setSeed(Math.abs((renderState.customName != null ? renderState.customName.getString().hashCode() : rabbit.getUUID().hashCode()) * 5642L));
            }

            if(iChunRabbit || rand.nextFloat() < (BetterThanBunnies.config.fancyChance / 100F))
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
                    renderHat = rand.nextFloat() < BetterThanBunnies.config.hatChance / 100F;
                    renderMonocle = rand.nextFloat() < BetterThanBunnies.config.monocleChance / 100F;
                    renderPipe = rand.nextFloat() < BetterThanBunnies.config.pipeChance / 100F;
                    renderSuit = rand.nextFloat() < BetterThanBunnies.config.suitChance / 100F;
                }

                if(renderHat || renderMonocle || renderPipe || renderSuit)
                {
                    modelFancyBunny.setupAnim(renderState);

                    int clr;
                    if(iChunRabbit)
                    {
                        int i = Mth.floor(renderState.ageInTicks) / 25 + rabbit.getId();
                        int j = DyeColor.values().length;
                        int k = i % j;
                        int l = (i + 1) % j;
                        float f = ((float)(Mth.floor(renderState.ageInTicks) % 25) + Mth.frac(renderState.ageInTicks)) / 25.0F;
                        int clr1 = Sheep.getColor(DyeColor.byId(k));
                        int clr2 = Sheep.getColor(DyeColor.byId(l));
                        clr = ARGB.lerp(f, clr1, clr2);
                    }
                    else
                    {
                        rand.setSeed(Math.abs(rabbit.getId() * 1234L));
                        clr = Sheep.getColor(DyeColor.byId(rand.nextInt(16)));
                    }

                    VertexConsumer ivertexbuilder = bufferSource.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY));

                    int packedOverlay = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);

                    //push for body renderBody
                    poseStack.pushPose();

                    if(rabbit.isBaby()) //child has an additional pushpop for head
                    {
                        poseStack.scale(0.56666666F, 0.56666666F, 0.56666666F);
                        poseStack.translate(0.0D, 1.375D, 0.125D);
                    }
                    else
                    {
                        poseStack.scale(0.6F, 0.6F, 0.6F);
                        poseStack.translate(0.0D, 1.0D, 0.0D);
                    }

                    if(renderHat || renderMonocle || renderPipe)
                    {
                        poseStack.pushPose();

                        poseStack.translate(0F, 1F, -0.0625F);
                        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot));
                        poseStack.mulPose(Axis.XP.rotationDegrees(renderState.xRot));
                        poseStack.translate(0F, -1F, 0.0625F);

                        modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, false, poseStack, ivertexbuilder, packedLight, packedOverlay, 0xffffffff);
                        if(renderHat)
                        {
                            ivertexbuilder = bufferSource.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                            modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, true, poseStack, ivertexbuilder, packedLight, packedOverlay, clr);
                            ivertexbuilder = bufferSource.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY));
                        }
                        poseStack.popPose();
                    }

                    if(renderSuit)
                    {
                        //Render body
                        if(rabbit.isBaby()) //transform/scale for body
                        {
                            poseStack.popPose();
                            poseStack.pushPose();
                            poseStack.scale(0.4F, 0.4F, 0.4F);
                            poseStack.translate(0.0D, 2.25D, 0.0D);
                        }

                        modelFancyBunny.renderBody(false, poseStack, ivertexbuilder, packedLight, packedOverlay, 0xffffffff);

                        ivertexbuilder = bufferSource.getBuffer(RenderType.entityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                        modelFancyBunny.renderBody(true, poseStack, ivertexbuilder, packedLight, packedOverlay, clr);
                    }
                    poseStack.popPose();
                }
            }
        }
    }
}
