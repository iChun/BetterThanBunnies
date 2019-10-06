package me.ichun.mods.betterthanbunnies.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import me.ichun.mods.betterthanbunnies.client.model.BunnyFancyModel;
import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
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
    public void render(RabbitEntity rabbit, float limbSwing, float limbSwingAmount, float renderTick, float ageInTicks, float netHeadYaw, float headPitch, float f5)
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

                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

                //push for body renderBody
                GlStateManager.pushMatrix();

                float scale = 0.0625F;
                if(rabbit.isChild()) //child has an additional pushpop for head
                {
                    GlStateManager.scalef(0.56666666F, 0.56666666F, 0.56666666F);
                    GlStateManager.translatef(0.0F, 22.0F * scale, 2.0F * scale);
                }
                else
                {
                    GlStateManager.scalef(0.6F, 0.6F, 0.6F);
                    GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
                }

                if(renderHat || renderMonocle || renderPipe)
                {
                    GlStateManager.pushMatrix();

                    GlStateManager.translatef(0F, 1F, -0.0625F);
                    GlStateManager.rotatef(netHeadYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotatef(interpolateValues(rabbit.prevRotationPitch, rabbit.rotationPitch, renderTick), 1.0F, 0.0F, 0.0F);
                    GlStateManager.translatef(0F, -1F, 0.0625F);

                    this.bindTexture(TEX_FANCY_BUNNY);
                    GlStateManager.color3f(1F, 1F, 1F);
                    modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, false, 0.0625F);

                    if(renderHat)
                    {
                        this.bindTexture(TEX_FANCY_BUNNY_COLORIZER);
                        GlStateManager.color3f(clr[0], clr[1], clr[2]);
                        modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, true, 0.0625F);
                    }
                    GlStateManager.popMatrix();
                }

                if(renderSuit)
                {
                    //Render body
                    if(rabbit.isChild()) //transform/scale for body
                    {
                        GlStateManager.popMatrix();
                        GlStateManager.pushMatrix();
                        GlStateManager.scalef(0.4F, 0.4F, 0.4F);
                        GlStateManager.translatef(0.0F, 36.0F * scale, 0.0F);
                    }

                    this.bindTexture(TEX_FANCY_BUNNY);
                    GlStateManager.color3f(1F, 1F, 1F);
                    modelFancyBunny.renderBody(rabbit, false, ageInTicks, 0.0625F);

                    this.bindTexture(TEX_FANCY_BUNNY_COLORIZER);
                    GlStateManager.color3f(clr[0], clr[1], clr[2]);
                    modelFancyBunny.renderBody(rabbit, true, ageInTicks, 0.0625F);
                }
                GlStateManager.popMatrix();

                GlStateManager.disableBlend();
            }
        }
    }

    public static float interpolateValues(float prevVal, float nextVal, float partialTick)
    {
        return prevVal + partialTick * (nextVal - prevVal);
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return true;
    }
}
