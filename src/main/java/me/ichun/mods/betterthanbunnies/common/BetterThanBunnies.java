package me.ichun.mods.betterthanbunnies.common;

import com.mojang.logging.LogUtils;
import me.ichun.mods.betterthanbunnies.common.core.Config;
import org.slf4j.Logger;

public abstract class BetterThanBunnies
{
    public static final String MOD_ID = "betterthanbunnies";
    public static final String MOD_NAME = "Better Than Bunnies";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static BetterThanBunnies modProxy;

    public static Config config;
}
