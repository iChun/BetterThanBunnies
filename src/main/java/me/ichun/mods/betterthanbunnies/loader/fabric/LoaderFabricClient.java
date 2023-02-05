package me.ichun.mods.betterthanbunnies.loader.fabric;

import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import me.lortseam.completeconfig.data.Config;
import net.fabricmc.api.ClientModInitializer;

public class LoaderFabricClient extends BetterThanBunnies
    implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        modProxy = this;

        //register config
        ConfigFabric configFabric = new ConfigFabric();
        config = configFabric;
        Config modConfig = new Config(MOD_ID, new String[]{}, configFabric);
        modConfig.load();
        Runtime.getRuntime().addShutdownHook(new Thread(modConfig::save));

        //Create event handler
        new EventHandlerClientFabric();
    }
}
