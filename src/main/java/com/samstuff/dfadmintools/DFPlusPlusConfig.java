package com.samstuff.dfadmintools;


import com.google.common.collect.Maps;
import com.samstuff.dfadmintools.chat.ChatPredicates;
import com.samstuff.dfadmintools.chat.ChatRule;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

import static com.samstuff.dfadmintools.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class DFPlusPlusConfig {
    private static ForgeConfigSpec configSpec;
    private static final ForgeConfigSpec.ConfigValue<String> customWordsSpec;
    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.BooleanValue> chatSideSpecs = Maps.newHashMap();

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("chat");
        customWordsSpec = builder
                .comment("The words for custom 2nd chat")
                .translation(MOD_ID + ".config.customWords")
                .define("customWords","");

        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values())
            chatSideSpecs.put(chatRuleType,builder
                    .comment(String.format("Side of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", MOD_ID,chatRuleType.name()))
                    .define(String.format("%s_isSide", chatRuleType.name()), false));

        configSpec = builder.build();
    }

    public static String getCustomWords() {
        return customWordsSpec.get();
    }

    public static void setCustomWords(String customWords) {
        customWordsSpec.set(customWords);
        customWordsSpec.save();
    }

    public static ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return chatSideSpecs.get(chatRuleType).get() ? ChatRule.ChatSide.SIDE: ChatRule.ChatSide.MAIN;
    }

    public static void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {
        chatSideSpecs.get(chatRuleType).set(chatSide == ChatRule.ChatSide.SIDE); // true if inputted 'SIDE'
        chatSideSpecs.get(chatRuleType).save();
    }

    public static ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }
}
