package me.ichun.mods.betterthanbunnies.common.core;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Config
{
    public ConfigWrapper<Integer> fancyChance;

    public ConfigWrapper<Integer> hatChance;
    public ConfigWrapper<Integer> monocleChance;
    public ConfigWrapper<Integer> pipeChance;
    public ConfigWrapper<Integer> suitChance;

    protected static class Reference
    {
        public static final String FANCY_CHANCE_COMMENT = "Chance of bunnies wearing parts of their outfit, in percentage% (0-100)";
        public static final String HAT_CHANCE_COMMENT = "Chance of bunnies wearing hats in their outfit, in percentage% (0-100)";
        public static final String MONOCLE_CHANCE_COMMENT = "Chance of bunnies wearing a monocle in their outfit, in percentage% (0-100)";
        public static final String PIPE_CHANCE_COMMENT = "Chance of bunnies having a pipe in their outfit, in percentage% (0-100)";
        public static final String SUIT_CHANCE_COMMENT = "Chance of bunnies wearing a suit in their outfit, in percentage% (0-100)";
    }

    public static class ConfigWrapper<T>
    {
        public final Supplier<T> getter;
        public final Consumer<T> setter;
        public final Runnable saver;

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter) {
            this.getter = getter;
            this.setter = setter;
            this.saver = null;
        }

        public ConfigWrapper(Supplier<T> getter, Consumer<T> setter, Runnable saver) {
            this.getter = getter;
            this.setter = setter;
            this.saver = saver;
        }

        public T get()
        {
            return getter.get();
        }

        public void set(T obj)
        {
            setter.accept(obj);
        }

        public void save()
        {
            if(saver != null)
            {
                saver.run();
            }
        }
    }
}
