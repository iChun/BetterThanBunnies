package me.ichun.mods.betterthanbunnies.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.ichun.mods.betterthanbunnies.client.model.BunnyFancyModel;
import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class BunnyFancyLayer extends LayerRenderer<RabbitEntity, RabbitModel<RabbitEntity>>
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
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, RabbitEntity rabbit, float limbSwing, float limbSwingAmount, float renderTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        boolean iChunRabbit = rabbit.hasCustomName() && "iChun".equals(rabbit.getName().getUnformattedComponentText());
        if(iChunRabbit)
        {
            rand.setSeed(Math.abs("iChun".hashCode() + (rabbit.getEntityId() * 63268L) * 5642L));
        }
        else
        {
            rand.setSeed(Math.abs((rabbit.hasCustomName() ? rabbit.getName().getUnformattedComponentText().hashCode() : rabbit.getEntityId()) * 5642L));
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
                modelFancyBunny.setRotationAngles(rabbit, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                float[] clr = new float[3];
                if (iChunRabbit)
                {
                    int i = rabbit.ticksExisted / 25 + rabbit.getEntityId();
                    int j = DyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f = ((float)(rabbit.ticksExisted % 25) + renderTick) / 25.0F;
                    float[] afloat1 = SheepEntity.getDyeRgb(DyeColor.byId(k));
                    float[] afloat2 = SheepEntity.getDyeRgb(DyeColor.byId(l));
                    clr[0] = afloat1[0] * (1.0F - f) + afloat2[0] * f;
                    clr[1] = afloat1[1] * (1.0F - f) + afloat2[1] * f;
                    clr[2] = afloat1[2] * (1.0F - f) + afloat2[2] * f;
                }
                else
                {
                    rand.setSeed(Math.abs(rabbit.getEntityId() * 1234L));
                    clr = SheepEntity.getDyeRgb(DyeColor.byId(rand.nextInt(16)));
                }

                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEX_FANCY_BUNNY));

                int packedOverlay = LivingRenderer.getPackedOverlay(rabbit, 0.0F);

                //push for body renderBody
                matrixStackIn.push();

                if(rabbit.isChild()) //child has an additional pushpop for head
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
                    matrixStackIn.push();

                    matrixStackIn.translate(0F, 1F, -0.0625F);
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees(netHeadYaw));
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(interpolateValues(rabbit.prevRotationPitch, rabbit.rotationPitch, renderTick)));
                    matrixStackIn.translate(0F, -1F, 0.0625F);

                    modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, false, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                    if(renderHat)
                    {
                        ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                        modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, true, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, clr[0], clr[1], clr[2], 1.0F);
                        ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEX_FANCY_BUNNY));
                    }
                    matrixStackIn.pop();
                }

                if(renderSuit)
                {
                    //Render body
                    if(rabbit.isChild()) //transform/scale for body
                    {
                        matrixStackIn.pop();
                        matrixStackIn.push();
                        matrixStackIn.scale(0.4F, 0.4F, 0.4F);
                        matrixStackIn.translate(0.0D, 2.25D, 0.0D);
                    }

                    modelFancyBunny.renderBody(false, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

                    ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(TEX_FANCY_BUNNY_COLORIZER));
                    modelFancyBunny.renderBody(true, matrixStackIn, ivertexbuilder, packedLightIn, packedOverlay, clr[0], clr[1], clr[2], 1.0F);
                }
                matrixStackIn.pop();
            }
        }
    }

    public static float interpolateValues(float prevVal, float nextVal, float partialTick)
    {
        return prevVal + partialTick * (nextVal - prevVal);
    }
}
