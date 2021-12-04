package me.ichun.mods.betterthanbunnies.common;

import me.ichun.mods.betterthanbunnies.client.render.BunnyFancyLayer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterThanBunnies.MOD_ID)
public class BetterThanBunnies
{
    public static final String MOD_ID = "betterthanbunnies";
    public static final String MOD_NAME = "Better Than Bunnies";

    private static final Logger LOGGER = LogManager.getLogger();

    public static Config config;

    public BetterThanBunnies()
    {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            setupConfig();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onAddLayers);
        });
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.log(Level.ERROR, "You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    private void setupConfig()
    {
        //build the config
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        config = new Config(configBuilder);

        //register the config. This loads the config for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, configBuilder.build(), MOD_ID + ".toml");
    }

    @OnlyIn(Dist.CLIENT)
    private void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        LivingEntityRenderer<Rabbit, ? extends EntityModel<Rabbit>> render = event.getRenderer(EntityType.RABBIT);
        if(render instanceof RabbitRenderer rabbitRenderer)
        {
            boolean flag = false;
            for(RenderLayer<Rabbit, RabbitModel<Rabbit>> layer : rabbitRenderer.layers)
            {
                if(layer instanceof BunnyFancyLayer)
                {
                    flag = true;
                    break;
                }
            }
            if(!flag)
            {
                rabbitRenderer.addLayer(new BunnyFancyLayer(rabbitRenderer));
            }
        }
    }

    public class Config
    {
        public final ForgeConfigSpec.IntValue fancyChance;

        public final ForgeConfigSpec.IntValue hatChance;
        public final ForgeConfigSpec.IntValue monocleChance;
        public final ForgeConfigSpec.IntValue pipeChance;
        public final ForgeConfigSpec.IntValue suitChance;

        public Config(ForgeConfigSpec.Builder builder)
        {
            builder.comment("General settings").push("general");

            fancyChance = builder.comment("Chance of bunnies wearing parts of their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.fancyChance.desc")
                    .defineInRange("fancyChance", 80, 0, 100);

            builder.pop();

            builder.comment("Settings regarding bunnies that are wearing outfits").push("outfit");

            hatChance = builder.comment("Chance of bunnies wearing hats in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.hatChance.desc")
                    .defineInRange("hatChance", 50, 0, 100);
            monocleChance = builder.comment("Chance of bunnies wearing a monocle in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.monocleChance.desc")
                    .defineInRange("monocleChance", 50, 0, 100);
            pipeChance = builder.comment("Chance of bunnies having a pipe in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.pipeChance.desc")
                    .defineInRange("pipeChance", 50, 0, 100);
            suitChance = builder.comment("Chance of bunnies wearing a suit in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.suitChance.desc")
                    .defineInRange("suitChance", 50, 0, 100);

            builder.pop();
        }
    }
}
