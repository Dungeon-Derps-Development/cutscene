package dev.dungeonderps.cutscene.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec.BooleanValue DEV_MODE;

    public static class CommonConfig {

        public static ForgeConfigSpec COMMON_CONFIG;

        static {
            ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

            COMMON_BUILDER.push("General Options");
            DEV_MODE = COMMON_BUILDER.comment("Set this to true to get extra info in console to help with creating cutscenes [true/false]")
                    .define("devMode",false);
            COMMON_BUILDER.pop();
            COMMON_CONFIG = COMMON_BUILDER.build();
        }
    }
}
