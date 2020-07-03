package com.samstuff.dfadmintools;


import com.samstuff.dfadmintools.chat.ChatPredicates;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import static com.samstuff.dfadmintools.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DFPlusPlusConfig {
    public static ForgeConfigSpec configSpec;
    private static final ForgeConfigSpec.ConfigValue<String> customWordsSpec;
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
        customWordsSpec = builder
                .comment("The words for custom 2nd chat")
                .translation(MOD_ID + ".config.customWords")
                .define("customWords","");
        builder.pop();

        configSpec = builder.build();
    }

    public static String getCustomWords() {
        return customWordsSpec.get();
    }

    public static void setCustomWords(String customWords) {
        customWordsSpec.set(customWords);
        customWordsSpec.save();
    }
}
