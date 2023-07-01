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

    public me.lortseam.completeconfig.data.Config configInstance;

    public ConfigFabric()
    {
        fancyChance = new ConfigWrapper<>(() -> GENERAL.fancyChance, v -> GENERAL.fancyChance = v);
        hatChance = new ConfigWrapper<>(() -> OUTFIT.hatChance, v -> OUTFIT.hatChance = v);
        monocleChance = new ConfigWrapper<>(() -> OUTFIT.monocleChance, v -> OUTFIT.monocleChance = v);
        pipeChance = new ConfigWrapper<>(() -> OUTFIT.pipeChance, v -> OUTFIT.pipeChance = v);
        suitChance = new ConfigWrapper<>(() -> OUTFIT.suitChance, v -> OUTFIT.suitChance = v);
    }


    @Transitive
    @ConfigEntries(includeAll = true)
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

        @Override
        public String getNameKey()
        {
            return "cat.general.name";
        }

        @Override
        public String getDescriptionKey()
        {
            return "cat.general.desc";
        }

        @ConfigEntry(nameKey = "prop.fancyChance.name", descriptionKey = "prop.fancyChance.desc", comment = "Chance of bunnies wearing parts of their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int fancyChance = 80;
    }

    @Transitive
    @ConfigEntries(includeAll = true)
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

        @Override
        public String getNameKey()
        {
            return "cat.outfit.name";
        }

        @Override
        public String getDescriptionKey()
        {
            return "cat.outfit.desc";
        }

        @ConfigEntry(nameKey = "prop.hatChance.name", descriptionKey = "prop.hatChance.desc", comment = "Chance of bunnies wearing hats in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int hatChance = 50;

        @ConfigEntry(nameKey = "prop.monocleChance.name", descriptionKey = "prop.monocleChance.desc", comment = "Chance of bunnies wearing a monocle in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int monocleChance = 50;

        @ConfigEntry(nameKey = "prop.pipeChance.name", descriptionKey = "prop.pipeChance.desc", comment = "Chance of bunnies having a pipe in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int pipeChance = 50;

        @ConfigEntry(nameKey = "prop.suitChance.name", descriptionKey = "prop.suitChance.desc", comment = "Chance of bunnies wearing a suit in their outfit, in percentage% (0-100)")
        @ConfigEntry.BoundedInteger(min = 0, max = 100)
        public int suitChance = 50;
    }
}
