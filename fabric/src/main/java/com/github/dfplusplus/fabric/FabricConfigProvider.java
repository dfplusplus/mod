package com.github.dfplusplus.fabric;

import com.github.dfplusplus.common.chat.ChatRule;
import com.github.dfplusplus.common.config.Config;
import com.github.dfplusplus.common.config.IConfigProvider;

public class FabricConfigProvider implements IConfigProvider {
    static {
        Config.setConfigProvider(new FabricConfigProvider());
    }

    @Override
    public String getCustomWords() {
        return "beans";
    }

    @Override
    public void setCustomWords(String customWords) {

    }

    @Override
    public boolean hasGeneratedChatSizingSettings() {
        return true;
    }

    @Override
    public void setGeneratedChatSizingSettings(boolean hasGeneratedChatSizingSettings) {

    }

    @Override
    public int getChatOffsetX() {
        return 0;
    }

    @Override
    public void setChatOffsetX(int newChatOffsetX) {

    }

    @Override
    public int getChatOffsetY() {
        return 0;
    }

    @Override
    public void setChatOffsetY(int newChatOffsetY) {

    }

    @Override
    public boolean getSyncWithMinecraftClient() {
        return true;
    }


    @Override
    public void setSyncWithMinecraftClient(boolean newSyncWithMinecraftClient) {

    }

    @Override
    public double getChatScale() {
        return 1;
    }

    @Override
    public void setChatScale(double newChatScale) {

    }

    @Override
    public double getWidth() {
        return 1;
    }

    @Override
    public void setChatWidth(double newChatWidth) {

    }

    @Override
    public ChatRule.ChatSide getChatSide(ChatRule.ChatRuleType chatRuleType) {
        return ChatRule.ChatSide.EITHER;
    }

    @Override
    public void setChatSide(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSide chatSide) {

    }

    @Override
    public ChatRule.ChatSound getChatSound(ChatRule.ChatRuleType chatRuleType) {
        return ChatRule.ChatSound.NONE;
    }

    @Override
    public void setChatSound(ChatRule.ChatRuleType chatRuleType, ChatRule.ChatSound chatSound) {

    }
}
