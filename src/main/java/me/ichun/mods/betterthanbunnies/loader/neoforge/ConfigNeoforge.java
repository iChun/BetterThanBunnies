package me.ichun.mods.betterthanbunnies.loader.neoforge;

import me.ichun.mods.betterthanbunnies.common.core.Config;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigNeoforge extends Config
{
    public ConfigNeoforge(ModConfigSpec.Builder builder)
    {
        builder.comment("General settings").push("general");

        final ModConfigSpec.IntValue cFancyChance = builder.comment(Reference.FANCY_CHANCE_COMMENT)
            .translation("config.betterthanbunnies.prop.fancyChance.desc")
            .defineInRange("fancyChance", 80, 0, 100);
        fancyChance = new ConfigWrapper<>(cFancyChance::get, cFancyChance::set, cFancyChance::save);

        builder.pop();

        builder.comment("Settings regarding bunnies that are wearing outfits").push("outfit");

        final ModConfigSpec.IntValue cHatChance = builder.comment(Reference.HAT_CHANCE_COMMENT)
            .translation("config.betterthanbunnies.prop.hatChance.desc")
            .defineInRange("hatChance", 50, 0, 100);
        hatChance = new ConfigWrapper<>(cHatChance::get, cHatChance::set, cHatChance::save);

        final ModConfigSpec.IntValue cMonocleChance = builder.comment(Reference.MONOCLE_CHANCE_COMMENT)
            .translation("config.betterthanbunnies.prop.monocleChance.desc")
            .defineInRange("monocleChance", 50, 0, 100);
        monocleChance = new ConfigWrapper<>(cMonocleChance::get, cMonocleChance::set, cMonocleChance::save);

        final ModConfigSpec.IntValue cPipeChance = builder.comment(Reference.PIPE_CHANCE_COMMENT)
            .translation("config.betterthanbunnies.prop.pipeChance.desc")
            .defineInRange("pipeChance", 50, 0, 100);
        pipeChance = new ConfigWrapper<>(cPipeChance::get, cPipeChance::set, cPipeChance::save);

        final ModConfigSpec.IntValue cSuitChance = builder.comment(Reference.SUIT_CHANCE_COMMENT)
            .translation("config.betterthanbunnies.prop.suitChance.desc")
            .defineInRange("suitChance", 50, 0, 100);
        suitChance = new ConfigWrapper<>(cSuitChance::get, cSuitChance::set, cSuitChance::save);

        builder.pop();
    }
}
