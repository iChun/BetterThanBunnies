package me.ichun.mods.betterthanbunnies.common;

import me.ichun.mods.betterthanbunnies.client.render.BunnyFancyLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            setupConfig();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::finishLoading);
        });
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> LOGGER.log(Level.ERROR, "You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!"));
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
    private void finishLoading(FMLLoadCompleteEvent event)
    {
        EntityRenderer<?> render = Minecraft.getInstance().getRenderManager().renderers.get(EntityType.RABBIT);
        if(render instanceof RabbitRenderer)
        {
            RabbitRenderer rabbitRenderer = (RabbitRenderer)render;
            rabbitRenderer.addLayer(new BunnyFancyLayer(rabbitRenderer));
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
                    .defineInRange("hatChance", 100, 0, 100);
            monocleChance = builder.comment("Chance of bunnies wearing a monocle in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.monocleChance.desc")
                    .defineInRange("monocleChance", 100, 0, 100);
            pipeChance = builder.comment("Chance of bunnies having a pipe in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.pipeChance.desc")
                    .defineInRange("pipeChance", 100, 0, 100);
            suitChance = builder.comment("Chance of bunnies wearing a suit in their outfit, in percentage% (0-100)")
                    .translation("config.betterthanbunnies.prop.suitChance.desc")
                    .defineInRange("suitChance", 100, 0, 100);

            builder.pop();
        }
    }
}
