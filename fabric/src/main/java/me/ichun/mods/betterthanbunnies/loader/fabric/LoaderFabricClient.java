package me.ichun.mods.betterthanbunnies.loader.fabric;

import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import me.ichun.mods.betterthanbunnies.common.core.Config;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends BetterThanBunnies
    implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register  config
        config = iChunUtil.d().registerConfig(new Config());

        //Create event handler
        eventHandlerClient = new EventHandlerClientFabric();
    }
}
