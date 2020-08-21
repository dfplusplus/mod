package com.github.dfplusplus.common;


import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.providers.IConfigProvider;
import net.minecraft.client.GameSettings;

public class Config {
    private static IConfigProvider configProvider;

    public static IConfigProvider getConfigProvider() {
        return configProvider;
    }

    public static void setConfigProvider(IConfigProvider configProvider) {
        Config.configProvider = configProvider;
    }

    public static String getCustomWords() {
        return configProvider.getCustomWords();
    }

    public static void setCustomWords(String customWords) {
        configProvider.setCustomWords(customWords);
    }

    public static void genChatSizingSettings(GameSettings gameSettings) {
        configProvider.setChatScale(gameSettings.chatScale);
        configProvider.setChatWidth(gameSettings.chatWidth);
        configProvider.setGeneratedChatSizingSettings(true);
    }

    public static boolean hasGeneratedChatSizingSettings() {
        return configProvider.hasGeneratedChatSizingSettings();
    }

    public static int getChatOffsetX() {
        return configProvider.getChatOffsetX();
    }

    public static void setChatOffsetX(int newChatOffsetX) {
        if (newChatOffsetX != configProvider.getChatOffsetX())
            configProvider.setChatOffsetX(newChatOffsetX);
    }

    public static int getChatOffsetY() {
        return configProvider.getChatOffsetY();
    }

    public static void setChatOffsetY(int newChatOffsetY) {
        if (newChatOffsetY != configProvider.getChatOffsetY())
            configProvider.setChatOffsetY(newChatOffsetY);
    }

    public static boolean getSyncWithMinecraft() {
        return configProvider.getSyncWithMinecraft();
    }

    public static void setSyncWithMinecraft(boolean newSyncWithMinecraft) {
        if (newSyncWithMinecraft != configProvider.getSyncWithMinecraft())
            configProvider.setSyncWithMinecraft(newSyncWithMinecraft);
    }

    public static double getChatScale() {
        return configProvider.getChatScale();
    }

    public static void setChatScale(double newChatScale) {
        if (newChatScale != configProvider.getChatScale())
            configProvider.setChatScale(newChatScale);
    }

    public static double getChatWidth() {
        return configProvider.getChatWidth();
    }

    public static void setChatWidth(double newChatWidth) {
        if (newChatWidth != configProvider.getChatWidth())
            configProvider.setChatWidth(newChatWidth);
    }

    public static ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return configProvider.getChatSide(chatRuleType);
    }

    public static void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {
        configProvider.setChatSide(chatRuleType,chatSide);
    }

    public static ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return configProvider.getChatSound(chatRuleType);
    }

    public static void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {
        configProvider.setChatSound(chatRuleType,chatSound);
    }
}
