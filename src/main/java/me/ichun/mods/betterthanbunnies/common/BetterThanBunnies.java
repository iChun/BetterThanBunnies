package me.ichun.mods.betterthanbunnies.common;

import me.ichun.mods.betterthanbunnies.common.render.LayerFancyBunny;
import me.ichun.mods.betterthanbunnies.common.core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderRabbit;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME,
        version = Reference.VERSION,
        clientSideOnly = true,
        acceptableRemoteVersions = "*",
        dependencies = "required-after:forge@[13.19.0.2141,)",
        acceptedMinecraftVersions = "[1.11,1.12)"
)
public class BetterThanBunnies
{
    public boolean hasShownFirstGui;

    public static int fancyWeightage = 80;
    public static int randomizeSuitParts = 1;
    public static String[] disabledSuitParts = new String[0];

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        config.addCustomCategoryComment("general", "General settings");
        fancyWeightage = config.getInt("fancyWeightage", "general", 80, 0, 100, "Weightage of bunnies wearing parts of their outfit, in percentage% (0-100)");
        randomizeSuitParts = config.getInt("randomizeSuitParts", "general", 1, 0, 1, "0 = Render the entire outfit (except disabled parts)\n1 = Randomly choose which parts of the outfit to render (per bunny)");
        disabledSuitParts = config.getStringList("disabledSuitParts", "general", disabledSuitParts, "Disable parts of the outfit", new String[] { "hat", "monocle", "pipe", "suit" });

        if(config.hasChanged())
        {
            config.save();
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if(!hasShownFirstGui)
        {
            hasShownFirstGui = true;

            //Add the layer renderers
            Render renderer = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityRabbit.class);
            if(renderer instanceof RenderRabbit)
            {
                RenderRabbit renderRabbit = (RenderRabbit)renderer;
                renderRabbit.addLayer(new LayerFancyBunny(renderRabbit));
            }
        }
    }
}
