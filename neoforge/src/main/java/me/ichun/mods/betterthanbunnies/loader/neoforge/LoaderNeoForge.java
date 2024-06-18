package me.ichun.mods.betterthanbunnies.loader.neoforge;

import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import me.ichun.mods.betterthanbunnies.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(BetterThanBunnies.MOD_ID)
public class LoaderNeoForge extends BetterThanBunnies
{
    public LoaderNeoForge(IEventBus modEventBus)
    {
        modProxy = this;

        if(FMLEnvironment.dist.isClient())
        {
            initClient(modEventBus);
        }
        else
        {
            LOGGER.error("You are loading " + MOD_NAME + " on a server. " + MOD_NAME + " is a client only mod!");
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void initClient(IEventBus modEventBus)
    {
        setupConfig(modEventBus);
        new EventHandlerClientNeoForge(modEventBus);
    }

    @OnlyIn(Dist.CLIENT)
    private void setupConfig(IEventBus modEventBus)
    {
        BetterThanBunnies.config = iChunUtil.d().registerConfig(new Config(), modEventBus);
    }

}
