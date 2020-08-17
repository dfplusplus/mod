package com.github.dfplusplus.forge;


import com.google.common.collect.Maps;
import com.github.dfplusplus.forge.chat.ChatRule;
import net.minecraft.client.GameSettings;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static com.github.dfplusplus.forge.Main.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class Config {
    private static final ForgeConfigSpec configSpec;
    private static final ForgeConfigSpec.ConfigValue<String> customWordsSpec;
    private static final ForgeConfigSpec.ConfigValue<Integer> chatOffsetXSpec, chatOffsetYSpec;
    private static final ForgeConfigSpec.BooleanValue hasGeneratedChatSizingSettingsSpec;
    private static final ForgeConfigSpec.BooleanValue syncWithMinecraftSpec;
    private static final ForgeConfigSpec.DoubleValue chatScaleSpec, chatWidthSpec;

    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.EnumValue<ChatRule.ChatSide>> chatSideSpecs = Maps.newHashMap();
    private static final Map<ChatRule.ChatRuleType, ForgeConfigSpec.EnumValue<ChatRule.ChatSound>> chatSoundSpecs = Maps.newHashMap();

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("chat");
        customWordsSpec = builder
                .comment("The words for custom 2nd chat")
                .translation(MOD_ID + ".config.customwords")
                .define("customWords","");

        hasGeneratedChatSizingSettingsSpec = builder
                .comment("Whether the user has select 'Do not sync' on the chat sizing settings before")
                .translation(MOD_ID + ".config.hasgeneratedchatsizingsettings")
                .define("hasGeneratedChatSizingSettings",false);

        chatOffsetXSpec = builder
                .comment("The offset X, in pixels")
                .translation(MOD_ID + ".config.chatoffsetx")
                .define("chatOffsetX",0);

        chatOffsetYSpec = builder
                .comment("The offset Y, in pixels")
                .translation(MOD_ID + ".config.chatoffsety")
                .define("chatOffsetY",0);

        syncWithMinecraftSpec = builder
                .comment("Whether chat sizing settings are synced with minecraft")
                .translation(MOD_ID + ".config.syncwithminecraft")
                .define("syncWithMinecraft",true);

        chatScaleSpec = builder
                .comment("The scale of the chat")
                .translation(MOD_ID + ".config.chatscale")
                .defineInRange("chatScale",0,0f,1f);

        chatWidthSpec = builder
                .comment("The width of the chat")
                .translation(MOD_ID + ".config.chatwidth")
                .defineInRange("chatWidth",0,0f,1f);

        for (ChatRule.ChatRuleType chatRuleType : ChatRule.ChatRuleType.values()) {
            chatSideSpecs.put(chatRuleType, builder
                    .comment(String.format("Side of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", MOD_ID, chatRuleType.name()))
                    .defineEnum(String.format("%s_isSide", chatRuleType.name()), ChatRule.ChatSide.MAIN));
            chatSoundSpecs.put(chatRuleType, builder
                    .comment(String.format("Sound of %s", chatRuleType.name()))
                    .translation(String.format("%s.config.%sside", MOD_ID, chatRuleType.name()))
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

    public static void genChatSizingSettings(GameSettings gameSettings) {
        chatScaleSpec.set(gameSettings.chatScale);
        chatScaleSpec.save();
        chatWidthSpec.set(gameSettings.chatWidth);
        chatWidthSpec.save();
        hasGeneratedChatSizingSettingsSpec.set(true);
        hasGeneratedChatSizingSettingsSpec.save();
    }

    public static boolean hasGeneratedChatSizingSettings() {
        return hasGeneratedChatSizingSettingsSpec.get();
    }

    public static int getChatOffsetX() {
        return chatOffsetXSpec.get();
    }

    public static void setChatOffsetX(int newChatOffsetX) {
        if (newChatOffsetX == chatOffsetXSpec.get()) return;
        chatOffsetXSpec.set(newChatOffsetX);
        chatOffsetXSpec.save();
    }

    public static int getChatOffsetY() {
        return chatOffsetYSpec.get();
    }

    public static void setChatOffsetY(int newChatOffsetY) {
        if (newChatOffsetY == chatOffsetYSpec.get()) return;
        chatOffsetYSpec.set(newChatOffsetY);
        chatOffsetYSpec.save();
    }

    public static boolean getSyncWithMinecraft() {
        return syncWithMinecraftSpec.get();
    }

    public static void setSyncWithMinecraft(boolean newSyncWithMinecraft) {
        if (newSyncWithMinecraft == syncWithMinecraftSpec.get()) return;
        syncWithMinecraftSpec.set(newSyncWithMinecraft);
        syncWithMinecraftSpec.save();
    }

    public static double getChatScale() {
        return chatScaleSpec.get();
    }

    public static void setChatScale(double newChatScale) {
        if (newChatScale == chatScaleSpec.get()) return;
        chatScaleSpec.set(newChatScale);
        chatScaleSpec.save();
    }

    public static double getChatWidth() {
        return chatWidthSpec.get();
    }

    public static void setChatWidth(double newChatWidth) {
        if (newChatWidth == chatWidthSpec.get()) return;
        chatWidthSpec.set(newChatWidth);
        chatWidthSpec.save();
    }

    public static ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return chatSideSpecs.get(chatRuleType).get();
    }

    public static void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {
        chatSideSpecs.get(chatRuleType).set(chatSide); // true if inputted 'SIDE'
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
