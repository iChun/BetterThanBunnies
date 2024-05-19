package me.ichun.mods.betterthanbunnies.common.core;

import me.ichun.mods.betterthanbunnies.common.BetterThanBunnies;
import me.ichun.mods.ichunutil.common.config.ConfigBase;
import me.ichun.mods.ichunutil.common.config.annotations.CategoryDivider;
import me.ichun.mods.ichunutil.common.config.annotations.Prop;
import org.jetbrains.annotations.NotNull;

public class Config extends ConfigBase
{
    @Prop(min = 0, max = 100)
    public int fancyChance = 80;

    @CategoryDivider(name = "outfit")
    @Prop(min = 0, max = 100)
    public int hatChance = 50;

    @Prop(min = 0, max = 100)
    public int monocleChance = 50;

    @Prop(min = 0, max = 100)
    public int pipeChance = 50;

    @Prop(min = 0, max = 100)
    public int suitChance = 50;

    public Config()
    {
        super(BetterThanBunnies.MOD_ID + ".toml");
    }

    @NotNull
    @Override
    public String getModId()
    {
        return BetterThanBunnies.MOD_ID;
    }

    @NotNull
    @Override
    public String getConfigName()
    {
        return BetterThanBunnies.MOD_NAME;
    }

    @Override
    public Type getConfigType()
    {
        return Type.CLIENT;
    }
}
