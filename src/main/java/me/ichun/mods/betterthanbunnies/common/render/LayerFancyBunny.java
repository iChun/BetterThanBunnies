package me.ichun.mods.betterthanbunnies.common.render;

import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import me.ichun.mods.betterthanbunnies.common.model.ModelFancyBunny;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderRabbit;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class LayerFancyBunny implements LayerRenderer<EntityRabbit>
{
    public final RenderRabbit renderer;

    public ModelFancyBunny modelFancyBunny = new ModelFancyBunny();
    public static final ResourceLocation texFancyBunny = new ResourceLocation("betterthanbunnies","textures/model/fancybunny.png");
    public static final ResourceLocation texFancyBunnyColorizer = new ResourceLocation("betterthanbunnies","textures/model/fancybunnycolorizer.png");
    public Random rand;

    public LayerFancyBunny(RenderRabbit renderer)
    {
        this.renderer = renderer;
        this.rand = new Random();
    }

    @Override
    public void doRenderLayer(EntityRabbit rabbit, float limbSwing, float limbSwingAmount, float renderTick, float ageInTicks, float netHeadYaw, float headPitch, float f5)
    {
        rand.setSeed(Math.abs(rabbit.getEntityId() * 5642L));
        if(!rabbit.isInvisible() && rand.nextFloat() < (BetterThanBunnies.fancyWeightage / 100F))
        {
            boolean renderHat = true;
            boolean renderMonocle = true;
            boolean renderPipe = true;
            boolean renderSuit = true;

            for(String disabled : BetterThanBunnies.disabledSuitParts)
            {
                if(disabled.equalsIgnoreCase("hat"))
                {
                    renderHat = false;
                }
                if(disabled.equalsIgnoreCase("monocle"))
                {
                    renderMonocle = false;
                }
                if(disabled.equalsIgnoreCase("pipe"))
                {
                    renderPipe = false;
                }
                if(disabled.equalsIgnoreCase("suit"))
                {
                    renderSuit = false;
                }
            }
            if(BetterThanBunnies.randomizeSuitParts == 1)
            {
                rand.setSeed(Math.abs(rabbit.getEntityId() * 8542L));
                if(renderHat)
                {
                    renderHat = rand.nextBoolean();
                }
                if(renderMonocle)
                {
                    renderMonocle = rand.nextBoolean();
                }
                if(renderPipe)
                {
                    renderPipe = rand.nextBoolean();
                }
                if(renderSuit)
                {
                    renderSuit = rand.nextBoolean();
                }
            }

            if(renderHat || renderMonocle || renderPipe || renderSuit)
            {
                float[] clr = new float[3];
                if (rabbit.hasCustomName() && "jeb_".equals(rabbit.getCustomNameTag()))
                {
                    int i = rabbit.ticksExisted / 25 + rabbit.getEntityId();
                    int j = EnumDyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f = ((float)(rabbit.ticksExisted % 25) + renderTick) / 25.0F;
                    float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
                    float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
                    clr[0] = afloat1[0] * (1.0F - f) + afloat2[0] * f;
                    clr[1] = afloat1[1] * (1.0F - f) + afloat2[1] * f;
                    clr[2] = afloat1[2] * (1.0F - f) + afloat2[2] * f;
                }
                else
                {
                    rand.setSeed(Math.abs(rabbit.getEntityId() * 1234L));
                    clr = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(rand.nextInt(16)));
                }

                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

                //push for body renderBody
                GlStateManager.pushMatrix();

                float scale = 0.0625F;
                if(rabbit.isChild()) //child has an additional pushpop for head
                {
                    GlStateManager.scale(0.56666666F, 0.56666666F, 0.56666666F);
                    GlStateManager.translate(0.0F, 22.0F * scale, 2.0F * scale);
                }
                else
                {
                    GlStateManager.scale(0.6F, 0.6F, 0.6F);
                    GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
                }

                if(renderHat || renderMonocle || renderPipe)
                {
                    GlStateManager.pushMatrix();

                    GlStateManager.translate(0F, 1F, -0.0625F);
                    GlStateManager.rotate(netHeadYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(interpolateValues(rabbit.prevRotationPitch, rabbit.rotationPitch, renderTick), 1.0F, 0.0F, 0.0F);
                    GlStateManager.translate(0F, -1F, 0.0625F);

                    renderer.bindTexture(texFancyBunny);
                    GlStateManager.color(1F, 1F, 1F);
                    modelFancyBunny.renderHeadParts(renderHat, renderMonocle, renderPipe, false, 0.0625F);

                    if(renderHat)
                    {
                        renderer.bindTexture(texFancyBunnyColorizer);
                        GlStateManager.color(clr[0], clr[1], clr[2]);
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
                        GlStateManager.scale(0.4F, 0.4F, 0.4F);
                        GlStateManager.translate(0.0F, 36.0F * scale, 0.0F);
                    }

                    renderer.bindTexture(texFancyBunny);
                    GlStateManager.color(1F, 1F, 1F);
                    modelFancyBunny.renderBody(rabbit, false, ageInTicks, 0.0625F);

                    renderer.bindTexture(texFancyBunnyColorizer);
                    GlStateManager.color(clr[0], clr[1], clr[2]);
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
