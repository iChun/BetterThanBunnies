package me.ichun.mods.betterthanbunnies.loader.fabric;

import me.ichun.mods.betterthanbunnies.common.core.Config;
import me.lortseam.completeconfig.api.ConfigContainer;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.api.ConfigGroup;

public class ConfigFabric extends Config
    implements ConfigContainer
{
    public static General GENERAL = null;
    public static Outfit OUTFIT = null;

    public ConfigFabric()
    {
        fancyChance = new ConfigWrapper<>(() -> GENERAL.fancyChance, v -> GENERAL.fancyChance = v);
        hatChance = new ConfigWrapper<>(() -> OUTFIT.hatChance, v -> OUTFIT.hatChance = v);
        monocleChance = new ConfigWrapper<>(() -> OUTFIT.monocleChance, v -> OUTFIT.monocleChance = v);
        pipeChance = new ConfigWrapper<>(() -> OUTFIT.pipeChance, v -> OUTFIT.pipeChance = v);
        suitChance = new ConfigWrapper<>(() -> OUTFIT.suitChance, v -> OUTFIT.suitChance = v);
    }


    @Transitive
    @ConfigEntries
    public static class General implements ConfigGroup
    {
        public General()
        {
            GENERAL = this;
        }

        @Override
        public String getComment()
        {
            return "General configs that don't fit any other category.";
        }

        @ConfigEntry(comment = "Chance of bunnies wearing parts of their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int fancyChance = 80;
    }

    @Transitive
    @ConfigEntries
    public static class Outfit implements ConfigGroup
    {
        public Outfit()
        {
            OUTFIT = this;
        }

        @Override
        public String getComment()
        {
            return "Configs regarding bunnies that are wearing outfits.";
        }

        @ConfigEntry(comment = "Chance of bunnies wearing hats in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int hatChance = 50;

        @ConfigEntry(comment = "Chance of bunnies wearing a monocle in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int monocleChance = 50;

        @ConfigEntry(comment = "Chance of bunnies having a pipe in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int pipeChance = 50;

        @ConfigEntry(comment = "Chance of bunnies wearing a suit in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int suitChance = 50;
    }
}
