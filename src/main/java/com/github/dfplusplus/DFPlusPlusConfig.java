package com.github.dfplusplus;


import com.google.common.collect.Maps;
import com.github.dfplusplus.chat.ChatRule;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class DFPlusPlusConfig {
    private static ForgeConfigSpec configSpec;
    private static final ForgeConfigSpec.ConfigValue<String> customWordsSpec;
    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.BooleanValue> chatSideSpecs = Maps.newHashMap();
    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.EnumValue<ChatRule.ChatSound>> chatSoundSpecs = Maps.newHashMap();

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("chat");
        customWordsSpec = builder
                .comment("The words for custom 2nd chat")
                .translation(Main.MOD_ID + ".config.customWords")
                .define("customWords","");

        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            chatSideSpecs.put(chatRuleType, builder
                    .comment(String.format("Side of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", Main.MOD_ID, chatRuleType.name()))
                    .define(String.format("%s_isSide", chatRuleType.name()), false));
            chatSoundSpecs.put(chatRuleType, builder
                    .comment(String.format("Sound of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", Main.MOD_ID, chatRuleType.name()))
                    .defineEnum(String.format("%s_sound", chatRuleType.name()), ChatRule.ChatSound.NONE));
        }
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

    public static ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return chatSoundSpecs.get(chatRuleType).get();
    }

    public static void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {
        chatSoundSpecs.get(chatRuleType).set(chatSound);
        chatSoundSpecs.get(chatRuleType).save();
    }

    public static ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }
}
